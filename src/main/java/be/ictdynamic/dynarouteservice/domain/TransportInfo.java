package be.ictdynamic.dynarouteservice.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Class GoogleMapResponse.
 *
 * @author Wim Van den Brande
 * @since 03/10/2015 - 20:35
 */
public class TransportInfo implements Serializable {
    // distance in meters
    @Getter
    @Setter
    private Integer distance;

    // duration in seconds
    @Getter
    @Setter
    private Integer duration;

    @Override
    public String toString() {
        return "TransportInfo{" +
                "distance=" + distance +
                ", duration=" + duration +
                '}';
    }
}
