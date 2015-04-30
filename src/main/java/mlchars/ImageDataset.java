package mlchars;

import mlchars.metric.Metric;

import java.util.Collection;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public interface ImageDataset {
    boolean add(DefaultImage img);

    boolean addAll(Collection<? extends DefaultImage> c);

    DefaultImage getImage(int index);

    java.util.Set<DefaultImage> kNearest(int k, DefaultImage img, Metric metric);

    int size();

    int getMaxWidth();

    int getMaxHeight();

    double getMinPixel(int x, int y);

    double getMaxPixel(int x, int y);
}
