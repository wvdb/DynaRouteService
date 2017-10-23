package be.ictdynamic.dynarouteservice.controller;

import be.ictdynamic.dynarouteservice.domain.CarPoolParkingFile;
import be.ictdynamic.dynarouteservice.domain.TransportResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
public class LoadFileController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadFileController.class);

//    @Autowired
//    private LoadFileService loadFileService;

    @ApiOperation(value = "Method to upload a CarPoolParkingFile.")
    @RequestMapping(value = "/upload",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity upload(@RequestParam("file") MultipartFile uploadedInputStream) {
        ObjectMapper mapper = new ObjectMapper();

        CarPoolParkingFile carPoolParkingFile;

        try {
            carPoolParkingFile = mapper.readValue(uploadedInputStream.getInputStream(), CarPoolParkingFile.class);
        } catch (IOException e) {
            transportResponse.setResponseMessage((e.getMessage() == null) ? e.toString() : e.getMessage());
            return ResponseEntity.ok(transportResponse);
        }

//        loadFileService.persistContentOfFile(carPoolParkingFile);

        String processingMessage = String.format("File %s has been processed. %06d Car Pool Parkings have been inserted. ",
                uploadedInputStream.getOriginalFilename(), carPoolParkingFile.getCarpoolparkings().size());
        LOGGER.info(processingMessage);

        transportResponse.setResponseMessage(processingMessage);

        return ResponseEntity.ok(transportResponse);
    }

}
