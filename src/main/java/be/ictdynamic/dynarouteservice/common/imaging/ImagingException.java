package be.ictdynamic.dynarouteservice.common.imaging;

/**
 * Class ImagingException.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 28-jun-2007 - 15:11:06
 */
public class ImagingException extends RuntimeException {
    private static final long serialVersionUID = 7768339664459338816L;

    /**
     * Instantiates a new imaging exception.
     */
    public ImagingException() {
        super();
    }

    /**
     * Instantiates a new imaging exception.
     *
     * @param message the message
     */
    public ImagingException(String message) {
        super(message);
    }

    /**
     * Instantiates a new imaging exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ImagingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new imaging exception.
     *
     * @param cause the cause
     */
    public ImagingException(Throwable cause) {
        super(cause);
    }
}
