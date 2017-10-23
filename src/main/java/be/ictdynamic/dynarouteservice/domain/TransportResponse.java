package be.ictdynamic.dynarouteservice.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class TransportResponse.
 *
 * @author Wim Van den Brande
 * @since 03/10/2015 - 20:35
 */
@Component
public class TransportResponse implements Serializable {
    // hashMap with key = transport and value = GoogleTransportInfo
    private HashMap<String, TransportInfo> transportInfoMap;

    private double lat;
    private double lng;

    private Map<String, Double> mapWeather;

    private String responseMessage;

    public HashMap<String, TransportInfo> getTransportInfoMap() {
        if (transportInfoMap == null) {
            transportInfoMap = new HashMap<>();
        }
        return transportInfoMap;
    }

    public void setTransportInfoMap(HashMap<String, TransportInfo> transportInfoMap) {
        this.transportInfoMap = transportInfoMap;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Map<String, Double> getMapWeather() {
        return mapWeather;
    }

    public void setMapWeather(Map<String, Double> mapWeather) {
        this.mapWeather = mapWeather;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
