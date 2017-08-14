package be.ictdynamic.dynarouteservice.services.google_service;

import be.ictdynamic.dynarouteservice.DynaRouteServiceConstants;
import be.ictdynamic.dynarouteservice.domain.TransportInfo;
import be.ictdynamic.dynarouteservice.domain.TransportRequest;
import be.ictdynamic.dynarouteservice.domain.TransportResponse;
import be.ictdynamic.dynarouteservice.domain.TransportResponseFastestSlowest;
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
import java.text.SimpleDateFormat;
import java.util.*;

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

        // part  1 - retrieve duration and distance for 4 different modes

        transitModes.forEach(transitMode -> {
            TransportInfo transportInfo = null;
            try {
                long departTimeLong;
                // if departureTime is unknown, we use system date as default
                if (transportRequest.getDepartureTime() == null) {
                    departTimeLong = System.currentTimeMillis();
                }
                else {
                    departTimeLong = transportRequest.getDepartureTime().getTime();
                }

                transportInfo = this.getGoogleDistanceBasedOnTransportModeAndTime(transportRequest, transitMode, departTimeLong);
                googleTransportInfoMap.put(transitMode, transportInfo);
            } catch (URISyntaxException e) {
                LOGGER.error(DynaRouteServiceConstants.LOG_ERROR + "Exception occurred when invoking getGoogleDistance : message = {}, mode = {}, request = {}", e.getMessage(), transitMode, transportRequest);
            }
        });

        transportResponse.setTransportInfoMap(googleTransportInfoMap);

        // part  2 - retrieve latitude and longitude of Home Address

        Map<String, Double> mapWithLatitudeAndLongitude = transportResponse.getMapWithLatitudeAndLongitude();

        try {
            Map<String, Double> mapWithLatitudeAndLongitudeOfHomeAddress = this.getLatitudeAndLongitude(transportRequest.getHomeAddress());
            mapWithLatitudeAndLongitude.put("address1.lat", mapWithLatitudeAndLongitudeOfHomeAddress.get("lat"));
            mapWithLatitudeAndLongitude.put("address1.lng", mapWithLatitudeAndLongitudeOfHomeAddress.get("lng"));
        } catch (URISyntaxException e) {
            LOGGER.error(DynaRouteServiceConstants.LOG_ERROR + "Exception occurred when invoking getGoogleDistance : message = {}, mode = {}, address = {}", e.getMessage(), transportRequest.getHomeAddress());
        }

        // part  3 - retrieve latitude and longitude of Office Address

        try {
            Map<String, Double> mapWithLatitudeAndLongitudeOfHomeAddress = this.getLatitudeAndLongitude(transportRequest.getOfficeAddress());
            mapWithLatitudeAndLongitude.put("address2.lat", mapWithLatitudeAndLongitudeOfHomeAddress.get("lat"));
            mapWithLatitudeAndLongitude.put("address2.lng", mapWithLatitudeAndLongitudeOfHomeAddress.get("lng"));
        } catch (URISyntaxException e) {
            LOGGER.error(DynaRouteServiceConstants.LOG_ERROR + "Exception occurred when invoking getGoogleDistance : message = {}, mode = {}, address = {}", e.getMessage(), transportRequest.getHomeAddress());
        }

        transportResponse.setMapWithLatitudeAndLongitude(mapWithLatitudeAndLongitude);

        return transportResponse;
    }

    public TransportResponseFastestSlowest getGoogleDistanceFastestAndSlowest(final TransportRequest transportRequest) {
        TransportResponseFastestSlowest transportResponseFastestSlowest = new TransportResponseFastestSlowest();
        transportResponseFastestSlowest.setFastestRoutes(new ArrayList<>(5));
        transportResponseFastestSlowest.setSlowestRoutes(new ArrayList<>(5));
        transportResponseFastestSlowest.setRoutes(new ArrayList<>(336));

        List<String> transitModes = Collections.singletonList(DRIVING);

        transitModes.forEach(transitMode -> {
            long departTimeLong = transportRequest.getDepartureTime().getTime();

            for (int i = 1; i <= transportRequest.getNumberOfDepartureTimesToBeProcessed(); i++) {
                TransportInfo transportInfo = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String routeDateAsString = sdf.format(transportRequest.getDepartureTime().getTime());
                    transportInfo = this.getGoogleDistanceBasedOnTransportModeAndTime(transportRequest, transitMode, departTimeLong);

                    TransportResponseFastestSlowest.TransportResponseDetailsFastestSlowest transportResponseDetailsFastestSlowest = new TransportResponseFastestSlowest.TransportResponseDetailsFastestSlowest(new Date(departTimeLong), routeDateAsString, transportInfo.getDuration(), transportInfo.getDurationAsText());
                    transportResponseFastestSlowest.getRoutes().add(transportResponseDetailsFastestSlowest);

                    // increase departure Time with 1800 seconds / half an hour for next processing
                    departTimeLong += 1800 * 1000;
                } catch (URISyntaxException e) {
                    LOGGER.error(DynaRouteServiceConstants.LOG_ERROR + "Exception occurred when invoking getGoogleDistanceBasedOnTransportModeAndTime : message = {}, mode = {}, request = {}", e.getMessage(), transitMode, transportRequest);
                }
            }
        });

        return transportResponseFastestSlowest;
    }

    public TransportResponseFastestSlowest getFastestAndSlowestRouteForEachDayOfTheWeek(final TransportRequest transportRequest, Integer numberOfDaysToBeProcessed, Integer granularityInMinutes) {
        TransportResponseFastestSlowest transportResponseFastestSlowest = new TransportResponseFastestSlowest();

        List<String> transitModes = Collections.singletonList(DRIVING);

        transitModes.forEach(transitMode -> {
            long departTimeLong;
            departTimeLong = transportRequest.getDepartureTime().getTime();
            for (int day = 1; day <= numberOfDaysToBeProcessed; day++) {
                // processing per day
                transportResponseFastestSlowest.setRoutes(new ArrayList<>(48));
                // retrieve fastest route for every timeslot
                // granularityInMinutes = 30 => # timeslots = 48
                // granularityInMinutes = 60 => # timeslots = 24
                // granularityInMinutes = 120 => # timeslots = 12
                for (int timeSlotCounter = 1; timeSlotCounter <= (24 * 60) / granularityInMinutes; timeSlotCounter++) {
                    TransportInfo transportInfo = null;
                    try {
                        // TODO : to make more flexible (now we skip first 16 quarters and last 16 quarters)
                        if (timeSlotCounter>24 && timeSlotCounter<72) {
                            transportInfo = this.getGoogleDistanceBasedOnTransportModeAndTime(transportRequest, transitMode, departTimeLong);

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            String routeDateAsString = sdf.format(new Date(departTimeLong * 1000));

                            TransportResponseFastestSlowest.TransportResponseDetailsFastestSlowest transportResponseDetailsFastestSlowest = new TransportResponseFastestSlowest.TransportResponseDetailsFastestSlowest(new Date(departTimeLong), routeDateAsString, transportInfo.getDuration(), transportInfo.getDurationAsText());
                            transportResponseFastestSlowest.getRoutes().add(transportResponseDetailsFastestSlowest);
                        }

                        // increase departure Time with X seconds / for next processing
                        // example : increase departure Time with 1800 seconds / half an hour for next processing
                        departTimeLong += granularityInMinutes * 60 * 1000;
                    } catch (URISyntaxException e) {
                        LOGGER.error(DynaRouteServiceConstants.LOG_ERROR + "Exception occurred when invoking getFastestAndSlowestRouteForEachDayOfTheWeek : message = {}, mode = {}, request = {}", e.getMessage(), transitMode, transportRequest);
                    }
                }
                // end of the day
                // let's pick the fastest time-slot for this day
                Collections.sort(transportResponseFastestSlowest.getRoutes(), (route1, route2) -> route1.getRouteDuration().compareTo(route2.getRouteDuration()));
                transportResponseFastestSlowest.getFastestRoutesPerDay().put("Day " + day, transportResponseFastestSlowest.getRoutes().stream().limit(1).findFirst().get());
                // let's pick the slowest time-slot for this day
                Collections.sort(transportResponseFastestSlowest.getRoutes(), (route1, route2) -> route2.getRouteDuration().compareTo(route1.getRouteDuration()));
                transportResponseFastestSlowest.getSlowestRoutesPerDay().put("Day " + day, transportResponseFastestSlowest.getRoutes().stream().limit(1).findFirst().get());
            }
        });
        return transportResponseFastestSlowest;
    }

    private TransportInfo getGoogleDistanceBasedOnTransportModeAndTime(final TransportRequest transportRequest, final String transitMode, final long departureTime) throws URISyntaxException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String departureTimeAsString = sdf.format(new Date(departureTime));

        LOGGER.info(DynaRouteServiceConstants.LOG_STARTING + "mode = {}, request = {}, departureTimeAsString = {}", transitMode, transportRequest, departureTimeAsString);

        // Google Distance Matrix API:
        //   https://developers.google.com/maps/documentation/distance-matrix/start
        //   https://developers.google.com/maps/documentation/distance-matrix/intro

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

        String departureTimeParameter = transitMode.equals("transit") ? "" : "&departure_time=" + departureTime;
        String transitModeParameter = transitMode.equals("transit") ? "&train=" : "";

        URI uri = new URI(
                "https",
                "maps.googleapis.com",
                "/maps/api/distancematrix/json",
                "origins=" + transportRequest.getHomeAddress()
                        + "&destinations=" + transportRequest.getOfficeAddress()
                        + departureTimeParameter + "&mode=" + transitMode + transitModeParameter + "&key=" + GOOGLE_DISTANCE_MATRIX_API_KEY,
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
                        // when we drive, we use duration_in_traffic (if available) to get a more realistic duration
                        if (DRIVING.equals(transitMode)) {
                            Object googleDurationDriving = jsonElement.opt("duration_in_traffic") == null ? jsonElement.getJSONObject("duration").get("value") : jsonElement.getJSONObject("duration_in_traffic").get("value");
                            LOGGER.debug("---google duration in seconds (driving) = {}", googleDurationDriving);
                            transportInfo.setDuration((Integer) googleDurationDriving);
                            transportInfo.setDurationAsText(jsonElement.opt("duration_in_traffic") == null ? (String) jsonElement.getJSONObject("duration").get("text") : (String) jsonElement.getJSONObject("duration_in_traffic").get("text"));
                        }
                        else {
                            LOGGER.debug("---google duration in seconds = {}", jsonElement.opt("duration") == null ? 0 : jsonElement.getJSONObject("duration").get("value"));
                            transportInfo.setDuration(jsonElement.opt("duration") == null ? 0 : (Integer) jsonElement.getJSONObject("duration").get("value"));
                            transportInfo.setDurationAsText(jsonElement.opt("duration") == null ? "" : (String) jsonElement.getJSONObject("duration").get("text"));
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

    private Map<String, Double> getLatitudeAndLongitude(final String address) throws URISyntaxException {
        LOGGER.info(DynaRouteServiceConstants.LOG_STARTING + " getLatitudeAndLongitude address = {}", address);

        // Google GEOCoding API: https://developers.google.com/maps/documentation/geocoding/intro

        // example of a request:

//        https://maps.googleapis.com/maps/api/geocode/json?address=Tweebunder%204,%20Belgium

        // example of a response:

//        {
//            "results" : [
//            {
//                "address_components" : [
//                {
//                    "long_name" : "4",
//                        "short_name" : "4",
//                        "types" : [ "street_number" ]
//                },
//                {
//                    "long_name" : "Tweebunder",
//                        "short_name" : "Tweebunder",
//                        "types" : [ "route" ]
//                },
//                {
//                    "long_name" : "Edegem",
//                        "short_name" : "Edegem",
//                        "types" : [ "political", "sublocality", "sublocality_level_1" ]
//                },
//                {
//                    "long_name" : "Edegem",
//                        "short_name" : "Edegem",
//                        "types" : [ "locality", "political" ]
//                },
//                {
//                    "long_name" : "Antwerpen",
//                        "short_name" : "AN",
//                        "types" : [ "administrative_area_level_2", "political" ]
//                },
//                {
//                    "long_name" : "Vlaanderen",
//                        "short_name" : "Vlaanderen",
//                        "types" : [ "administrative_area_level_1", "political" ]
//                },
//                {
//                    "long_name" : "Belgium",
//                        "short_name" : "BE",
//                        "types" : [ "country", "political" ]
//                },
//                {
//                    "long_name" : "2650",
//                        "short_name" : "2650",
//                        "types" : [ "postal_code" ]
//                }
//                ],
//                "formatted_address" : "Tweebunder 4, 2650 Edegem, Belgium",
//                    "geometry" : {
//                "bounds" : {
//                    "northeast" : {
//                        "lat" : 51.1499616,
//                                "lng" : 4.4582731
//                    },
//                    "southwest" : {
//                        "lat" : 51.1499561,
//                                "lng" : 4.4582564
//                    }
//                },
//                "location" : {
//                    "lat" : 51.1499561,
//                            "lng" : 4.4582564
//                },
//                "location_type" : "RANGE_INTERPOLATED",
//                        "viewport" : {
//                    "northeast" : {
//                        "lat" : 51.15130783029151,
//                                "lng" : 4.459613730291502
//                    },
//                    "southwest" : {
//                        "lat" : 51.14860986970851,
//                                "lng" : 4.456915769708498
//                    }
//                }
//            },
//                "place_id" : "EiJUd2VlYnVuZGVyIDQsIDI2NTAgRWRlZ2VtLCBCZWxnacOr",
//                    "types" : [ "street_address" ]
//            }
//            ],
//            "status" : "OK"
//        }

        Map<String, Double> mapWithLatitudeAndLongitude = new HashMap<>();

        HttpClient httpClient = new DefaultHttpClient();

        URI uri = new URI(
                "https",
                "maps.googleapis.com",
                "/maps/api/geocode/json",
                "address=" + address,
                null);

        String httpRequest = uri.toASCIIString();

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
                JSONArray googleResults = jsonObject.getJSONArray("results");

                if (googleResults.length() > 0) {
                    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                    JSONObject firstGoogleResult = googleResults.getJSONObject(0);

                    Double lat = (Double) ((JSONObject)((JSONObject) firstGoogleResult.get("geometry")).get("location")).get("lat");
                    Double lng = (Double) ((JSONObject)((JSONObject) firstGoogleResult.get("geometry")).get("location")).get("lng");
                    mapWithLatitudeAndLongitude.put("lat", lat);
                    mapWithLatitudeAndLongitude.put("lng", lng);
                }
            }
            else {
                LOGGER.warn(DynaRouteServiceConstants.LOG_ERROR + "Google geocode returns an error: status {}", jsonObject.get("status"));
            }

        } catch (Throwable e) {
            LOGGER.error(DynaRouteServiceConstants.LOG_ERROR + "Exception occurred when invoking Google geocode: message = {}, address = {}", e.getMessage(), address);
            return mapWithLatitudeAndLongitude;
        }

        LOGGER.info(DynaRouteServiceConstants.LOG_ENDING + " getLatitudeAndLongitude address = {}", address);
        return mapWithLatitudeAndLongitude;
    }

}
