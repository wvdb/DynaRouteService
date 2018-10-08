package be.ictdynamic.mobiscan.utilities;

import org.slf4j.Logger;

/**
 * Created by Wim Van den Brande on 21/12/2017.
 */
public class MobiscanUtilities {
    public static <T> T timedReturn(final Logger LOGGER, String method, long start, T object) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Execution of method %s took %05d ms", method, System.currentTimeMillis() - start));
        }

        return object;
    }
}
