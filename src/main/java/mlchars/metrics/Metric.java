package mlchars.metrics;

import mlchars.Image;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public interface Metric {
    public long getDistanceBetween(Image a, Image b);
}
