package be.ictdynamic.mobiscan.utilities;

public class MobiscanException extends Exception {
    public MobiscanException(Exception e) {
        super(e);
    }

    public MobiscanException(String message) {
        super(message);
    }

}
