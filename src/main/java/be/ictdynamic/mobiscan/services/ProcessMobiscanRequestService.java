package be.ictdynamic.mobiscan.services;

import be.ictdynamic.mobiscan.domain.GoogleDistanceMatrixResponse;
import be.ictdynamic.mobiscan.domain.MobiscanRequest;
import be.ictdynamic.mobiscan.repository.MobiscanRequestRepository;
import be.ictdynamic.mobiscan.utilities.MobiscanException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static be.ictdynamic.mobiscan.utilities.MobiscanUtilities.timedReturn;

@Component
public class ProcessMobiscanRequestService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private MobiscanRequestRepository mobiscanRequestRepository;

    @Autowired
    private GoogleService googleService;

    @Value("${mobiscan.saveToES}")
    private boolean saveToES;

    @Value("${mobiscan.es.mobiscan_location_index_name}")
    public String ES_MOBISCAN_LOCATION_INDEX_NAME;

    @Value("${mobiscan.es.mobiscan_location_index_type}")
    public String ES_MOBISCAN_LOCATION_INDEX_TYPE;

    @Value("${mobiscan.es.mobiscan_route_details_index_name}")
    public String ES_MOBISCAN_ROUTE_DETAILS_INDEX_TYPE;

    @Autowired
    private RestHighLevelClient restClient;

    /*
      This method is <b>scheduled</b> and is responsible for the actual processing of requests that haven't been processed yet.

      Method will do the following:
      * retrieving requests that haven't been processed yet
      * for each request:
          * retrieving distance and duration from Google (or test-json as found in MobiscanConstants)
          * for location-from-address|location-to-address
          *   if address doesn't exist in ES yet
          *     retrieving latitude/longitude from Google (or test-json as found in MobiscanConstants)
          *     persistence of address with lat/lon in ES
          *   end-if
          * end-for
          * persistence of googleDistanceMatrixResponse in ES
          * update request's processing-date

     */
    @Scheduled(fixedDelayString = "${mobiscan.job.processMobiscanRequest.fixedRate}")
    public void processMobiscanRequests() throws MobiscanException {
        List<MobiscanRequest> mobiscanRequestsToBeProcessed = mobiscanRequestRepository.findByProcessingDateIsNull();
        LOGGER.info(String.format("Number of requests to be processed = %06d.", mobiscanRequestsToBeProcessed == null ? 0 : mobiscanRequestsToBeProcessed.size()));

        if (mobiscanRequestsToBeProcessed != null) {
            mobiscanRequestsToBeProcessed.forEach(mobiscanRequest -> {
                String addressId1 = null;
                String addressId2 = null;

                GoogleDistanceMatrixResponse googleDistanceMatrixResponse = googleService.getGoogleDistanceMatrixResponse(mobiscanRequest);

                if (mobiscanRequest.getLocationFrom() != null && saveToES) {
                    try {
                        addressId1 = persistAddress(mobiscanRequest.getLocationFrom());
                    } catch (MobiscanException e) {
                        LOGGER.error("Processing mobiscanRequest {} caused error {}.", e.getMessage());
                        return;
                    }
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Id of from-address {} is {}.", mobiscanRequest.getLocationFrom(), addressId1);
                    }
                }

                if (mobiscanRequest.getLocationTo() != null && saveToES) {
                    try {
                        addressId2 = persistAddress(mobiscanRequest.getLocationTo());
                    } catch (MobiscanException e) {
                        LOGGER.error("Processing mobiscanRequest {} caused error {}.", mobiscanRequest, e.getMessage());
                        return;
                    }
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Id of to-address {} is {}.", mobiscanRequest.getLocationTo(), addressId2);
                    }
                }

                if (addressId1 == null || addressId2 == null) {
                    LOGGER.error(addressId1 == null ? mobiscanRequest.getLocationFrom() + " is invalid (according to Google!)" : "");
                    LOGGER.error(addressId2 == null ? mobiscanRequest.getLocationTo() + " is invalid (according to Google!)" : "");
                }
                else {
                    if (saveToES) {
                        try {
                            persistGoogleDistanceMatrixResponse(addressId1, addressId2, googleDistanceMatrixResponse);
                        } catch (MobiscanException e) {
                            LOGGER.error("MobiscanRequest {} caused error {} and response has not been persisted.", mobiscanRequest, e.getMessage());
                            return;
                        }
                    }
                }

                mobiscanRequest.setProcessingDate(LocalDateTime.now());
                mobiscanRequestRepository.save(mobiscanRequest);
            });
        }

    }

    // private methods
    // ---------------

    private String persistGoogleDistanceMatrixResponse(String addressId1, String addressId2, GoogleDistanceMatrixResponse googleDistanceMatrixResponse) throws MobiscanException {
        IndexRequest indexRequest = new IndexRequest(ES_MOBISCAN_ROUTE_DETAILS_INDEX_TYPE, "doc")
                .source(
                        "createdOn", new Date(),
                        "addressId1", addressId1,
                        "addressId2", addressId2,
                        googleDistanceMatrixResponse.getGoogleDistanceMatrixDetails()
                );

        try {
            IndexResponse indexResponse = restClient.index(indexRequest);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Persisted googleDistanceMatrixResponse: {}, id = {}.", googleDistanceMatrixResponse, indexResponse.getId());
            }
            return indexResponse.getId();
        } catch (IOException e) {
            LOGGER.error("Failed to persist GoogleDistanceMatrixResponse {} in ES. Exception = {}.", googleDistanceMatrixResponse, e);
            throw new MobiscanException(e);
        }
    }

    private String persistAddress(String address) throws MobiscanException {
        String mobiscanLocationId = retrieveMobiscanLocationIdFromES(address);

        if (mobiscanLocationId == null) {
            Map<String, Double> latLonMap = googleService.getLatitudeLongitudeFromGoogle(address);
            if (latLonMap != null && latLonMap.keySet().size() == 2) {
                return persistAddressInES(address, latLonMap.get("lat"), latLonMap.get("lng"));
            }
            else {
                return null;
            }
        }
        else {
            return mobiscanLocationId;
        }
    }

    private String retrieveMobiscanLocationIdFromES(String address) throws MobiscanException {
        Date startDate = new Date();

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Retrieving address {}.", address);
        }

        BoolQueryBuilder boolQuery = new BoolQueryBuilder();
        boolQuery.must(QueryBuilders.termQuery("address", address));

        sourceBuilder.query(boolQuery).size(10_000);

        SearchRequest searchRequest = new SearchRequest(ES_MOBISCAN_LOCATION_INDEX_NAME)
                .types(ES_MOBISCAN_LOCATION_INDEX_TYPE)
                .source(sourceBuilder);
        String addressRetrieved = getES_id(searchRequest);

        return timedReturn(LOGGER, new Object() {}.getClass().getEnclosingMethod().getName(), startDate.getTime(), addressRetrieved);
    }

    private String persistAddressInES(String address, double latitude, double longitude) throws MobiscanException {
        JSONObject location = new JSONObject();
        location.put("lon", longitude);
        location.put("lat", latitude);

        IndexRequest indexRequest = new IndexRequest(ES_MOBISCAN_LOCATION_INDEX_NAME, ES_MOBISCAN_LOCATION_INDEX_TYPE)
                .source(
                        "createdOn", new Date(),
                        "address", address,
                        "location", location
                );

        try {
            IndexResponse indexResponse = restClient.index(indexRequest);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Persisted address: {}, id = {}.", address, indexResponse.getId());
            }
            return indexResponse.getId();
        } catch (IOException e) {
            LOGGER.error("Failed to persist address {} in ES. Exception = {}.", address, e);
            throw new MobiscanException(e);
        }
    }

    private String getES_id(SearchRequest searchRequest) throws MobiscanException {
        String id = null;
        try {
            SearchResponse searchResponse = restClient.search(searchRequest);
            SearchHits hits = searchResponse.getHits();

            for (SearchHit hit : hits.getHits()) {
                id = hit.getId();
            }
        }
        catch (Exception e) {
            LOGGER.error("Elasticsearch error. Message = {}.", e.getMessage());
            throw new MobiscanException(e);
        }
        return id;
    }

    private static String getAddressFromESHit(SearchHit hit) {
        Map<String,Object> source = hit.getSourceAsMap();
        return (String) source.get("address");
    }
}
