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
    private HashMap<String, GoogleTransportInfo> googleTransportInfoMap;

    public HashMap<String, GoogleTransportInfo> getGoogleTransportInfoMap() {
        if (googleTransportInfoMap == null) {
            googleTransportInfoMap = new HashMap<>();
        }
        return googleTransportInfoMap;
    }

    public void setGoogleTransportInfoMap(HashMap<String, GoogleTransportInfo> googleTransportInfoMap) {
        this.googleTransportInfoMap = googleTransportInfoMap;
    }
}
