package mlchars;

import mlchars.metrics.Metric;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public class Image {
    private int mWidth, mHeight;
    private boolean mPixels[];

    public Image(File imageFile) throws IOException {
        BufferedImage img = javax.imageio.ImageIO.read(imageFile);

        Rectangle r = trimLeftAndUp(img);
        int[] rgbPixels = img.getRGB(r.x, r.y, r.width, r.height, null, 0, r.width);

        mWidth = r.width;
        mHeight = r.height;

        mPixels = new boolean[rgbPixels.length];
        for (int i = 0; i < mPixels.length; ++i)
            mPixels[i] = rgbPixels[i] != -1;
    }

    public long getDistanceFrom(Image other, Metric metric) {
        return metric.getDistanceBetween(this, other);
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public boolean[] getPixels() {
        return mPixels;
    }

    public void printASCII() {
        for (int y = 0; y < mHeight; ++y) {
            for (int x = 0; x < mWidth; ++x) {
                if (mPixels[y * mWidth + x])
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
