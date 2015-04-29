package mlchars;

import mlchars.metrics.Metric;

import java.util.Collection;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public interface ImageDataset {
    public boolean add(Image img);

    public boolean addAll(Collection<? extends Image> c);

    public Image getImage(int index);

    public java.util.Set<Image> kNearest(int k, Image img, Metric metric);
}
