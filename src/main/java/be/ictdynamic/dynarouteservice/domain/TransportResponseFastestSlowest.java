package be.ictdynamic.dynarouteservice.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Class TransportResponseFastestSlowest.
 *
 * @author Wim Van den Brande
 * @since 03/10/2015 - 20:35
 */
public class TransportResponseFastestSlowest implements Serializable {
    @Getter
    @Setter
    private List<TransportResponseDetailsFastestSlowest> fastestRoutes;

    @Getter
    @Setter
    private List<TransportResponseDetailsFastestSlowest> slowestRoutes;

    @Getter
    @Setter
    private List<TransportResponseDetailsFastestSlowest> routes;

    static public class TransportResponseDetailsFastestSlowest {
        @Getter
        @Setter
        private Date routeDate;

        @Getter
        @Setter
        private String routeDateAsString;

        @Getter
        @Setter
        private Integer routeDuration;

        public TransportResponseDetailsFastestSlowest(Date routeDate, String routeDateAsString, Integer routeDuration) {
            this.routeDate = routeDate;
            this.routeDateAsString = routeDateAsString;
            this.routeDuration = routeDuration;
        }
    }
}


