package mlchars.metric;

import mlchars.Image;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public interface Metric {
    boolean compare(double a, double b);

    double getDistanceBetween(Image a, Image b);

    double getMaxValue();

    double getMinValue();
}
