package be.ictdynamic.mobiscan.controller;

import be.ictdynamic.mobiscan.domain.CarPoolParkingFile;
import be.ictdynamic.mobiscan.services.LoadCarPoolParkingFileService;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class LoadFileController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadFileController.class);

    @Autowired
    private LoadCarPoolParkingFileService loadCarPoolParkingFileService;

    @ApiOperation(value = "Method to upload a CarPoolParkingFile.")
    @RequestMapping(value = "/uploadCarPoolParkingFile",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity uploadCarPoolParkingFile(@RequestParam("file") MultipartFile uploadedInputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        CarPoolParkingFile carPoolParkingFile;

        try {
            carPoolParkingFile = mapper.readValue(uploadedInputStream.getInputStream(), CarPoolParkingFile.class);

            for (CarPoolParkingFile.CarPoolParking carPoolParking : carPoolParkingFile.getCarpoolparkings()) {
                CarPoolParkingFile.CarPoolParking.Parking parking = carPoolParking.getParking();

                Pattern pattern = Pattern.compile("\\d+\\.\\d+");
                Matcher matcher = pattern.matcher(parking.getLocationDetails());

                short index = 0;
                while (matcher.find()) {
                    if (index == 0) {
                        parking.setLongitudeOfParking(Float.parseFloat(matcher.group()));
                    }
                    if (index == 1) {
                        parking.setLatitudeOfParking(Float.parseFloat(matcher.group()));
                    }
                    index++;
                }

                LOGGER.debug(String.format("locationDetails %s results in latitude %13.10f and in longitude %13.10f.", parking.getLocationDetails(), parking.getLatitudeOfParking(), parking.getLongitudeOfParking()));
            }
        } catch (IOException e) {
            transportResponse.setResponseMessage((e.getMessage() == null) ? e.toString() : e.getMessage());
            return ResponseEntity.ok(transportResponse);
        }

        loadCarPoolParkingFileService.persistCarPoolParkings(carPoolParkingFile);

        String processingMessage = String.format("File %s has been processed. %06d Car Pool Parkings have been inserted. ",
                uploadedInputStream.getOriginalFilename(), carPoolParkingFile.getCarpoolparkings().size());
        LOGGER.info(processingMessage);

        transportResponse.setResponseMessage(processingMessage);

        return ResponseEntity.ok(transportResponse);
    }

}
