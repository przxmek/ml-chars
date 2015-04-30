package mlchars;

/**
 * Created by Przemysław Kuczyński on 4/30/15.
 */
public abstract class AbstractImage implements Image {
    public Image subtract(DefaultImage other) {
        double[] resultPixels = new double[this.attributesCount()];
        int w = this.getWidth();
        int h = this.getHeight();

        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++w) {
                resultPixels[y * w + x] = this.getPixel(x, y) - other.getPixel(x, y);
            }
        }

        return new DefaultImage(w, h, resultPixels);
    }

    public Image subtract(double value) {
        double[] resultPixels = new double[this.attributesCount()];
        int w = this.getWidth();
        int h = this.getHeight();

        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++w) {
                resultPixels[y * w + x] = this.getPixel(x, y) - value;
            }
        }

        return new DefaultImage(w, h, resultPixels);
    }
}
