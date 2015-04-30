package mlchars;

import mlchars.metric.Metric;

import java.util.*;


/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public class ImageDatasetDefault implements ImageDataset {

    List<Image> data = new ArrayList<Image>();

    public ImageDatasetDefault() {
        // nothing to do.
    }

    public synchronized boolean add(Image img) {
        // check(img);
        return data.add(img);
    }

    public synchronized boolean addAll(Collection<? extends Image> c) {
        // check(img);
        return data.addAll(c);
    }

    public void clear() {
        data.clear();
    }

    public Image getImage(int index) {
        return data.get(index);
    }

    public Set<Image> kNearest(int k, Image img, Metric metric) {
        Map<Image, Double> closest = new HashMap<Image, Double>();
        double max = metric.getMaxValue();
        for (Image i : data) {
            if (img.equals(i))
                continue;
            double d = metric.getDistanceBetween(img, i);
            if (metric.compare(d, max)) {
                closest.put(i, d);
                if (closest.size() > k)
                    max = removeFurthest(closest, metric);
            }
        }

        return closest.keySet();
    }

    public int size() {
        return data.size();
    }

    public int getMaxWidth() {
        int maxW = data.get(0).getWidth();

        for (Image i : data) {
            int w = i.getWidth();
            if (w > maxW)
                maxW = w;
        }

        return maxW;
    }

    public int getMaxHeight() {
        int maxH = data.get(0).getHeight();

        for (Image i : data) {
            int w = i.getHeight();
            if (w > maxH)
                maxH = w;
        }

        return maxH;
    }

    public double getMinPixel(int x, int y) {
        double minP = data.get(0).getPixel(x, y);

        for (Image i : data) {
            double p = i.getPixel(x, y);
            if (p < minP)
                minP = p;
        }

        return minP;
    }

    public double getMaxPixel(int x, int y) {
        double maxP = data.get(0).getPixel(x, y);

        for (Image i : data) {
            double p = i.getPixel(x, y);
            if (p > maxP)
                maxP = p;
        }

        return maxP;
    }

    private double removeFurthest(Map<Image, Double> closest, Metric metric) {
        Image furthest = null;
        double max = metric.getMinValue();

        for (Image i : closest.keySet()) {
            double d = closest.get(i);

            if (metric.compare(max, d)) {
                max = d;
                furthest = i;
            }
        }

        closest.remove(furthest);
        return max;
    }
}
