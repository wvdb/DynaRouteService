package be.ictdynamic.dynarouteservice.common.lang;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.StopWatch;

/**
 * Class StopWatchUtilities.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since Jul 1, 2009
 */
public final class StopWatchUtilities {
    private StopWatchUtilities() {
    }

    public static String getTime(StopWatch stopWatch) {
        return DateFormatUtils.format(stopWatch.getTime() - DateUtils.MILLIS_PER_HOUR, "HH:mm:ss,SSS");
    }
}
