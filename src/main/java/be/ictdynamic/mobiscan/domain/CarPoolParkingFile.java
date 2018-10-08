package be.ictdynamic.mobiscan.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 23/10/2017.
 */
public class CarPoolParkingFile implements Serializable {
    private List<CarPoolParking> carpoolparkings;

    public List<CarPoolParking> getCarpoolparkings() {
        if (carpoolparkings == null) {
            carpoolparkings = new ArrayList<>();
        }
        return carpoolparkings;
    }

    public void setCarpoolparkings(List<CarPoolParking> carpoolparkings) {
        this.carpoolparkings = carpoolparkings;
    }

    public static class CarPoolParking {
        private Parking parking;

        public Parking getParking() {
            return parking;
        }

        public void setParking(Parking parking) {
            this.parking = parking;
        }

        public static class Parking {
            private String title;

            @JsonProperty("field_carpool_parking_types")
            private String parkingType;

            @JsonProperty("field_gemeente")
            private String commune;

            @JsonProperty("field_locatie")
            private String locationDescription;

            @JsonProperty("field_carpoolkaart")
            private String locationDetails;

            double latitudeOfParking;
            double longitudeOfParking;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getParkingType() {
                return parkingType;
            }

            public void setParkingType(String parkingType) {
                this.parkingType = parkingType;
            }

            public String getCommune() {
                return commune;
            }

            public void setCommune(String commune) {
                this.commune = commune;
            }

            public String getLocationDescription() {
                return locationDescription;
            }

            public void setLocationDescription(String locationDescription) {
                this.locationDescription = locationDescription;
            }

            public String getLocationDetails() {
                return locationDetails;
            }

            public void setLocationDetails(String locationDetails) {
                this.locationDetails = locationDetails;
            }

            public double getLatitudeOfParking() {
                return latitudeOfParking;
            }

            public void setLatitudeOfParking(double latitudeOfParking) {
                this.latitudeOfParking = latitudeOfParking;
            }

            public double getLongitudeOfParking() {
                return longitudeOfParking;
            }

            public void setLongitudeOfParking(double longitudeOfParking) {
                this.longitudeOfParking = longitudeOfParking;
            }

            @Override
            public String toString() {
                return "Parking{" +
                        "title='" + title + '\'' +
                        ", parkingType='" + parkingType + '\'' +
                        ", commune='" + commune + '\'' +
                        ", locationDescription='" + locationDescription + '\'' +
                        ", locationDetails='" + locationDetails + '\'' +
                        '}';
            }
        }
    }
}
