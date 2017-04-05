package be.ictdynamic.dynarouteservice.domain;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class GoogleMapResponse.
 *
 * @author Wim Van den Brande
 * @since 03/10/2015 - 20:35
 */
public class GoogleMapResponse implements Serializable {
    // hashmap with key = transport and value = GoogleTransportInfo
    private HashMap<String, GoogleTransportInfo> transportInfoMap;

    public HashMap<String, GoogleTransportInfo> getTransportInfoMap() {
        if (transportInfoMap == null) {
            transportInfoMap = new HashMap<>();
        }
        return transportInfoMap;
    }

    public void setTransportInfoMap(HashMap<String, GoogleTransportInfo> transportInfoMap) {
        this.transportInfoMap = transportInfoMap;
    }
}
