package be.ictdynamic.dynarouteservice.common.lang;

import org.apache.commons.collections.Closure;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;

import java.text.MessageFormat;

/**
 * Class TimedFunction.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since Dec 14, 2009
 */
public abstract class TimedFunction implements Closure {
    private String name;
    private StopWatch sw = new StopWatch();

    public TimedFunction(String name) {
        this.setName(name);
    }

    public void run(Logger log) {
        log.info((log.isInfoEnabled() ? "Starting: " + this.getName() : null));
        sw.start();
        this.execute(null);
        sw.stop();
        log.info((log.isInfoEnabled() ? MessageFormat.format("Completed: {0} -- duration: {1}", this.getName(), StopWatchUtilities.getTime(getSw())) : null));
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public StopWatch getSw() {
        return sw;
    }
}
