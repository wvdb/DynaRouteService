package be.ictdynamic.dynarouteservice.common.imaging;

/**
 * Interface ImageService.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 28-jun-2007 - 15:04:13
 */
public interface ImageService {

    /**
     * Resize fit.
     *
     * @param image     the image
     * @param maxwidth  the maxwidth
     * @param maxheight the maxheight
     * @param enlarge   the enlarge
     * @return the byte[]
     */
    byte[] resizeFit(byte[] image, int maxwidth, int maxheight, boolean enlarge);
}
