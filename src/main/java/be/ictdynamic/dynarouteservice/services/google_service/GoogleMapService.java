package be.ictdynamic.dynarouteservice.services.google_service;

import be.ictdynamic.dynarouteservice.domain.GoogleMapRequest;
import be.ictdynamic.dynarouteservice.domain.GoogleMapResponse;

/**
 * Class GoogleMapService.
 *
 * @author Wim Van den Brande
 * @since 05/10/2015 - 20:35
 */
public interface GoogleMapService {
    GoogleMapResponse getGoogleDistance(GoogleMapRequest googleMapRequest) throws Exception;
}
