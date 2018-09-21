package be.ictdynamic.mobiscan.services;

import be.ictdynamic.mobiscan.domain.CarPoolParking;
import be.ictdynamic.mobiscan.domain.CarPoolParkingFile;
import be.ictdynamic.mobiscan.repository.CarPoolParkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by wvdbrand on 31/08/2017.
 */
@Transactional
@Service
public class LoadCarPoolParkingFileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadCarPoolParkingFileService.class);

    @Autowired
    private CarPoolParkingRepository carPoolParkingRepository;

    public int persistCarPoolParkings(CarPoolParkingFile carPoolParkingFile) {
        carPoolParkingRepository.deleteAll();

        carPoolParkingFile.getCarpoolparkings().forEach(carPoolParking -> {
            CarPoolParking carPoolParkingToBePersisted = new CarPoolParking();
            carPoolParkingToBePersisted.setCreatedOn(new Date());

            carPoolParkingToBePersisted.setCommune(carPoolParking.getParking().getCommune());
            carPoolParkingToBePersisted.setType(carPoolParking.getParking().getParkingType());
            carPoolParkingToBePersisted.setTitle(carPoolParking.getParking().getTitle());
            carPoolParkingToBePersisted.setLatitude(carPoolParking.getParking().getLatitudeOfParking());
            carPoolParkingToBePersisted.setLongitude(carPoolParking.getParking().getLongitudeOfParking());

            carPoolParkingRepository.save(carPoolParkingToBePersisted);
        });

        return carPoolParkingFile.getCarpoolparkings().size();
    }

}
