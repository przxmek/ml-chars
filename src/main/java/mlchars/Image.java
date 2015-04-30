package mlchars;

/**
 * Created by Przemysław Kuczyński on 4/30/15.
 */
public interface Image {
    int attributesCount();

    int getWidth();

    int getHeight();

    double getPixel(int x, int y);

    Image add(Image other);

    Image subtract(Image other);

    Image subtract(double value);

    Image multiply(Image other);

    Image divide(double value);

    Image divide(Image other);

    Image sqrt();
}
