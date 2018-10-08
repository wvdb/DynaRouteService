package be.ictdynamic.mobiscan.services;

import be.ictdynamic.mobiscan.domain.GoogleDistanceMatrixResponse;
import be.ictdynamic.mobiscan.domain.MobiscanRequest;

/**
 * Class GoogleService.
 *
 * @author Wim Van den Brande
 * @since 05/10/2015 - 20:35
 */
public interface GoogleService {
    public GoogleDistanceMatrixResponse getGoogleDistanceMatrixResponse(final MobiscanRequest mobiscanRequest);
//    public TransportResponse processRouteRequest(MobiscanRequest mobiscanRequest) throws Exception;
}
