package mlchars.metrics;

import mlchars.Image;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public class PixelMetric implements Metric {
    public long getDistanceBetween(Image img1, Image img2) {
        boolean[] a = img1.getPixels();
        boolean[] b = img2.getPixels();
        if (a.length < b.length) {
            boolean[] t = a;
            a = b;
            b = t;
        }
        int len = Math.max(a.length, b.length);

        System.err.println(a.length);
        System.err.println(b.length);

        long distance = 0;
        for (int i = 0; i < len; ++i) {
            if (b.length > i) {
                if (a[i] != b[i])
                    ++distance;
            } else if (a[i]) {
                ++distance;
            }
        }

        return distance;
    }
}
