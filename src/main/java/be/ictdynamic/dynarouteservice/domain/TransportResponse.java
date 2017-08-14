package be.ictdynamic.dynarouteservice.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class TransportResponse.
 *
 * @author Wim Van den Brande
 * @since 03/10/2015 - 20:35
 */
public class TransportResponse implements Serializable {
    // hashMap with key = transport and value = GoogleTransportInfo
    private HashMap<String, TransportInfo> transportInfoMap;

    // hashMap with keys = address1.lat, address1.lng, address2.lat, address2.lng
    private Map<String, Double> mapWithLatitudeAndLongitude;

    public HashMap<String, TransportInfo> getTransportInfoMap() {
        if (transportInfoMap == null) {
            transportInfoMap = new HashMap<>();
        }
        return transportInfoMap;
    }

    public void setTransportInfoMap(HashMap<String, TransportInfo> transportInfoMap) {
        this.transportInfoMap = transportInfoMap;
    }

    public Map<String, Double> getMapWithLatitudeAndLongitude() {
        if (mapWithLatitudeAndLongitude == null) {
            mapWithLatitudeAndLongitude = new LinkedHashMap<>();
        }
        return mapWithLatitudeAndLongitude;
    }

    public void setMapWithLatitudeAndLongitude(Map<String, Double> mapWithLatitudeAndLongitude) {
        this.mapWithLatitudeAndLongitude = mapWithLatitudeAndLongitude;
    }
}
