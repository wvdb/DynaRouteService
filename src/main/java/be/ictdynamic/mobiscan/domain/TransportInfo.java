package be.ictdynamic.mobiscan.domain;

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

    // duration as text
    @Getter
    @Setter
    private String durationAsText;

    // longitude
    @Getter
    @Setter
    private double lng;

    // latitude
    @Getter
    @Setter
    private double lat;

    @Override
    public String toString() {
        return "TransportInfo{" +
                "distance=" + distance +
                ", duration=" + duration +
                ", lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
