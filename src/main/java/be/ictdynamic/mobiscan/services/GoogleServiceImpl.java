package be.ictdynamic.mobiscan.services;

import be.ictdynamic.mobiscan.MobiscanConstants;
import be.ictdynamic.mobiscan.domain.GoogleDistanceMatrixResponse;
import be.ictdynamic.mobiscan.domain.MobiscanRequest;
import be.ictdynamic.mobiscan.enums.GoogleDistanceMatrixTransitMode;
import be.ictdynamic.mobiscan.utilities.DateUtility;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

//    @Override
//    public TransportResponse processRouteRequest(final TransportRequest transportRequest) {
//        TransportResponse transportResponse = new TransportResponse();
//        HashMap<String, TransportInfo> googleTransportInfoMap = new HashMap<>();
//        transportResponse.setTransportInfoMap(new HashMap<>());
//
//        List<String> transitModes = Arrays.asList(DRIVING, WALKING, BICYCLING, TRANSIT);
//
//        transitModes.forEach(transitMode -> {
//            try {
//                long departTimeLong;
//                // if departureTime is unknown, we use system date as default
//                if (transportRequest.getDepartureTime() == null) {
//                    departTimeLong = System.currentTimeMillis() / 1000;
//                }
//                else {
//                    departTimeLong = transportRequest.getDepartureTime().getTime() / 1000;
//                }
//                TransportInfo transportInfo = this.getGoogleDistanceBasedOnTransportModeAndTime(transportRequest, transitMode);
//                googleTransportInfoMap.put(transitMode, transportInfo);
//            } catch (URISyntaxException e) {
//                LOGGER.error(MobiscanConstants.LOG_ERROR + "Exception occurred when invoking getGoogleDistanceBasedOnTransportModeAndTime : message = {}, mode = {}, request = {}", e.getMessage(), transitMode, transportRequest);
//            }
//        });
//
//        // set map with 4 different transit-modes
//        transportResponse.setTransportInfoMap(googleTransportInfoMap);
//
////        Map<String, Double> mapLatLng = this.getLatitudeLongitudeFromGoogle(transportRequest.getHomeAddress());
////
////        // set latitude and longitude
////        if (mapLatLng != null && mapLatLng.get("lat") != null && mapLatLng.get("lng") != null) {
////            transportResponse.setLat(mapLatLng.get("lat"));
////            transportResponse.setLng(mapLatLng.get("lng"));
////
////            // lat/lon is known so let's retrieve the weather info for this particular Date and let's set it
////            if (transportRequest.getDepartureTime() != null) {
////                transportResponse.setMapWeather(this.getInfoFromOpenWeatherMap(transportRequest.getHomeAddress(), mapLatLng, transportRequest.getDepartureTime()));
////            }
////        }
//
//        return transportResponse;
//    }

    private Map<String, Double> getInfoFromOpenWeatherMap(String address, Map<String, Double> mapLatLng, Date departureTime) {
        LOGGER.info(MobiscanConstants.LOG_STARTING + "getting info from OpenWeatherMap for address = {}, departureTime {}.", address, departureTime);

        // http://openweathermap.org/forecast5

        HttpClient httpClient = new DefaultHttpClient();
        Map<String, Double> mapWithWeatherInfo = new HashMap<>();

//        http://api.openweathermap.org/data/2.5/forecast?lat=51.1500242&lon=4.4584652&APPID=97fdf5ad61c66373bf9e7c0134e256de
//        try {
//            URI uri = new URI(
//                    "http",
//                    "api.openweathermap.org",
//                    "/data/2.5/forecast",
//                    "lat=" + mapLatLng.get("lat") + "&lon=" + mapLatLng.get("lng") + "&APPID=97fdf5ad61c66373bf9e7c0134e256de",
//                    null);
//
//            String httpRequest = uri.toASCIIString();
//            HttpGet request = new HttpGet(httpRequest);
//            HttpResponse httpResponse = httpClient.execute(request);
//
//            // CONVERT RESPONSE TO STRING
//            String stringResult = EntityUtils.toString(httpResponse.getEntity());
//            LOGGER.debug("--- stringResult = {}", stringResult);
//
//            JSONObject jsonObject = new JSONObject(stringResult);
//            LOGGER.debug("--- jsonObject = {}", jsonObject);
//
//            // only process response if cnt > 0
//
//            if ((Integer)jsonObject.get("cnt") > 0) {
//                // CONVERT STRING TO JSON ARRAY
//                JSONArray lists = jsonObject.getJSONArray("list");
//
//                boolean mathingDate = false;
//
//                for (int i = 0; i < lists.length() & mathingDate == false; i++) {
//                    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
//                    JSONObject list = lists.getJSONObject(i);
//
//                    Integer forecastDateTimeAsInteger = (Integer) list.get("dt");
//
//                    // we stop processing once we heave a weather list with a forecastDateTime that exceeds our departure-time
//                    if ((((long) forecastDateTimeAsInteger) * 1000) > DateUtility.convertDateToEpoch(departureTime)) {
//                        mathingDate = true;
//
//                        JSONObject rain = list.getJSONObject("rain");
//                        JSONObject snow = list.opt("snow") == null ? null : list.getJSONObject("snow");
//                        Double rainVolumeForLast3H = rain == null ? 0 : (Double) rain.get("3h");
//                        Double snowVolumeForLast3H = snow == null ? 0 : (Double) snow.get("3h");
//
//                        mapWithWeatherInfo.put("rain", rainVolumeForLast3H);
//                        mapWithWeatherInfo.put("snow", snowVolumeForLast3H);
//                    }
//                }
//            } else {
//                LOGGER.error(MobiscanConstants.LOG_ERROR + "openweathermap cnt is <= 0 : {}", jsonObject.get("cnt"));
//            }
//
//        } catch (Throwable e) {
//            LOGGER.error(MobiscanConstants.LOG_ERROR + "Exception occurred when using openweathermap: address = {}, exception = {}", address, e);
//            return mapWithWeatherInfo;
//        }
//
//        LOGGER.info(MobiscanConstants.LOG_ENDING + "address {}, departureTime {} has rain {} and snow {}.", address, departureTime, mapWithWeatherInfo.get("lat"), mapWithWeatherInfo.get("lng"));
        return mapWithWeatherInfo;
    }

    private Map<String, Double> getLatitudeLongitudeFromGoogle(String address) {
        LOGGER.info(MobiscanConstants.LOG_STARTING + "getting LatitudeLongitudeFromGoogle for address = {}", address);

        // Geocoding API = https://developers.google.com/maps/documentation/geocoding/intro

        HttpClient httpClient = new DefaultHttpClient();
        Map<String, Double> mapWithLatAndLng = new HashMap<>();

        try {
            URI uri = new URI(
                    "https",
                    "maps.googleapis.com",
                    "/maps/api/geocode/json",
//                    "address=" + address + "&key=" + GOOGLE_DISTANCE_MATRIX_API_KEY,
                    "address=" + address,
                    null);

            String httpRequest = uri.toASCIIString();
            HttpGet request = new HttpGet(httpRequest);
            HttpResponse httpResponse = httpClient.execute(request);

            // CONVERT RESPONSE TO STRING
            String stringResult = EntityUtils.toString(httpResponse.getEntity());
            LOGGER.debug("--- stringResult = {}", stringResult);

//            JSONObject jsonObject = new JSONObject(stringResult);
//            LOGGER.debug("--- jsonObject = {}", jsonObject);

            // only process response if google was able to process the request

//            if ("OK".equals(jsonObject.get("status"))) {
//                // CONVERT STRING TO JSON ARRAY
//                JSONArray results = jsonObject.getJSONArray("results");
//
//                for (int i = 0; i < results.length(); i++) {
//                    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
//                    JSONObject result = results.getJSONObject(i);
//
//                    JSONObject geometry = result.getJSONObject("geometry");
//                    JSONObject location = geometry.getJSONObject("location");
//                    Double lat = (Double) location.get("lat");
//                    Double lng = (Double) location.get("lng");
//
//                    mapWithLatAndLng.put("lat", lat);
//                    mapWithLatAndLng.put("lng", lng);
//                }
//            } else {
//                LOGGER.error(MobiscanConstants.LOG_ERROR + "Google returns an error: status {}", jsonObject.get("status"));
//            }

        } catch (Throwable e) {
            LOGGER.error(MobiscanConstants.LOG_ERROR + "Exception occurred when geocoding: address = {}", address);
            return mapWithLatAndLng;
        }

        LOGGER.info(MobiscanConstants.LOG_ENDING + "address {} has lat {} and lng {}.", address, mapWithLatAndLng.get("lat"), mapWithLatAndLng.get("lng"));
        return mapWithLatAndLng;
    }

