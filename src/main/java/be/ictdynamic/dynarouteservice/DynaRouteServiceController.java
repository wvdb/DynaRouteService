package be.ictdynamic.dynarouteservice;

import be.ictdynamic.dynarouteservice.domain.Dummy;
import be.ictdynamic.dynarouteservice.domain.GoogleMapRequest;
import be.ictdynamic.dynarouteservice.domain.GoogleMapResponse;
import be.ictdynamic.dynarouteservice.domain.Greeting;
import be.ictdynamic.dynarouteservice.services.google_service.GoogleMapServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class DynaRouteServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynaRouteServiceController.class);
    private static final String TEMPLATE = "You are from %s!";
    private static final AtomicLong COUNTER = new AtomicLong();

    @Autowired
    private Dummy dummy;

    @Autowired
    private GoogleMapServiceImpl googleMapService;

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

    @ApiOperation(value = "Business method to retrieve distances and duration when driving/walking/bicycling/using public transport",
            notes = "Distances is in metres, duration is in seconds.")
    @RequestMapping(value = "/route",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
//            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public GoogleMapResponse handleGetRequest(
              @RequestParam(value = "homeAddress", required = true) String homeAddress
            , @RequestParam(value = "officeAddress", required = true) String officeAddress) {

        GoogleMapRequest googleMapRequest = new GoogleMapRequest(officeAddress, homeAddress);

        return googleMapService.getGoogleDistance(googleMapRequest);
    }

}
