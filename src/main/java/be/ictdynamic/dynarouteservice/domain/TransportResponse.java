package be.ictdynamic.dynarouteservice.domain;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class TransportResponse.
 *
 * @author Wim Van den Brande
 * @since 03/10/2015 - 20:35
 */
public class TransportResponse implements Serializable {
    // hashMap with key = transport and value = GoogleTransportInfo
    private HashMap<String, TransportInfo> transportInfoMap;

    public HashMap<String, TransportInfo> getTransportInfoMap() {
        if (transportInfoMap == null) {
            transportInfoMap = new HashMap<>();
        }
        return transportInfoMap;
    }

    public void setTransportInfoMap(HashMap<String, TransportInfo> transportInfoMap) {
        this.transportInfoMap = transportInfoMap;
    }
}
