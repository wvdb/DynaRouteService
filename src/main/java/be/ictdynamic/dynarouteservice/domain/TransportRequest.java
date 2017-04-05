package be.ictdynamic.dynarouteservice.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Class GoogleMapRequest.
 *
 * @author Wim Van den Brande
 * @since 03/10/2015 - 20:35
 */
public class TransportRequest implements Serializable {

    private static final long serialVersionUID = -5073135041934007777L;

    @Getter
    @Setter
    private String officeAddress;

    @Getter
    @Setter
    private String homeAddress;

    public TransportRequest(String officeAddress, String homeAddress) {
        this.officeAddress = officeAddress;
        this.homeAddress = homeAddress;
    }

    @Override
    public String toString() {
        return "GoogleMapRequest{" +
                "officeAddress='" + officeAddress + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                '}';
    }
}
