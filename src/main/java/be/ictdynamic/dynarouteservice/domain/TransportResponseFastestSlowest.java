package be.ictdynamic.dynarouteservice.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    private Map<String, TransportResponseDetailsFastestSlowest> fastestRoutesPerDay;

    public Map<String, TransportResponseDetailsFastestSlowest> getFastestRoutesPerDay() {
        if (fastestRoutesPerDay == null) {
            fastestRoutesPerDay = new LinkedHashMap<>();
        }
        return fastestRoutesPerDay;
    }

    public void setFastestRoutesPerDay(Map<String, TransportResponseDetailsFastestSlowest> fastestRoutesPerDay) {
        this.fastestRoutesPerDay = fastestRoutesPerDay;
    }

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

        @Getter
        @Setter
        private String routeDurationAsText;

        public TransportResponseDetailsFastestSlowest(Date routeDate, String routeDateAsString, Integer routeDuration, String routeDurationAsText) {
            this.routeDate = routeDate;
            this.routeDateAsString = routeDateAsString;
            this.routeDuration = routeDuration;
            this.routeDurationAsText = routeDurationAsText;
        }
    }
}


