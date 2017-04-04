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
public class GoogleMapRequest implements Serializable {

    private static final long serialVersionUID = -5073135041934007777L;

    @Getter
    @Setter
    public String officeAddress;

    @Getter
    @Setter
    public String homeAddress;

    public GoogleMapRequest(String officeAddress, String homeAddress) {
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
