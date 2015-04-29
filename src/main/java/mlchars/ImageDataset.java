package mlchars;

import mlchars.metric.Metric;

import java.util.Collection;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public interface ImageDataset {
    boolean add(Image img);

    boolean addAll(Collection<? extends Image> c);

    Image getImage(int index);

    java.util.Set<Image> kNearest(int k, Image img, Metric metric);

    int size();
}
