package be.ictdynamic.mobiscan.services;

import be.ictdynamic.mobiscan.MobiscanConstants;
import be.ictdynamic.mobiscan.domain.GoogleDistanceMatrixResponse;
import be.ictdynamic.mobiscan.domain.MobiscanRequest;
import be.ictdynamic.mobiscan.enums.GoogleDistanceMatrixTransitMode;
import be.ictdynamic.mobiscan.utilities.DateUtility;
import be.ictdynamic.mobiscan.utilities.MobiscanException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

import static be.ictdynamic.mobiscan.utilities.MobiscanUtilities.timedReturn;

/**
 * Class GoogleServiceImpl.
 *
 * @author Wim Van den Brande
 * @since 04/04/2017 - 20:35
 */
@Service
public class GoogleServiceImpl implements GoogleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleServiceImpl.class);

    @Value("${mobiscan.google-distance-matrix-api-key}")
    private String GOOGLE_DISTANCE_MATRIX_API_KEY;

    @Value("${mobiscan.isTestMode}")
    private boolean isTestMode;

    @Override
    public GoogleDistanceMatrixResponse getGoogleDistanceMatrixResponse(final MobiscanRequest mobiscanRequest) {
        Date startDate = new Date();

        GoogleDistanceMatrixResponse googleDistanceMatrixResponse = new GoogleDistanceMatrixResponse();

        List<GoogleDistanceMatrixTransitMode> transitModes = Arrays.asList(GoogleDistanceMatrixTransitMode.values());

        transitModes.forEach(transitMode -> {
            try {
                GoogleDistanceMatrixResponse.GoogleDistanceMatrixResponseDetail googleDistanceMatrixResponseDetail = this.getGoogleDistanceBasedOnTransportModeAndTime(mobiscanRequest, transitMode.getTransitMode());
                googleDistanceMatrixResponse.getGoogleDistanceMatrixDetails().put(transitMode.getTransitMode(), googleDistanceMatrixResponseDetail);
            } catch (Exception e) {
                LOGGER.error(MobiscanConstants.LOG_ERROR + "Exception occurred : message = {}, mode = {}, mobiscanRequest = {}", e.getMessage(), transitMode, mobiscanRequest);
            }
        });

        return timedReturn(LOGGER, new Object() {}.getClass().getEnclosingMethod().getName(), startDate.getTime(), googleDistanceMatrixResponse);
    }

    @Override
    public Map<String, Double> getGoogleLatitudeLongitude(String address) throws MobiscanException {
        LOGGER.info(MobiscanConstants.LOG_STARTING + "getting LatitudeLongitudeFromGoogle for address = {}", address);

        // Geocoding API = https://developers.google.com/maps/documentation/geocoding/intro

        HttpClient httpClient = HttpClientBuilder.create().build();
        Map<String, Double> mapWithLatAndLng = new HashMap<>();

        try {
            URI uri = new URI(
                    "https",
                    "maps.googleapis.com",
                    "/maps/api/geocode/json",
                    "address=" + address + "&key=" + GOOGLE_DISTANCE_MATRIX_API_KEY,
                    null);

            String httpRequest = uri.toASCIIString();
            HttpGet request = new HttpGet(httpRequest);
            HttpResponse httpResponse = httpClient.execute(request);

            // CONVERT RESPONSE TO STRING
            String stringResult = EntityUtils.toString(httpResponse.getEntity());
            LOGGER.debug("--- stringResult = {}", stringResult);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(stringResult);
            LOGGER.debug("--- jsonObject = {}", jsonObject);

            // only process response if google was able to process the request

            if ("OK".equals(jsonObject.get("status"))) {
                JSONArray results = (JSONArray) jsonObject.get("results");

                for (int i = 0; i < results.size(); i++) {
                    JSONObject result = (JSONObject) results.get(i);

                    JSONObject geometry = (JSONObject) result.get("geometry");
                    JSONObject location = (JSONObject) geometry.get("location");

                    mapWithLatAndLng.put("lat", (Double) location.get("lat"));
                    mapWithLatAndLng.put("lng", (Double) location.get("lng"));
                }
            } else {
                LOGGER.error(MobiscanConstants.LOG_ERROR + "Google returns an error: status {}", jsonObject.get("status"));
                throw new MobiscanException("Google returns an error: status: " + jsonObject.get("status"));
            }

        } catch (Exception e) {
            LOGGER.error(MobiscanConstants.LOG_ERROR + "Exception occurred when geo-coding: address = {}", address);
            throw new MobiscanException(e);
        }

        LOGGER.info(MobiscanConstants.LOG_ENDING + "address {} has lat {} and lng {}.", address, mapWithLatAndLng.get("lat"), mapWithLatAndLng.get("lng"));
        return mapWithLatAndLng;
    }

    // private methods
    // ---------------

    private GoogleDistanceMatrixResponse.GoogleDistanceMatrixResponseDetail getGoogleDistanceBasedOnTransportModeAndTime(final MobiscanRequest mobiscanRequest, final String transitMode) throws Exception {
        LOGGER.debug(MobiscanConstants.LOG_STARTING + "mode = {}, fromAddress = {}, toAddress = {}, departureTime = {}", transitMode, mobiscanRequest.getLocationFrom(), mobiscanRequest.getLocationTo(), mobiscanRequest.getDepartureDate());

        GoogleDistanceMatrixResponse.GoogleDistanceMatrixResponseDetail googleDistanceMatrixResponseDetail = new GoogleDistanceMatrixResponse.GoogleDistanceMatrixResponseDetail();

        HttpClient httpClient = HttpClientBuilder.create().build();

        // we only include departureTime in the request when departure-date-time is in the future

        String departureTime = "";
        if (mobiscanRequest.getDepartureDate() != null && mobiscanRequest.getDepartureDate().isAfter(LocalDateTime.now()) ) {
            departureTime = "&departure_time=" + DateUtility.convertLocalDateTimeToEpoch(mobiscanRequest.getDepartureDate());
        }

        URI uri = new URI(
                "https",
                "maps.googleapis.com",
                "/maps/api/distancematrix/json",
                          "origins=" + mobiscanRequest.getLocationFrom()
                        + "&destinations=" + mobiscanRequest.getLocationTo()
                        + departureTime + "&mode=" + transitMode + "&key=" + GOOGLE_DISTANCE_MATRIX_API_KEY,
                null);

        String httpRequest = uri.toASCIIString();

        LOGGER.debug("--- HTTP request = {}", httpRequest);

        HttpGet request = new HttpGet(httpRequest);

        try {
            String stringResult;

            if (!isTestMode) {
                HttpResponse httpResponse = httpClient.execute(request);
                LOGGER.debug("--- httpResponse = {}", httpResponse);

                // CONVERT RESPONSE TO STRING
                stringResult = EntityUtils.toString(httpResponse.getEntity());
            }
            else {
                short index = (short) Integer.parseInt(mobiscanRequest.getEmployeeId());
                if (Integer.parseInt(mobiscanRequest.getEmployeeId()) > 1) {
                    index = 0;
                }
                stringResult = MobiscanConstants.DISTANCE_MATRIX_MOCK_RESPONSES[index];
            }

            LOGGER.debug("--- stringResult = {}", stringResult);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(stringResult);

            LOGGER.debug("--- jsonObject = {}", jsonObject);

            if ("OK".equals(jsonObject.get("status"))) {
                JSONArray jsonArrayRow = (JSONArray) jsonObject.get("rows");

                for (int i = 0; i < jsonArrayRow.size(); i++) {
                    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                    JSONObject jsonRow = (JSONObject) jsonArrayRow.get(i);

                    JSONArray jsonArrayElement = (JSONArray) jsonRow.get("elements");
                    for (int j = 0; j < jsonArrayElement.size(); j++) {
                        JSONObject jsonElement = (JSONObject) jsonArrayElement.get(j);

                        LOGGER.debug("---google distance = {}", jsonElement.get("distance") == null ? 0 : ((JSONObject) jsonElement.get("distance")).get("value"));
                        googleDistanceMatrixResponseDetail.setDistance(jsonElement.containsKey("distance")? (Long) ((JSONObject) jsonElement.get("distance")).get("value") : 0) ;

                        // when we drive, we use duration_in_traffic (if available) to get a more realistic duration
                        if (GoogleDistanceMatrixTransitMode.DRIVING.getTransitMode().equals(transitMode) && jsonElement.containsKey("duration_in_traffic")) {
                            LOGGER.debug("---google duration_in_traffic in seconds = {}", jsonElement.containsKey("duration_in_traffic") ? ((JSONObject) jsonElement.get("duration_in_traffic")).get("value") : 0);
                            googleDistanceMatrixResponseDetail.setDuration(jsonElement.containsKey("duration_in_traffic") ? (Long) ((JSONObject) jsonElement.get("duration_in_traffic")).get("value") : 0);
                        }
                        else {
                            LOGGER.debug("---google duration in seconds = {}", jsonElement.containsKey("duration") ? ((JSONObject) jsonElement.get("duration")).get("value") : 0);
                            googleDistanceMatrixResponseDetail.setDuration(jsonElement.containsKey("duration") ? (Long) ((JSONObject) jsonElement.get("duration")).get("value") : 0);
                        }
                    }
                }
            }
            else {
                LOGGER.error(MobiscanConstants.LOG_ERROR + "Google returns an error: status {}", jsonObject.get("status"));
            }

        } catch (IOException e ) {
            LOGGER.error(MobiscanConstants.LOG_ERROR + "Exception occurred when querying Google Maps: message = {}, mode = {}, request = {}", e.getMessage(), transitMode, mobiscanRequest);
            throw e;
        }

        LOGGER.debug(MobiscanConstants.LOG_ENDING + "mode = {}, request = {}, googleDistanceMatrixResponseDetail = {}", transitMode, mobiscanRequest, googleDistanceMatrixResponseDetail);
        return googleDistanceMatrixResponseDetail;
    }

}
