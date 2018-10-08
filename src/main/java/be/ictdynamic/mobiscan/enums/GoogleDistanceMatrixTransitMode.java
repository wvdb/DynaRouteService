package be.ictdynamic.mobiscan.enums;

/**
 * Created by Wim Van den Brande on 27/09/2018.
 */
public enum GoogleDistanceMatrixTransitMode {
    DRIVING("driving"),
    WALKING("walking"),
    BICYCLING("bicycling"),
    TRANSIT("transit");

    private final String transitMode;

    GoogleDistanceMatrixTransitMode(String transitMode) {
        this.transitMode = transitMode;
    }

    public String getTransitMode() {
        return transitMode;
    }
}
