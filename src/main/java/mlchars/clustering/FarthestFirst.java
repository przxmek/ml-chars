package mlchars.clustering;

import mlchars.Image;
import mlchars.ImageDataset;
import mlchars.ImageDatasetDefault;
import mlchars.metric.Metric;
import mlchars.metric.PixelMetric;

import java.util.Random;

/**
 * Created by Przemysław Kuczyński on 4/30/15.
 */
public class FarthestFirst implements Clusterer {
    private ImageDataset data;

    private int numClusters = 2;

    private Image[] centroids;

    private Metric metric;

    private Random random;

    public FarthestFirst() {
        this(4, new PixelMetric());
    }

    public FarthestFirst(int numClusters, Metric metric) {
        this.numClusters = numClusters;
        this.metric = metric;
        this.random = new Random(System.currentTimeMillis());
    }

    public ImageDataset[] cluster(ImageDataset data) {
        this.data = data;
        centroids = new Image[numClusters];

        int dataSize = data.size();
        boolean[] selected = new boolean[dataSize];
        double[] minDistance = new double[dataSize];

        for(int i = 0; i < dataSize; i++)
            minDistance[i] = Double.MAX_VALUE;

        int firstI = random.nextInt(dataSize);
        centroids[0] = data.getImage(firstI);
        selected[firstI] = true;

        updateMinDistance(minDistance, selected, centroids[0]);

        if (numClusters > dataSize)
            numClusters = dataSize;

        for (int i = 1; i < numClusters; ++i) {
            int nextI = farthestAway(minDistance, selected);
            centroids[i] = data.getImage(nextI);
            selected[nextI] = true;
            updateMinDistance(minDistance, selected, centroids[i]);
        }

        ImageDataset[] clusters = new ImageDataset[numClusters];
        for (int i = 0; i < clusters.length; ++i) {
            clusters[i] = new ImageDatasetDefault();
        }

        for (int i = 0; i < dataSize; ++i) {
            Image img = data.getImage(i);
            int index = 0;
            double min = metric.getDistanceBetween(img, centroids[index]);
            for (int j = 1; j < numClusters; ++j) {
                double tmp = metric.getDistanceBetween(img, centroids[j]);
                if (tmp < min) {
                    min = tmp;
                    index = j;
                }
            }
            clusters[index].add(img);
        }

        return clusters;
    }

    private void updateMinDistance(double[] minDistance, boolean[] selected, Image center) {
        for (int i = 0; i < selected.length; ++i) {
            if (!selected[i]) {
                double d = metric.getDistanceBetween(center, data.getImage(i));
                if (d < minDistance[i])
                    minDistance[i] = d;
            }
        }
    }

    private int farthestAway(double[] minDistance, boolean[] selected) {
        double maxDistance = -1.0f;
        int maxI = -1;
        for (int i = 0; i < selected.length; ++i) {
            if (!selected[i]) {
                if (maxDistance < minDistance[i]) {
                    maxDistance = minDistance[i];
                    maxI = i;
                }
            }
        }

        return maxI;
    }
}
