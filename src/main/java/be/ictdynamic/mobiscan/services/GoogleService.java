package be.ictdynamic.mobiscan.services;

import be.ictdynamic.mobiscan.domain.GoogleDistanceMatrixResponse;
import be.ictdynamic.mobiscan.domain.MobiscanRequest;
import be.ictdynamic.mobiscan.utilities.MobiscanException;

import java.util.Map;

/**
 * Class GoogleService.
 *
 * @author Wim Van den Brande
 * @since 05/10/2015 - 20:35
 */
public interface GoogleService {
    public GoogleDistanceMatrixResponse getGoogleDistanceMatrixResponse(final MobiscanRequest mobiscanRequest);
    public Map<String, Double> getLatitudeLongitudeFromGoogle(String address) throws MobiscanException;
//    public TransportResponse processRouteRequest(MobiscanRequest mobiscanRequest) throws Exception;
}
