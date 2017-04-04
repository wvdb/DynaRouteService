package be.ictdynamic.dynarouteservice.common.io;

/**
 * Interface FileLineClosure.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 16-dec-2007 - 13:52:36
 */
public interface FileLineClosure {

    /**
     * Process line.
     *
     * @param line the line
     */
    void processLine(String line);
}
