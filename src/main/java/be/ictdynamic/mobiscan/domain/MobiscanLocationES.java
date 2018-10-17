package be.ictdynamic.mobiscan.domain;

import lombok.Getter;
import lombok.Setter;

public class MobiscanLocationES {
    @Getter
    @Setter
	private String address;

    @Getter
    @Setter
    private double latitude;

    @Getter
    @Setter
    private double longitude;

    @Override
    public String toString() {
        return "MobiscanLocationES{" +
                "address='" + address + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
