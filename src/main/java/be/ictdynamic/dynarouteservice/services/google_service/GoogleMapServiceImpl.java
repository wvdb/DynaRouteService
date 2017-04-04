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

    public GoogleMapResponse getGoogleDistance(final GoogleMapRequest googleMapRequest) {
        GoogleMapResponse googleMapResponse = new GoogleMapResponse();
        HashMap<String, GoogleTransportInfo> googleTransportInfoMap = new HashMap<>();
        googleMapResponse.setGoogleTransportInfoMap(new HashMap<>());

        List<String> transitModes = Arrays.asList("driving ", "walking", "bicycling", "transit");
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

        googleMapResponse.setGoogleTransportInfoMap(googleTransportInfoMap);
        return googleMapResponse;
    }

    private GoogleTransportInfo getGoogleDistanceBasedOnTransportModeAndTime(final GoogleMapRequest googleMapRequest, final String transitMode, final long departureTime) throws URISyntaxException {
        LOGGER.info(DynaRouteServiceConstants.LOG_STARTING + "mode = {}, request = {}", transitMode, googleMapRequest);

        GoogleTransportInfo googleTransportInfo = new GoogleTransportInfo();
        HttpClient client = new DefaultHttpClient();

        // url of Google Distance Matrix API = https://developers.google.com/maps/documentation/distance-matrix/intro#travel_modes

//        URI uri = new URI(
//                "https",
//                "maps.googleapis.com",
//                "/maps/api/distancematrix/json",
//                          "origins=" + googleMapRequest.homeAddress + ",+" + googleMapRequest.homeCommune + ",+" + googleMapRequest.homeCountry
//                        + "&destinations=" + googleMapRequest.officeAddress + ",+" + googleMapRequest.officeCommune + ",+" + googleMapRequest.officeCountry
//                        + "&departure_time=" + departureTime + "&mode=" + transitMode + "&key=" + GOOGLE_DISTANCE_MATRIX_API_KEY,
//                null);

        URI uri = new URI(
                "https",
                "maps.googleapis.com",
                "/maps/api/distancematrix/json",
                          "origins=" + googleMapRequest.homeAddress
                        + "&destinations=" + googleMapRequest.officeAddress
                        + "&departure_time=" + departureTime + "&mode=" + transitMode + "&key=" + GOOGLE_DISTANCE_MATRIX_API_KEY,
                null);

        String httpRequest = uri.toASCIIString();

        LOGGER.debug(">>>HTTP request = {}" + httpRequest);

        HttpGet request = new HttpGet(httpRequest);

        try {
            HttpResponse response = client.execute(request);
            LOGGER.debug(">>>HTTP response = {}" + response);

            // CONVERT RESPONSE TO STRING
            String stringResult = EntityUtils.toString(response.getEntity());
            LOGGER.debug(">>>HTTP stringResult = {}" + stringResult);

            JSONObject jsonobject1 = new JSONObject(stringResult);
            LOGGER.debug(">>>HTTP jsonobject1 = {}" + jsonobject1);

            // CONVERT STRING TO JSON ARRAY
            JSONArray jsonArrayRow = jsonobject1.getJSONArray("rows");

            for (int i = 0; i < jsonArrayRow.length(); i++) {
                // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                JSONObject jsonobject2 = jsonArrayRow.getJSONObject(i);

                JSONArray jsonArrayElement = jsonobject2.getJSONArray("elements");
                for (int j = 0; j < jsonArrayElement.length(); j++) {
                    JSONObject jsonElement = jsonArrayElement.getJSONObject(j);
                    LOGGER.debug("---google distance = {0}", jsonElement.getJSONObject("distance") == null ? 0 : jsonElement.getJSONObject("distance").get("value"));
                    LOGGER.debug("---google duration = {0}", jsonElement.getJSONObject("duration") == null ? 0 : jsonElement.getJSONObject("duration").get("value"));

                    googleTransportInfo.distance = jsonElement.opt("distance") == null ? 0 : (Integer) jsonElement.getJSONObject("distance").get("value");
                    googleTransportInfo.duration = jsonElement.opt("duration") == null ? 0 : (Integer) jsonElement.getJSONObject("duration").get("value");
                }
            }

        } catch (Throwable e) {
            LOGGER.error(DynaRouteServiceConstants.LOG_ERROR + "Exception occurred when querying Google Maps: message = {}, mode = {}, request = {}", e.getMessage(), transitMode, googleMapRequest);
            return googleTransportInfo;
        }

        LOGGER.info(DynaRouteServiceConstants.LOG_ENDING + "mode = {}, request = {}, response = {}", transitMode, googleMapRequest, googleTransportInfo);
        return googleTransportInfo;
    }

}
