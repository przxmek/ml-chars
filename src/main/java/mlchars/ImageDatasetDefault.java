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

    Metric metric;
    double[][] distanceMatrix = null;
    ArrayList<SortedMap<Double, List<Integer>>> neighbourMatrix = null;

    private double[][] getDistanceMatrix(Metric metric) {
        if (this.distanceMatrix == null || this.metric != metric) {
            this.metric = metric;
            createDistanceMatrix();
        }
        return distanceMatrix;
    }

    public ArrayList<SortedMap<Double, List<Integer>>> getNeighbourMatrix(Metric metric) {
        if (this.neighbourMatrix == null || this.metric != metric) {
            this.metric = metric;
            createSortedDistanceList();
        }
        return neighbourMatrix;
    }

    private void createDistanceMatrix() {
        int dataSize = data.size();

        distanceMatrix = new double[dataSize][dataSize];

        for (int i = 0; i < dataSize - 1; i++) {
            for (int j = i + 1; j < dataSize; j++)
                distanceMatrix[i][j] = metric.getDistanceBetween(getImage(i), getImage((j)));
        }
    }

    /**
     * For every element in the dataset this method calculates the distance to all
     * the other elements. It is assumed that the used distance measurement is symetric.
     * Therefore only an upper diagonal matrix is created. The distances are
     * then stored in a sorted map so that it is clear which distance belongs
     * to which element (via the index).
     * This method is useful for finding the n-nearest neighbours of an element
     * of finding all elements that are within a certain radius to the element.
     *
     * @return
     */
    private void createSortedDistanceList() {
        if (this.distanceMatrix == null) {
            this.createDistanceMatrix();
        }

        int dataSize = data.size();
        double dist;
        ArrayList<SortedMap<Double, List<Integer>>> sortedMap = new ArrayList<SortedMap<Double, List<Integer>>>(dataSize);

        for (int i = 0; i < dataSize; ++i) {
            sortedMap.add(i, new TreeMap<Double, List<Integer>>());
//            sortedMap.set(i, new TreeMap<Double, List<Integer>>());
            for (int j = 0; j < dataSize; ++j) {
                if (i != j) {
                    dist = this.getDistance(i, j);
                    Object l = sortedMap.get(i).get(dist);
                    List<Integer> tmp = new ArrayList<Integer>();
                    if (l == null){
                        sortedMap.get(i).put(dist, tmp);
                    }
                    tmp.add(j);
                }
            }

        }
        neighbourMatrix = sortedMap;

    }

    /**
     * This method is used to normalize querys on the distance Matirx. That Way only
     * the upper half of the matrix has to be stored since we expect the
     * distance measurement to be symetrical.
     * @param image1 index of one of the points that we are interested in in the dataset
     * @param image2 index of the other of the points that we are interested in in the dataset
     * @return distance between the two points
     */
    private double getDistance (int image1, int image2){
        if (image1 < image2)
            return distanceMatrix[image1][image2];
        else
            return distanceMatrix[image2][image1];
    }


}
