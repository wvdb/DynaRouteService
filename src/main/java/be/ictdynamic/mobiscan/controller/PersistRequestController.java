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

@RestController
public class PersistRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistRequestController.class);

    @Autowired
    MobiscanRequestRepository mobiscanRequestRepository;

    @ApiOperation(value = "Method to persist request.",
            notes = "TBD.")
    @RequestMapping(value = "/persistRequest",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity persistRequest(
            @ApiParam(value = "Partial or complete Home address. Example: Tweebunder 4, Edegem, België")
            @RequestBody(required = true) MobiscanRequest mobiscanRequest) {

            mobiscanRequestRepository.save(mobiscanRequest);

            return null;
    }

}
