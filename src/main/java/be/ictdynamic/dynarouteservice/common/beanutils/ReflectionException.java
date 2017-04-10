package be.ictdynamic.dynarouteservice.common.beanutils;

/**
 * Class ReflectionException.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 6-okt-2008
 */
public class ReflectionException extends RuntimeException {

    private static final long serialVersionUID = -1143496211198853437L;

    /**
     * Instantiates a new reflection exception.
     */
    public ReflectionException() {
        super();
    }

    /**
     * Instantiates a new reflection exception.
     *
     * @param cause the cause
     */
    public ReflectionException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new reflection exception.
     *
     * @param message the message
     */
    public ReflectionException(String message) {
        super(message);
    }

    /**
     * Instantiates a new reflection exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
