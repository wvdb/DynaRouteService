package be.ictdynamic.dynarouteservice.controller;

import be.ictdynamic.dynarouteservice.domain.CarPoolParkingFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

            for (CarPoolParkingFile.CarPoolParking carPoolParking : carPoolParkingFile.getCarpoolparkings()) {
                CarPoolParkingFile.CarPoolParking.Parking parking = carPoolParking.getParking();

                Double latitudeOfParking;
                Double longitudeOfParking;

//                Pattern pattern = Pattern.compile("POINT \\((\\f+) (\\f+)");
//                Pattern pattern = Pattern.compile("POINT \\([0-9]*\\.?[0-9]+");
//                Pattern pattern = Pattern.compile("\\d*\\.\\d+");
//                Matcher matcher = pattern.matcher(parking.getField_carpoolkaart().substring(7, parking.getField_carpoolkaart().length() - 1));

                //                Double latitudeOfParking = Double.parseDouble(matcher.group(1));
//                Double longitudeOfParking = Double.parseDouble(matcher.group(2));

                // example = "POINT (4.2171191592222 50.726070514757)"

                if (parking.getField_carpoolkaart() != null && parking.getField_carpoolkaart().length() >= 7) {
                    String latLonAsString = parking.getField_carpoolkaart().substring(7, parking.getField_carpoolkaart().length() - 1);
                    String[] latlon = latLonAsString.split(" ");
                    longitudeOfParking = Double.parseDouble(latlon[0]);
                    latitudeOfParking = Double.parseDouble(latlon[1]);
                }
                else {
                    longitudeOfParking = 0D;
                    latitudeOfParking = 0D;
                }

                LOGGER.debug(String.format("field_carpoolkaart %s results in lat %13.10f and in lon %13.10f.", parking.getField_carpoolkaart(), latitudeOfParking, longitudeOfParking ));
            }
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
