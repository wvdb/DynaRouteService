package be.ictdynamic.dynarouteservice.services.google_service;

import be.ictdynamic.dynarouteservice.DynaRouteServiceConstants;
import be.ictdynamic.dynarouteservice.domain.TransportInfo;
import be.ictdynamic.dynarouteservice.domain.TransportRequest;
import be.ictdynamic.dynarouteservice.domain.TransportResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Class GoogleServiceImpl.
 *
 * @author Wim Van den Brande
 * @since 04/04/2017 - 20:35
 */
@Service
public class GoogleServiceImpl implements GoogleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleServiceImpl.class);

    @Value("${dyna-route-service.google-distance-matrix-api-key}")
    public String GOOGLE_DISTANCE_MATRIX_API_KEY;

    public static final String DRIVING = "driving";
    public static final String WALKING = "walking";
    public static final String BICYCLING = "bicycling";
    public static final String TRANSIT = "transit";

    public TransportResponse getGoogleDistance(final TransportRequest transportRequest) {
        TransportResponse transportResponse = new TransportResponse();
        HashMap<String, TransportInfo> googleTransportInfoMap = new HashMap<>();
        transportResponse.setTransportInfoMap(new HashMap<>());

        List<String> transitModes = Arrays.asList(DRIVING, WALKING, BICYCLING, TRANSIT);

        transitModes.forEach(transitMode -> {
            TransportInfo transportInfo = null;
            try {
                long departTimeLong;
                // if departureTime is unknown, we use system date as default
                if (transportRequest.getDepartureTime() == null) {
                    departTimeLong = System.currentTimeMillis() / 1000;
                }
                else {
                    departTimeLong = transportRequest.getDepartureTime().getTime() / 1000;
                }
                transportInfo = this.getGoogleDistanceBasedOnTransportModeAndTime(transportRequest, transitMode, departTimeLong);
                googleTransportInfoMap.put(transitMode, transportInfo);
            } catch (URISyntaxException e) {
                LOGGER.error(DynaRouteServiceConstants.LOG_ERROR + "Exception occurred when invoking getGoogleDistanceBasedOnTransportModeAndTime : message = {}, mode = {}, request = {}", e.getMessage(), transitMode, transportRequest);
            }
        });

        transportResponse.setTransportInfoMap(googleTransportInfoMap);
        return transportResponse;
    }

    private TransportInfo getGoogleDistanceBasedOnTransportModeAndTime(final TransportRequest transportRequest, final String transitMode, final long departureTime) throws URISyntaxException {
        LOGGER.info(DynaRouteServiceConstants.LOG_STARTING + "mode = {}, request = {}", transitMode, transportRequest);

        // Google Distance Matrix API = https://developers.google.com/maps/documentation/distance-matrix/start

        // example of a request:

//        https://maps.googleapis.com/maps/api/distancematrix/json?
//                              origins=Tweebunder%204,+Edegem,+Belgium&
//                              destinations=Oostende,+Belgium&
//                              departure_time=1492675220&
//                              key=AIzaSyDrQxf6ftnF-2xihZBUQkTL6ZEIlgee5WA

        // example of a response:

//        {
//            "destination_addresses" : [ "8400 Ostend, Belgium" ],
//            "origin_addresses" : [ "Tweebunder 4, 2650 Edegem, Belgium" ],
//            "rows" : [
//            {
//                "elements" : [
//                {
//                    "distance" : {
//                    "text" : "129 km",
//                            "value" : 128582
//                },
//                    "duration" : {
//                    "text" : "1 hour 17 mins",
//                            "value" : 4629
//                },
//                    "duration_in_traffic" : {
//                    "text" : "1 hour 19 mins",
//                            "value" : 4725
//                },
//                    "status" : "OK"
//                }
//                ]
//            }
//            ],
//            "status" : "OK"
//        }

        // epoch : https://www.epochconverter.com/

        TransportInfo transportInfo = new TransportInfo();
        HttpClient httpClient = new DefaultHttpClient();

        URI uri = new URI(
                "https",
                "maps.googleapis.com",
                "/maps/api/distancematrix/json",
                          "origins=" + transportRequest.getHomeAddress()
                        + "&destinations=" + transportRequest.getOfficeAddress()
                        + "&departure_time=" + departureTime + "&mode=" + transitMode + "&key=" + GOOGLE_DISTANCE_MATRIX_API_KEY,
                null);

        String httpRequest = uri.toASCIIString();

        LOGGER.debug("--- HTTP request = {}", httpRequest);

        HttpGet request = new HttpGet(httpRequest);

        try {
            HttpResponse httpResponse = httpClient.execute(request);
            LOGGER.debug("--- httpResponse = {}", httpResponse);

            // CONVERT RESPONSE TO STRING
            String stringResult = EntityUtils.toString(httpResponse.getEntity());
            LOGGER.debug("--- stringResult = {}", stringResult);

            JSONObject jsonObject = new JSONObject(stringResult);
            LOGGER.debug("--- jsonObject = {}", jsonObject);

            // only process response if google was able to process the request

            if ("OK".equals(jsonObject.get("status"))) {
                // CONVERT STRING TO JSON ARRAY
                JSONArray jsonArrayRow = jsonObject.getJSONArray("rows");

                for (int i = 0; i < jsonArrayRow.length(); i++) {
                    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                    JSONObject jsonObject2 = jsonArrayRow.getJSONObject(i);

                    JSONArray jsonArrayElement = jsonObject2.getJSONArray("elements");
                    for (int j = 0; j < jsonArrayElement.length(); j++) {
                        JSONObject jsonElement = jsonArrayElement.getJSONObject(j);
                        LOGGER.debug("---google distance = {}", jsonElement.getJSONObject("distance") == null ? 0 : jsonElement.getJSONObject("distance").get("value"));

                        transportInfo.setDistance(jsonElement.opt("distance") == null ? 0 : (Integer) jsonElement.getJSONObject("distance").get("value"));
                        // when we drive, we use duration_in_traffic to get a more realistic duration
                        if (DRIVING.equals(transitMode)) {
                            LOGGER.debug("---google duration in metres = {}", jsonElement.opt("duration_in_traffic") == null ? 0 : jsonElement.getJSONObject("duration_in_traffic").get("value"));
                            transportInfo.setDuration(jsonElement.opt("duration_in_traffic") == null ? 0 : (Integer) jsonElement.getJSONObject("duration_in_traffic").get("value"));
                        }
                        else {
                            LOGGER.debug("---google duration in seconds = {}", jsonElement.opt("duration") == null ? 0 : jsonElement.getJSONObject("duration").get("value"));
                            transportInfo.setDuration(jsonElement.opt("duration") == null ? 0 : (Integer) jsonElement.getJSONObject("duration").get("value"));
                        }
                    }
                }
            }
            else {
                LOGGER.error(DynaRouteServiceConstants.LOG_ERROR + "Google returns an error: status {}", jsonObject.get("status"));
            }

        } catch (Throwable e) {
            LOGGER.error(DynaRouteServiceConstants.LOG_ERROR + "Exception occurred when querying Google Maps: message = {}, mode = {}, request = {}", e.getMessage(), transitMode, transportRequest);
            return transportInfo;
        }

        LOGGER.info(DynaRouteServiceConstants.LOG_ENDING + "mode = {}, request = {}, googleTransportInfo = {}", transitMode, transportRequest, transportInfo);
        return transportInfo;
    }

}
