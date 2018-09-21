package be.ictdynamic.mobiscan.services;

import be.ictdynamic.mobiscan.domain.TransportRequest;
import be.ictdynamic.mobiscan.domain.TransportResponse;

/**
 * Class GoogleService.
 *
 * @author Wim Van den Brande
 * @since 05/10/2015 - 20:35
 */
public interface GoogleService {
    TransportResponse processRouteRequest(TransportRequest transportRequest) throws Exception;
}
