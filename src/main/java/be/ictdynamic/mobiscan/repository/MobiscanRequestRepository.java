package be.ictdynamic.mobiscan.repository;

import be.ictdynamic.mobiscan.domain.MobiscanRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "mobiscanRequests", path = "mobiscanRequests")
public interface MobiscanRequestRepository extends CrudRepository<MobiscanRequest,Long> {
    	List<MobiscanRequest> findByProcessingDateIsNull();
}
