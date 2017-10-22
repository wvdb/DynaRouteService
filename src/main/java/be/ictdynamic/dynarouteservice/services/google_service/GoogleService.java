package be.ictdynamic.dynarouteservice.services.google_service;

import be.ictdynamic.dynarouteservice.domain.TransportRequest;
import be.ictdynamic.dynarouteservice.domain.TransportResponse;

/**
 * Class GoogleService.
 *
 * @author Wim Van den Brande
 * @since 05/10/2015 - 20:35
 */
public interface GoogleService {
    TransportResponse processRouteRequest(TransportRequest transportRequest) throws Exception;
}
