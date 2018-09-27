package be.ictdynamic.mobiscan.controller;

import be.ictdynamic.mobiscan.domain.MobiscanRequest;
import be.ictdynamic.mobiscan.repository.MobiscanRequestRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@RestController
public class PersistRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistRequestController.class);

    @Autowired
    MobiscanRequestRepository mobiscanRequestRepository;

    @ApiOperation(value = "Method to persist Mobiscan Requests.",
            notes = "TBD.")
    @RequestMapping(value = "/mobiscanRequests",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity persistRequest(
            @ApiParam(value = "MobiscanRequest. Example: TBD")
            @RequestBody(required = true) MobiscanRequest mobiscanRequest) {

        LocalDateTime lastDayOfMonth = mobiscanRequest.getDepartureDate().with(lastDayOfMonth());

        Long index = mobiscanRequestRepository.findMaxId();
        if (index == null) {
            index = 1L;
        }
        else {
            index += 1;
        }

        while (!mobiscanRequest.getDepartureDate().isAfter(lastDayOfMonth)) {
            if (mobiscanRequest.getDepartureDate().getDayOfWeek() != DayOfWeek.SATURDAY && mobiscanRequest.getDepartureDate().getDayOfWeek() != DayOfWeek.SUNDAY) {
                mobiscanRequest.setId(index++);
                mobiscanRequestRepository.save(mobiscanRequest);
            }
            mobiscanRequest.setDepartureDate(mobiscanRequest.getDepartureDate().plusDays(1));
        }

        return null;
    }

}
