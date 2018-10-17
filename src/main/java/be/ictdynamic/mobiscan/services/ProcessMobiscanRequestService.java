package be.ictdynamic.mobiscan.services;

import be.ictdynamic.mobiscan.domain.GoogleDistanceMatrixResponse;
import be.ictdynamic.mobiscan.domain.MobiscanRequest;
import be.ictdynamic.mobiscan.repository.MobiscanRequestRepository;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
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

    @Autowired
    private RestHighLevelClient restClient;

    /*
      This method is <b>scheduled</b> and is responsible for the actual processing of requests that haven't been processed yet.

      Method will do the following:
      * retrieving requests that haven't been processed yet
      * for each request:
          * retrieving distance and duration from Google (or test-json as found in MobiscanConstants)
          * retrieving latitude/longitude from Google (or test-json as found in MobiscanConstants) -> TBD
          * persistence of locationFrom in ES (if entry doesn't exist yet) -> TBD
          * persistence of locationTo in ES (if entry doesn't exist yet) -> TBD
          * update request's processing-date

     */
    @Scheduled(fixedDelayString = "${mobiscan.job.processMobiscanRequest.fixedRate}")
    public void processMobiscanRequests() {
        List<MobiscanRequest> mobiscanRequestsToBeProcessed = mobiscanRequestRepository.findByProcessingDateIsNull();
        LOGGER.info(String.format("Number of requests to be processed = %06d.", mobiscanRequestsToBeProcessed == null ? 0 : mobiscanRequestsToBeProcessed.size()));

        if (mobiscanRequestsToBeProcessed != null) {
            mobiscanRequestsToBeProcessed.forEach(mobiscanRequest -> {
                String addressId1 = null;
                String addressId2 = null;

                GoogleDistanceMatrixResponse googleDistanceMatrixResponse = googleService.getGoogleDistanceMatrixResponse(mobiscanRequest);

                if (mobiscanRequest.getLocationFrom() != null && saveToES) {
                    addressId1 = persistAddressInES(mobiscanRequest.getLocationFrom(), 0D, 0D);
                }

                if (mobiscanRequest.getLocationTo() != null && saveToES) {
                    addressId2 = persistAddressInES(mobiscanRequest.getLocationTo(), 0D, 0D);
                }

                mobiscanRequest.setProcessingDate(LocalDateTime.now());
                mobiscanRequestRepository.save(mobiscanRequest);
            });
        }

    }

    private String persistAddressInES(String address, double latitude, double longitude) {
//            MobiscanLocationES mobiscanLocationES = new MobiscanLocationES();

            // TBD : to search against ES if address exists already

            JSONObject myLocation = new JSONObject();
            myLocation.put("lon", longitude);
            myLocation.put("lat", latitude);

            IndexRequest indexRequest = new IndexRequest(ES_MOBISCAN_LOCATION_INDEX_NAME, ES_MOBISCAN_LOCATION_INDEX_TYPE)
                    .source(
                            "createdOn", new Date(),
                            "address", address,
                            "location", myLocation
                    );

        try {
            IndexResponse indexResponse = restClient.index(indexRequest);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Persisted address: {}, id = {}.", address, indexResponse.getId());
            }
            return indexResponse.getId();
        } catch (IOException e) {
            LOGGER.error("Failed to persist addres {} in ES. Exception = {}.", address, e);
            return null;
        }

    }
}
