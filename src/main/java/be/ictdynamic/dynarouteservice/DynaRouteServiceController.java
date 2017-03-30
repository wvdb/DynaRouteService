package be.ictdynamic.dynarouteservice;

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

    @RequestMapping(value = "/greeting", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Greeting greeting(@RequestParam(value = "commune", defaultValue = "Edegem") String commune) {
        return new Greeting(COUNTER.incrementAndGet(), String.format(TEMPLATE, commune));
    }


}
