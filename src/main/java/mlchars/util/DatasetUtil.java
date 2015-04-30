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

    public static Image average(ImageDataset data) {
        int maxW = data.getMaxWidth();
        int maxH = data.getMaxHeight();
        int maxLength = maxH * maxW;
        double[] result = new double[maxLength];

        for (int y = 0; y < maxH; ++y) {
            for (int x = 0; x < maxW; ++x) {
                double sum = 0;
                for (int j = 0; j < data.size(); ++j) {
                    Image img = data.getImage(j);
                    sum += img.getPixel(x, y);
                }
                result[y * maxW + x] = sum / data.size();
            }
        }
        return new DefaultImage(maxW, maxH, result);
    }

    public static Image standardDeviation(ImageDataset data, Image avg) {
        Image sum = new DefaultImage(avg.getWidth(), avg.getHeight(), new double[avg.attributesCount()]);
        for (int i = 0; i < data.size(); ++i) {
            Image img = data.getImage(i);
            Image diff = img.subtract(avg);
            sum = sum.add(diff.multiply(diff));
        }
        sum = sum.divide(data.size());
        return sum.sqrt();
    }
}
