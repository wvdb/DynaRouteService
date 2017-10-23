package be.ictdynamic.dynarouteservice.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 23/10/2017.
 */
public class CarPoolParkingFile implements Serializable {
    private List<CarPoolParkings> carpoolparkings;

    public List<CarPoolParkings> getCarpoolparkings() {
        if (carpoolparkings == null) {
            carpoolparkings = new ArrayList<>();
        }
        return carpoolparkings;
    }

    public void setCarpoolparkings(List<CarPoolParkings> carpoolparkings) {
        this.carpoolparkings = carpoolparkings;
    }

    private static class CarPoolParkings {
        private Parking parking;

        public Parking getParking() {
            return parking;
        }

        public void setParking(Parking parking) {
            this.parking = parking;
        }

        private static class Parking {
            private String title;
            private String field_carpool_parking_types;
            private String field_gemeente;
            private String field_locatie;
            private String field_carpoolkaart;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getField_carpool_parking_types() {
                return field_carpool_parking_types;
            }

            public void setField_carpool_parking_types(String field_carpool_parking_types) {
                this.field_carpool_parking_types = field_carpool_parking_types;
            }

            public String getField_gemeente() {
                return field_gemeente;
            }

            public void setField_gemeente(String field_gemeente) {
                this.field_gemeente = field_gemeente;
            }

            public String getField_locatie() {
                return field_locatie;
            }

            public void setField_locatie(String field_locatie) {
                this.field_locatie = field_locatie;
            }

            public String getField_carpoolkaart() {
                return field_carpoolkaart;
            }

            public void setField_carpoolkaart(String field_carpoolkaart) {
                this.field_carpoolkaart = field_carpoolkaart;
            }
        }
    }
}
