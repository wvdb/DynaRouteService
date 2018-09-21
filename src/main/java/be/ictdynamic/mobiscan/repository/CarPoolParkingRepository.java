package be.ictdynamic.mobiscan.repository;

import be.ictdynamic.mobiscan.domain.CarPoolParking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "carPoolParkings", path = "carPoolParkings")
public interface CarPoolParkingRepository extends CrudRepository<CarPoolParking,Long> {
}
