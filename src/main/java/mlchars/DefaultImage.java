package mlchars;

import mlchars.metric.Metric;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public class DefaultImage extends AbstractImage implements Image {
    private String label = null;
    private int width = -1;
    private int height = -1;
    private double pixels[];

    public DefaultImage(File imageFile) throws IOException {
        BufferedImage img = javax.imageio.ImageIO.read(imageFile);

        Rectangle r = trimLeftAndUp(img);
        int[] rgbPixels = img.getRGB(r.x, r.y, r.width, r.height, null, 0, r.width);

        label = imageFile.getName();
        width = r.width;
        height = r.height;

        pixels = new double[rgbPixels.length];
        for (int i = 0; i < pixels.length; ++i)
            pixels[i] = (rgbPixels[i] == -1) ? 0.f : 1.f;
    }

    public DefaultImage(int width, int height, double[] pixels) {
        if (width * height != pixels.length)
            throw new RuntimeException("Pixels array does not match the image dimensions.");

        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public double getDistanceFrom(DefaultImage other, Metric metric) {
        return metric.getDistanceBetween(this, other);
    }

    public String getLabel() {
        return label;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double[] getPixels() {
        return pixels;
    }

    public double getPixel(int x, int y) {
        if (x < 0 || y < 0)
            return 0;
        if (x < width && y < height)
            return pixels[y * width + x];
        return 0;
    }

    public int attributesCount() {
        return pixels.length;
    }

    public void printASCII() {
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                if (pixels[y * width + x] != 0)
                    System.out.print("#");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }


    private Rectangle trimLeftAndUp(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int trimX = 0;
        int trimY = 0;

        // Trim vertically
        while (trimX < width) {
            boolean hasPixels = false;
            int[] pixels = img.getRGB(trimX, 0, 1, height, null, 0, 1);

            for (int pixel : pixels) {
                if (pixel != -1) {
                    hasPixels = true;
                    break;
                }
            }
            if (hasPixels)
                break;
            ++trimX;
        }

        // Trim horizontally
        while (trimY < height) {
            boolean hasPixels = false;
            int[] pixels = img.getRGB(0, trimY, width, 1, null, 0, width);

            for (int pixel : pixels) {
                if (pixel != -1) {
                    hasPixels = true;
                    break;
                }
            }
            if (hasPixels)
                break;
            ++trimY;
        }

        return new Rectangle(trimX, trimY, width - trimX, height - trimY);
    }
}
