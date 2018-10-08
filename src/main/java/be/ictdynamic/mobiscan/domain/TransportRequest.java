package be.ictdynamic.mobiscan.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

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
    @NotBlank(message = "officeAddress cannot be empty!")
    private String officeAddress;

    @Getter
    @Setter
    @NotBlank(message = "homeAddress cannot be empty!")
    private String homeAddress;

    @Getter
    @Setter
    private Date departureTime;

    @Getter
    @Setter
    private Integer numberOfDepartureTimesToBeProcessed;

    public TransportRequest(String officeAddress, String homeAddress, Date departureTime) {
        this.officeAddress = officeAddress;
        this.homeAddress = homeAddress;
        this.departureTime = departureTime;
    }

    public TransportRequest(String officeAddress, String homeAddress, Date departureTime, Integer numberOfDepartureTimesToBeProcessed) {
        this.officeAddress = officeAddress;
        this.homeAddress = homeAddress;
        this.departureTime = departureTime;
        this.numberOfDepartureTimesToBeProcessed = numberOfDepartureTimesToBeProcessed;
    }

    @Override
    public String toString() {
        return "TransportRequest{" +
                "officeAddress='" + officeAddress + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", numberOfDepartureTimesToBeProcessed='" + numberOfDepartureTimesToBeProcessed + '\'' +
                '}';
    }
}
