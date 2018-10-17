package be.ictdynamic.mobiscan.services;

import be.ictdynamic.mobiscan.domain.CarPoolParking;
import be.ictdynamic.mobiscan.domain.CarPoolParkingES;
import be.ictdynamic.mobiscan.domain.CarPoolParkingFile;
import be.ictdynamic.mobiscan.repository.CarPoolParkingRepository;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.json.simple.JSONObject;

import javax.transaction.Transactional;
import java.io.IOException;
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

    @Autowired
    private RestHighLevelClient restClient;

    @Value("${mobiscan.saveToMySql}")
    private boolean saveToMySql;

    @Value("${mobiscan.saveToES}")
    private boolean saveToES;

    @Value("${mobiscan.es.carpool_parking_index_name}")
    public String ES_CAR_POOL_PARKING_INDEX_NAME;

    @Value("${mobiscan.es.carpool_parking_index_type}")
    public String ES_CAR_POOL_PARKING_INDEX_TYPE;

    public int persistCarPoolParkings(CarPoolParkingFile carPoolParkingFile) throws IllegalArgumentException, IOException{
        if (saveToMySql) {
            carPoolParkingRepository.deleteAll();
        }

        for (CarPoolParkingFile.CarPoolParking carPoolParking : carPoolParkingFile.getCarpoolparkings()) {
            CarPoolParking carPoolParkingToBePersisted = new CarPoolParking();
            carPoolParkingToBePersisted.setCreatedOn(new Date());

            carPoolParkingToBePersisted.setCommune(carPoolParking.getParking().getCommune());
            carPoolParkingToBePersisted.setType(carPoolParking.getParking().getParkingType());
            carPoolParkingToBePersisted.setTitle(carPoolParking.getParking().getTitle());
            carPoolParkingToBePersisted.setLatitude(carPoolParking.getParking().getLatitudeOfParking());
            carPoolParkingToBePersisted.setLongitude(carPoolParking.getParking().getLongitudeOfParking());

            if (saveToMySql) {
                persistInMySql(carPoolParkingToBePersisted);
            }

            if (saveToES) {
                persistInES(carPoolParkingToBePersisted);
            }
        };

        return carPoolParkingFile.getCarpoolparkings().size();
    }

    private void persistInMySql(CarPoolParking carPoolParkingToBePersisted) {
        carPoolParkingRepository.save(carPoolParkingToBePersisted);
    }

    private void persistInES(CarPoolParking carPoolParkingToBePersisted) throws IllegalArgumentException, IOException {
        CarPoolParkingES carPoolParkingES = new CarPoolParkingES();

        carPoolParkingES.setCommune(carPoolParkingToBePersisted.getCommune());
        carPoolParkingES.setType(carPoolParkingToBePersisted.getType());
        carPoolParkingES.setTitle(carPoolParkingToBePersisted.getTitle());
        carPoolParkingES.setLatitude(Double.toString(carPoolParkingToBePersisted.getLatitude()));
        carPoolParkingES.setLongitude(Double.toString(carPoolParkingToBePersisted.getLongitude()));

        JSONObject myLocation = new JSONObject();
        myLocation.put("lon", carPoolParkingES.getLongitude());
        myLocation.put("lat", carPoolParkingES.getLatitude());

        IndexRequest indexRequest = new IndexRequest(ES_CAR_POOL_PARKING_INDEX_NAME, ES_CAR_POOL_PARKING_INDEX_TYPE)
                .source(
                        "createdOn", new Date(),
                        "commune", carPoolParkingES.getCommune(),
                        "title", carPoolParkingES.getTitle(),
                        "type", carPoolParkingES.getType(),
                        "location", myLocation
                );

        IndexResponse indexResponse = restClient.index(indexRequest);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Persisted esCarPoolParking: {}", carPoolParkingES);
        }
    }

}