//    public TransportResponseFastestSlowest getGoogleDistanceFastestAndSlowest(final TransportRequest transportRequest) {
//        TransportResponseFastestSlowest transportResponseFastestSlowest = new TransportResponseFastestSlowest();
//        transportResponseFastestSlowest.setFastestRoutes(new ArrayList<>(5));
//        transportResponseFastestSlowest.setSlowestRoutes(new ArrayList<>(5));
//        transportResponseFastestSlowest.setRoutes(new ArrayList<>(336));
//
//        List<String> transitModes = Collections.singletonList(DRIVING);
//
//        transitModes.forEach(transitMode -> {
//            long departTimeLong;
//            departTimeLong = transportRequest.getDepartureTime().getTime() / 1000;
//            for (int i = 1; i <= transportRequest.getNumberOfDepartureTimesToBeProcessed(); i++) {
//                TransportInfo transportInfo = null;
//                try {
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//                    String routeDateAsString = sdf.format(new Date(departTimeLong * 1000));
//                    transportInfo = this.getGoogleDistanceBasedOnTransportModeAndTime(transportRequest, transitMode, departTimeLong, routeDateAsString);
//
//                    TransportResponseFastestSlowest.TransportResponseDetailsFastestSlowest transportResponseDetailsFastestSlowest = new TransportResponseFastestSlowest.TransportResponseDetailsFastestSlowest(new Date(departTimeLong), routeDateAsString, transportInfo.getDuration());
//                    transportResponseFastestSlowest.getRoutes().add(transportResponseDetailsFastestSlowest);
//
//                    // increase departure Time with 1800 seconds for next processing
//                    departTimeLong += 1800;
//                } catch (URISyntaxException e) {
//                    LOGGER.error(MobiscanConstants.LOG_ERROR + "Exception occurred when invoking getGoogleDistanceBasedOnTransportModeAndTime : message = {}, mode = {}, request = {}", e.getMessage(), transitMode, transportRequest);
//                }
//            }
//        });
//
//        return transportResponseFastestSlowest;
//    }

    private GoogleDistanceMatrixResponse.GoogleDistanceMatrixResponseDetail getGoogleDistanceBasedOnTransportModeAndTime(final MobiscanRequest mobiscanRequest, final String transitMode) throws Exception {
        LOGGER.debug(MobiscanConstants.LOG_STARTING + "mode = {}, fromAddress = {}, toAddress = {}, departureTime = {}", transitMode, mobiscanRequest.getLocationFrom(), mobiscanRequest.getLocationTo(), mobiscanRequest.getDepartureDate());

        // Google Distance Matrix API
        // https://developers.google.com/maps/documentation/distance-matrix/start
        // https://developers.google.com/maps/documentation/distance-matrix/intro

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

        GoogleDistanceMatrixResponse.GoogleDistanceMatrixResponseDetail googleDistanceMatrixResponseDetail = new GoogleDistanceMatrixResponse.GoogleDistanceMatrixResponseDetail();

        HttpClient httpClient = new DefaultHttpClient();

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

//        try {
//            String stringResult;
//
//            if (!isTestMode) {
//                HttpResponse httpResponse = httpClient.execute(request);
//                LOGGER.debug("--- httpResponse = {}", httpResponse);
//
//                // CONVERT RESPONSE TO STRING
//                stringResult = EntityUtils.toString(httpResponse.getEntity());
//            }
//            else {
//                short index = (short) Integer.parseInt(mobiscanRequest.getEmployeeId());
//                if (Integer.parseInt(mobiscanRequest.getEmployeeId()) > 1) {
//                    index = 0;
//                }
//                stringResult = MobiscanConstants.DISTANCE_MATRIX_TEST_RESPONSE[index];
//            }

//            LOGGER.debug("--- stringResult = {}", stringResult);
//            JSONObject jsonObject = new JSONObject(stringResult);
//            LOGGER.debug("--- jsonObject = {}", jsonObject);
//
//            // only process response if google was able to process the request
//
//            if ("OK".equals(jsonObject.get("status"))) {
//                // CONVERT STRING TO JSON ARRAY
//                JSONArray jsonArrayRow = jsonObject.getJSONArray("rows");
//
//                for (int i = 0; i < jsonArrayRow.length(); i++) {
//                    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
//                    JSONObject jsonObject2 = jsonArrayRow.getJSONObject(i);
//
//                    JSONArray jsonArrayElement = jsonObject2.getJSONArray("elements");
//                    for (int j = 0; j < jsonArrayElement.length(); j++) {
//                        JSONObject jsonElement = jsonArrayElement.getJSONObject(j);
//                        LOGGER.debug("---google distance = {}", jsonElement.getJSONObject("distance") == null ? 0 : jsonElement.getJSONObject("distance").get("value"));
//
//                        googleDistanceMatrixResponseDetail.setDistance(jsonElement.opt("distance") == null ? 0 : (Integer) jsonElement.getJSONObject("distance").get("value"));
//                        // when we drive, we use duration_in_traffic (if available) to get a more realistic duration
//                        if (GoogleDistanceMatrixTransitMode.DRIVING.getTransitMode().equals(transitMode) && jsonElement.opt("duration_in_traffic") != null) {
//                            LOGGER.debug("---google duration_in_traffic in seconds = {}", jsonElement.opt("duration_in_traffic") == null ? 0 : jsonElement.getJSONObject("duration_in_traffic").get("value"));
//                            googleDistanceMatrixResponseDetail.setDuration(jsonElement.opt("duration_in_traffic") == null ? 0 : (Integer) jsonElement.getJSONObject("duration_in_traffic").get("value"));
//                        }
//                        else {
//                            LOGGER.debug("---google duration in seconds = {}", jsonElement.opt("duration") == null ? 0 : jsonElement.getJSONObject("duration").get("value"));
//                            googleDistanceMatrixResponseDetail.setDuration(jsonElement.opt("duration") == null ? 0 : (Integer) jsonElement.getJSONObject("duration").get("value"));
//                        }
//                    }
//                }
//            }
//            else {
//                LOGGER.error(MobiscanConstants.LOG_ERROR + "Google returns an error: status {}", jsonObject.get("status"));
//            }
//
//        } catch (IOException e ) {
//            LOGGER.error(MobiscanConstants.LOG_ERROR + "Exception occurred when querying Google Maps: message = {}, mode = {}, request = {}", e.getMessage(), transitMode, mobiscanRequest);
//            throw e;
//        }

        LOGGER.debug(MobiscanConstants.LOG_ENDING + "mode = {}, request = {}, googleDistanceMatrixResponseDetail = {}", transitMode, mobiscanRequest, googleDistanceMatrixResponseDetail);
        return googleDistanceMatrixResponseDetail;
    }

}
