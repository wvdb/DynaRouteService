package be.ictdynamic.dynarouteservice;

import be.ictdynamic.dynarouteservice.domain.DynaRouteServiceResponse;
import be.ictdynamic.dynarouteservice.domain.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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

    @RequestMapping(value = "/greeting",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Greeting greeting(@RequestParam(value = "commune", defaultValue = "Edegem") String commune) {
        String greetingText = String.format(TEMPLATE, commune);
        LOGGER.info(DynaRouteServiceConstants.LOG_STARTING + " Greeting, text = " + greetingText);
        LOGGER.info(DynaRouteServiceConstants.LOG_ENDING + " Greeting");
        return new Greeting(COUNTER.incrementAndGet(), greetingText);
    }

    @RequestMapping(value = "/route",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
//            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public DynaRouteServiceResponse handleGetRequest(
              @RequestParam(value = "ipAddress", required = true) String ipAddress
            , @RequestParam(value = "customerId", required = true) String customerId
            , @RequestParam(value = "platform", required = false) String platform
            , @RequestParam(value = "identifier", required = false) String identifier) {

        DynaRouteServiceResponse response = new DynaRouteServiceResponse();
        response.dummy1 = "this is a test";

        return response;
    }

}
