package be.ictdynamic.dynarouteservice.common.io;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

/**
 * Static class providing utility methods for IO functionality.
 * <p/>
 * This class mainly extends the Commons IO functionality.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 16-dec-2007
 */
public final class IOUtilities {
    /**
     * Private constructor to prevent this static class from being instantiated.
     */
    private IOUtilities() {
    }

    /**
     * Read lines.
     *
     * @param in      the in
     * @param closure the closure
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void readLines(InputStream in, FileLineClosure closure) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = reader.readLine();

        while (line != null) {
            closure.processLine(line);
            line = reader.readLine();
        }
    }

    /**
     * Gets the contents of the given resource as lines
     *
     * @param resourceLocation The base class to search for the resource
     * @param resource         The resource to read
     * @return A Collection of Strings
     */
    @SuppressWarnings({"unchecked"})
    public static Collection<String> getResourceLines(Class resourceLocation, String resource) {
        InputStream input = null;
        Collection<String> lines;
        try {
            input = resourceLocation.getResourceAsStream(resource);
            lines = IOUtils.readLines(input);
        } catch (IOException e) {
            throw new RuntimeException(e); //NOSONAR
        } finally {
            IOUtils.closeQuietly(input);
        }
        return lines;
    }
}