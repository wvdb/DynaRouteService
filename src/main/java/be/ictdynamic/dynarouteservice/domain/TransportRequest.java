package be.ictdynamic.dynarouteservice.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Class TransportRequest.
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

    @Getter
    @Setter
    private Date departureTime;

    public TransportRequest(String officeAddress, String homeAddress, Date departureTime) {
        this.officeAddress = officeAddress;
        this.homeAddress = homeAddress;
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "TransportRequest{" +
                "officeAddress='" + officeAddress + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", departureTime='" + departureTime + '\'' +
                '}';
    }
}
