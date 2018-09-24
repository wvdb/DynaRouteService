package be.ictdynamic.mobiscan.controller;

import be.ictdynamic.mobiscan.domain.TransportRequest;
import be.ictdynamic.mobiscan.domain.TransportResponseFastestSlowest;
import be.ictdynamic.mobiscan.services.GoogleServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
public class PersistRequestControllerOld {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistRequestControllerOld.class);

    @Autowired
    private GoogleServiceImpl googleService;

    @ApiOperation(value = "Business method to retrieve distances and duration when driving/walking/bicycling/using public transport.",
            notes = "Distances is in metres, duration is in seconds.")
    @RequestMapping(value = "/route",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity handleRoute(
              @ApiParam(value = "Partial or complete Home address. Example: Tweebunder 4, Edegem, België")
              @RequestParam(value = "homeAddress", required = true) String homeAddress
            , @ApiParam(value = "Partial or complete Office address. Example: Da Vincilaan 5, Zaventem, België")
              @RequestParam(value = "officeAddress", required = true) String officeAddress
            , @ApiParam(value = "Optional time of departure in dd/MM/yyyy HH:mm:ss format. Example: 01/01/2020 17:00:00.")
              @RequestParam(value = "departureTime", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Date departureTime) {
        TransportRequest transportRequest = new TransportRequest(officeAddress, homeAddress, departureTime);
        if (departureTime != null && departureTime.before(new Date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Departure Time must not be in the past.");
        } else {
            return ResponseEntity.ok(googleService.processRouteRequest(transportRequest));
        }
    }

    @ApiOperation(value = "Business method to retrieve shortest and fastest route when driving.",
            notes = "Duration is in seconds.")
    @RequestMapping(value = "/fastestAndSlowestRoute",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity handleFastestAndSlowestRoute(
            @ApiParam(value = "Partial or complete Home address. Example: Tweebunder 4, Edegem, België")
            @RequestParam(value = "homeAddress", required = true) String homeAddress
            , @ApiParam(value = "Partial or complete Office address. Example: Da Vincilaan 5, Zaventem, België")
            @RequestParam(value = "officeAddress", required = true) String officeAddress
            , @ApiParam(value = "Time of departure in dd/MM/yyyy HH:mm:ss format. Example: 01/01/2020 17:00:00.")
            @RequestParam(value = "departureTime", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Date departureTime
            , @ApiParam(value = "Number of departureTime the be processed. Default = 336 (2 per hour, 24 hours, 7 days)")
            @RequestParam(value = "numberOfDepartureTimesToBeProcessed", required = false, defaultValue = "336") Integer numberOfDepartureTimesToBeProcessed) {
        TransportRequest transportRequest = new TransportRequest(officeAddress, homeAddress, departureTime, numberOfDepartureTimesToBeProcessed);
        if (departureTime.before(new Date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Departure Time must not be in the past.");
        } else {
            TransportResponseFastestSlowest transportResponseFastestSlowest = googleService.getGoogleDistanceFastestAndSlowest(transportRequest);

            // only return 5 fastest and 5 slowest routes
            Collections.sort(transportResponseFastestSlowest.getRoutes(), (route1, route2) -> route1.getRouteDuration().compareTo(route2.getRouteDuration()));
            transportResponseFastestSlowest.setFastestRoutes(transportResponseFastestSlowest.getRoutes().stream().limit(5).collect(Collectors.toList()));

            Collections.sort(transportResponseFastestSlowest.getRoutes(), (route1, route2) -> route2.getRouteDuration().compareTo(route1.getRouteDuration()));
            transportResponseFastestSlowest.setSlowestRoutes(transportResponseFastestSlowest.getRoutes().stream().limit(5).collect(Collectors.toList()));

            transportResponseFastestSlowest.setRoutes(null);

            return ResponseEntity.ok(transportResponseFastestSlowest);
        }
    }

}
