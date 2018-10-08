package be.ictdynamic.mobiscan.domain;

import lombok.Getter;
import lombok.Setter;

public class CarPoolParkingES {

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
	private String type;

    @Getter
    @Setter
	private String commune;

    @Getter
    @Setter
    private String latitude;

    @Getter
    @Setter
    private String longitude;

}
