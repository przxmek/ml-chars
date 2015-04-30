package mlchars;

/**
 * Created by Przemysław Kuczyński on 4/30/15.
 */
public interface Image {
    int attributesCount();

    int getWidth();

    int getHeight();

    double getPixel(int x, int y);

    Image subtract(DefaultImage other);

    Image subtract(double value);
}
