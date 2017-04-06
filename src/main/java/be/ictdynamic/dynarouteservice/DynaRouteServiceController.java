package be.ictdynamic.dynarouteservice;

import be.ictdynamic.dynarouteservice.domain.Dummy;
import be.ictdynamic.dynarouteservice.domain.Greeting;
import be.ictdynamic.dynarouteservice.domain.SystemParameterConfig;
import be.ictdynamic.dynarouteservice.domain.TransportRequest;
import be.ictdynamic.dynarouteservice.services.google_service.GoogleServiceImpl;
import generated.SystemParameterRequest;
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

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

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
              @ApiParam(value = "Partial or complete Home address. Example: Tweebunder 4, Edegem, Belgi�")
              @RequestParam(value = "homeAddress", required = true) String homeAddress
            , @ApiParam(value = "Partial or complete Office address. Example: Da Vincilaan 5, Zaventem, Belgi�")
              @RequestParam(value = "officeAddress", required = true) String officeAddress
            , @ApiParam(value = "Optional time of departure in dd/MM/yyyy HH:mm:ss format. Example: 01/01/2020 17:00:00.")
              @RequestParam(value = "departureTime", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Date departureTime) {
        TransportRequest transportRequest = new TransportRequest(officeAddress, homeAddress, departureTime);
        if (departureTime != null && departureTime.before(new Date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Departure Time must not be in the past.");
        } else {
            return ResponseEntity.ok(googleService.getGoogleDistance(transportRequest));
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
    public ResponseEntity updateSystemParameter(@RequestBody SystemParameterRequest request) {
        if (systemParameterConfig.getSystemParameters().containsKey(request.getParameterKey())) {
            systemParameterConfig.getSystemParameters().put(request.getParameterKey(), request.getParameterValue());
            return ResponseEntity.ok(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key of parameter is invalid.");
        }
    }

}
