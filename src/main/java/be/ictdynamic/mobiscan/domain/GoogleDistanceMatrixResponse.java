package be.ictdynamic.mobiscan.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class GoogleDistanceMatrixResponse.
 *
 * @author Wim Van den Brande
 * @since 03/10/2015 - 20:35
 */
@Component
public class GoogleDistanceMatrixResponse implements Serializable {
    private Map<String, GoogleDistanceMatrixResponseDetail> googleDistanceMatrixDetails;

    public Map<String, GoogleDistanceMatrixResponseDetail> getGoogleDistanceMatrixDetails() {
        if (googleDistanceMatrixDetails == null) {
            googleDistanceMatrixDetails = new LinkedHashMap<>();
        }
        return googleDistanceMatrixDetails;
    }

    public void setGoogleDistanceMatrixDetails(Map<String, GoogleDistanceMatrixResponseDetail> googleDistanceMatrixDetails) {
        this.googleDistanceMatrixDetails = googleDistanceMatrixDetails;
    }

    @Override
    public String toString() {
        return "GoogleDistanceMatrixResponse{" +
                "googleDistanceMatrixDetails=" + googleDistanceMatrixDetails +
                '}';
    }

    public static class GoogleDistanceMatrixResponseDetail implements Serializable {
        // distance in meters
        @Getter
        @Setter
        private Long distance;

        // duration in seconds
        @Getter
        @Setter
        private Long duration;

        @Override
        public String toString() {
            return "GoogleDistanceMatrixResponseDetail{" +
                    "distance=" + distance +
                    ", duration=" + duration +
                    '}';
        }
    }

}
