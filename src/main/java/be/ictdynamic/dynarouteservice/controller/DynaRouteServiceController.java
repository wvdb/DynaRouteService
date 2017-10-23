package be.ictdynamic.dynarouteservice.controller;

import be.ictdynamic.dynarouteservice.DynaRouteServiceConstants;
import be.ictdynamic.dynarouteservice.domain.*;
import be.ictdynamic.dynarouteservice.services.google_service.GoogleServiceImpl;
import be.ictdynamic.dynarouteservice.domain.SystemParameterRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
public class DynaRouteServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynaRouteServiceController.class);
    private static final String TEMPLATE = "You are from %s!";
    private static final AtomicLong COUNTER = new AtomicLong();

    @Autowired
    private Dummy dummy;

    @Autowired
    private GoogleServiceImpl googleService;

    @Autowired
    private SystemParameterConfig systemParameterConfig;

    @ApiOperation(value = "Test method to verify whether the service is up and running.",
            notes = "Accepts commune as a parameter and includes it in the response-message.")
    @RequestMapping(value = "/greeting",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity greeting(@RequestParam(value = "commune", required = true, defaultValue = "Edegem") String commune) {
        String greetingText = String.format(TEMPLATE, commune);
        LOGGER.info(DynaRouteServiceConstants.LOG_STARTING + " Greeting, text = " + greetingText);

        Dummy dummy1 = new Dummy("wim", "van den brande");
        Dummy dummy2 = new Dummy("kaat", "frison");
        Dummy dummy3 = new Dummy("donald", "trump");
        List<Dummy> dummies = new ArrayList<>();
        dummies.add(dummy1);
        dummies.add(dummy2);
        dummies.add(dummy3);
        List<String> voornamen1 = dummies.stream().map(dummy -> dummy.getVoornaam()).collect(Collectors.toList());
        List<String> voornamen2 = dummies.stream().map(Dummy::getVoornaam).collect(Collectors.toList());

        LOGGER.info(DynaRouteServiceConstants.LOG_ENDING + " Greeting");
        return ResponseEntity.ok(new Greeting(COUNTER.incrementAndGet(), greetingText));
    }

    @ApiOperation(value = "Business method to retrieve distances and duration when driving/walking/bicycling/using public transport.",
            notes = "Distances is in metres, duration is in seconds.")
    @RequestMapping(value = "/route",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
//            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
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

    @ApiOperation(value = "Admin method to retrieve all the system parameters of the DynaRouteService application.",
            notes = "This method may be executed by authorized personnel only.")
    @RequestMapping(value = "/systemParameters",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity retrieveSystemParameters() {
        return ResponseEntity.ok(systemParameterConfig.getSystemParameters());
    }

    @ApiOperation(value = "Admin method to update the value of a system parameter used by the DynaRouteService application.",
            notes = "This method may be executed by authorized personnel only.")
    @RequestMapping(value = "/systemParameters",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity updateSystemParameter(@Valid @RequestBody SystemParameterRequest request) {
        if (systemParameterConfig.getSystemParameters().containsKey(request.getParameterKey())) {
            systemParameterConfig.getSystemParameters().put(request.getParameterKey(), request.getParameterValue());
            return ResponseEntity.ok(null);
        }
        else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("%s is not a valid parameter key.", request.getParameterKey()));
            SystemParameterResponse systemParameterResponse = new SystemParameterResponse(String.format("%s is not a valid parameter key.", request.getParameterKey()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(systemParameterResponse);
        }
    }

}
