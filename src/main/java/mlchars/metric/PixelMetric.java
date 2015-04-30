package mlchars.metric;

import mlchars.Image;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public class PixelMetric implements Metric {
    public boolean compare(double a, double b) {
        return a < b;
    }

    public double getDistanceBetween(Image img1, Image img2) {
        int maxW = Math.max(img1.getWidth(), img2.getWidth());
        int maxH = Math.max(img1.getHeight(), img2.getHeight());

        double distance = 0;

        for (int y = 0; y < maxH; ++y) {
            for (int x = 0; x < maxW; ++x) {
                double d = img1.getPixel(x, y) - img2.getPixel(x, y);
                distance += d * d;
            }
        }

        return Math.sqrt(distance);
    }

    public double getMaxValue() {
        return Double.MAX_VALUE;
    }

    public double getMinValue() {
        return .0f;
    }
}
