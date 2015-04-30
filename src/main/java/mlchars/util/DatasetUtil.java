package mlchars.util;

import mlchars.DefaultImage;
import mlchars.Image;
import mlchars.ImageDataset;

import java.util.Random;

/**
 * Created by Przemysław Kuczyński on 4/30/15.
 */
final public class DatasetUtil {

    public static Image minAttributes(ImageDataset data) {
        int w = data.getMaxWidth();
        int h = data.getMaxHeight();
        double[] pixels = new double[w * h];

        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x)
                pixels[y * w + x] = data.getMinPixel(x, y);
        }
        return new DefaultImage(w, h, pixels);
    }

    public static Image maxAttributes(ImageDataset data) {
        int w = data.getMaxWidth();
        int h = data.getMaxHeight();
        double[] pixels = new double[w * h];

        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x)
                pixels[y * w + x] = data.getMaxPixel(x, y);
        }
        return new DefaultImage(w, h, pixels);
    }

    public static Image getRandomImage(ImageDataset data, Random random) {
        int w = data.getMaxWidth();
        int h = data.getMaxHeight();
        double[] pixels = new double[w * h];

        for (int i = 0; i < pixels.length; ++i)
            pixels[i] = random.nextBoolean() ? 1.f : 0.f;

        return new DefaultImage(w, h, pixels);
    }
}
