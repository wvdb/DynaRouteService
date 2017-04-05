package be.ictdynamic.dynarouteservice.services.google_service;

import be.ictdynamic.dynarouteservice.DynaRouteServiceConstants;
import be.ictdynamic.dynarouteservice.domain.GoogleMapRequest;
import be.ictdynamic.dynarouteservice.domain.GoogleMapResponse;
import be.ictdynamic.dynarouteservice.domain.GoogleTransportInfo;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Class GoogleMapRealServiceImpl.
 *
 * @author Wim Van den Brande
 * @since 04/04/2017 - 20:35
 */
@Service
public class GoogleMapServiceImpl implements GoogleMapService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleMapServiceImpl.class);
    public static final String GOOGLE_DISTANCE_MATRIX_API_KEY = "AIzaSyDrQxf6ftnF-2xihZBUQkTL6ZEIlgee5WA";

    public static final String DRIVING = "driving";
    public static final String WALKING = "walking";
    public static final String BICYCLING = "bicycling";
    public static final String TRANSIT = "transit";

    public GoogleMapResponse getGoogleDistance(final GoogleMapRequest googleMapRequest) {
        GoogleMapResponse googleMapResponse = new GoogleMapResponse();
        HashMap<String, GoogleTransportInfo> googleTransportInfoMap = new HashMap<>();
        googleMapResponse.setTransportInfoMap(new HashMap<>());

        List<String> transitModes = Arrays.asList(DRIVING, WALKING, BICYCLING, TRANSIT);

        transitModes.forEach(transitMode -> {
            GoogleTransportInfo googleTransportInfo = null;
            try {
                // TODO : support for future times
                googleTransportInfo = this.getGoogleDistanceBasedOnTransportModeAndTime(googleMapRequest, transitMode, System.currentTimeMillis() / 1000);
                googleTransportInfoMap.put(transitMode, googleTransportInfo);
            } catch (URISyntaxException e) {
                LOGGER.error(DynaRouteServiceConstants.LOG_ERROR + "Exception occurred when invoking getGoogleDistanceBasedOnTransportModeAndTime : message = {}, mode = {}, request = {}", e.getMessage(), transitMode, googleMapRequest);
            }
        });

        googleMapResponse.setTransportInfoMap(googleTransportInfoMap);
        return googleMapResponse;
    }

    private GoogleTransportInfo getGoogleDistanceBasedOnTransportModeAndTime(final GoogleMapRequest googleMapRequest, final String transitMode, final long departureTime) throws URISyntaxException {
        LOGGER.info(DynaRouteServiceConstants.LOG_STARTING + "mode = {}, request = {}", transitMode, googleMapRequest);

        GoogleTransportInfo googleTransportInfo = new GoogleTransportInfo();
        HttpClient client = new DefaultHttpClient();

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

        URI uri = new URI(
                "https",
                "maps.googleapis.com",
                "/maps/api/distancematrix/json",
                          "origins=" + googleMapRequest.homeAddress
                        + "&destinations=" + googleMapRequest.officeAddress
                        + "&departure_time=" + departureTime + "&mode=" + transitMode + "&key=" + GOOGLE_DISTANCE_MATRIX_API_KEY,
                null);

        String httpRequest = uri.toASCIIString();

        LOGGER.debug("--- HTTP request = {}" + httpRequest);

        HttpGet request = new HttpGet(httpRequest);

        try {
            HttpResponse response = client.execute(request);
            LOGGER.debug("--- HTTP response = {}" + response);

            // CONVERT RESPONSE TO STRING
            String stringResult = EntityUtils.toString(response.getEntity());
            LOGGER.debug("--- stringResult = {}" + stringResult);

            JSONObject jsonObject = new JSONObject(stringResult);
            LOGGER.debug("--- jsonObject = {}" + jsonObject);

            // only process response if google was able to process the request

            if ("OK".equals(jsonObject.get("status"))) {
                // CONVERT STRING TO JSON ARRAY
                JSONArray jsonArrayRow = jsonObject.getJSONArray("rows");

                for (int i = 0; i < jsonArrayRow.length(); i++) {
                    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                    JSONObject jsonobject2 = jsonArrayRow.getJSONObject(i);

                    JSONArray jsonArrayElement = jsonobject2.getJSONArray("elements");
                    for (int j = 0; j < jsonArrayElement.length(); j++) {
                        JSONObject jsonElement = jsonArrayElement.getJSONObject(j);
                        LOGGER.debug("---google distance = {0}", jsonElement.getJSONObject("distance") == null ? 0 : jsonElement.getJSONObject("distance").get("value"));

                        googleTransportInfo.distance = jsonElement.opt("distance") == null ? 0 : (Integer) jsonElement.getJSONObject("distance").get("value");
                        // when we drive, we use duration_in_traffic to get a more realistic duration
                        if (DRIVING.equals(transitMode)) {
                            LOGGER.debug("---google duration = {0}", jsonElement.getJSONObject("duration_in_traffic") == null ? 0 : jsonElement.getJSONObject("duration_in_traffic").get("value"));
                            googleTransportInfo.duration = jsonElement.opt("duration_in_traffic") == null ? 0 : (Integer) jsonElement.getJSONObject("duration_in_traffic").get("value");
                        }
                        else {
                            LOGGER.debug("---google duration = {0}", jsonElement.getJSONObject("duration") == null ? 0 : jsonElement.getJSONObject("duration").get("value"));
                            googleTransportInfo.duration = jsonElement.opt("duration") == null ? 0 : (Integer) jsonElement.getJSONObject("duration").get("value");
                        }
                    }
                }
            }
            else {
                LOGGER.error(DynaRouteServiceConstants.LOG_ERROR + "Google returns an error: status {}", jsonObject.get("status"));
            }

        } catch (Throwable e) {
            LOGGER.error(DynaRouteServiceConstants.LOG_ERROR + "Exception occurred when querying Google Maps: message = {}, mode = {}, request = {}", e.getMessage(), transitMode, googleMapRequest);
            return googleTransportInfo;
        }

        LOGGER.info(DynaRouteServiceConstants.LOG_ENDING + "mode = {}, request = {}, googleTransportInfo = {}", transitMode, googleMapRequest, googleTransportInfo);
        return googleTransportInfo;
    }

}
