package be.ictdynamic.mobiscan.services;

import be.ictdynamic.mobiscan.domain.MobiscanRequest;
import be.ictdynamic.mobiscan.repository.MobiscanRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ProcessRequestService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MobiscanRequestRepository mobiscanRequestRepository;

    @Autowired
    private GoogleService googleService;

    /*
      This method is responsible for the actual processing of requests that haven't been processed yet
     */
    @Scheduled(fixedDelayString = "${mobiscan.job.processMobiscanRequest.fixedRate}")
    public void processMobiscanRequests() {
        List<MobiscanRequest> mobiscanRequestsToBeProcessed = mobiscanRequestRepository.findByProcessingDateIsNull();
        logger.info(String.format("Number of requests to be processed = %06d.", mobiscanRequestsToBeProcessed == null ? 0 : mobiscanRequestsToBeProcessed.size()));

        if (mobiscanRequestsToBeProcessed != null) {
            mobiscanRequestsToBeProcessed.forEach(mobiscanRequest -> {

                googleService.getGoogleDistanceMatrixResponse(mobiscanRequest);

                mobiscanRequest.setProcessingDate(LocalDateTime.now());
                mobiscanRequestRepository.save(mobiscanRequest);
            });
        }

    }}
