package mlchars.clustering;

import mlchars.DefaultImage;
import mlchars.Image;
import mlchars.ImageDataset;
import mlchars.ImageDatasetDefault;
import mlchars.metric.Metric;
import mlchars.util.DatasetUtil;

import java.util.Random;

/**
 * Created by Przemysław Kuczyński on 4/30/15.
 */
public class KMeans implements Clusterer {
    private static final double EPSILON = 0.0001;

    private int numberOfClusters = -1;
    private int numberOfIterations = -1;
    private Random random;
    private Metric metric;


    public KMeans(int clusters, int iterations, Metric metric) {
        this.numberOfClusters = clusters;
        this.numberOfIterations = iterations;
        this.random = new Random(System.currentTimeMillis());
        this.metric = metric;
    }

    public ImageDataset[] cluster(ImageDataset data) {
        if (data.size() == 0)
            throw new RuntimeException("The dataset should not be empty.");
        if (numberOfClusters == 0)
            throw new RuntimeException("There should be at least one cluster.");

        Image min = DatasetUtil.minAttributes(data);
        Image max = DatasetUtil.maxAttributes(data);
        Image[] centroids = new DefaultImage[numberOfClusters];

        int maxW = data.getMaxWidth();
        int maxH = data.getMaxHeight();
        int maxLength = maxW * maxH;
        for (int j = 0; j < numberOfClusters; ++j)
            centroids[j] = DatasetUtil.getRandomImage(data, random);

        int iterationCount = 0;
        boolean centroidsChanged = true;
        boolean randomCentroids = true;

        while (randomCentroids || (iterationCount < numberOfIterations && centroidsChanged)) {
            ++iterationCount;
            System.err.print(String.format("Iteration %d/%d\n", iterationCount, numberOfIterations));

            // Assign each object to the group that has the closest centroid.
            int[] assignment = new int[data.size()];
            for (int i = 0; i < assignment.length; ++i) {
                int tmpCluster = 0;
                double minDistance = metric.getDistanceBetween(centroids[tmpCluster], data.getImage(i));

                for (int j = 1; j < centroids.length; ++j) {
                    double dist = metric.getDistanceBetween(centroids[j], data.getImage(i));
                    if (metric.compare(dist, minDistance)) {
                        minDistance = dist;
                        tmpCluster = j;
                    }
                }
                assignment[i] = tmpCluster;
            }

            // Recalculate positions of the K centroids.
            double[][] sumPosition = new double[numberOfClusters][maxLength];
            int[] countPosition = new int[numberOfClusters];
            for (int i = 0; i < data.size(); ++i) {
                Image img = data.getImage(i);
                for (int y = 0; y < maxH; ++y) {
                    for (int x = 0; x < maxW; ++x) {
                        sumPosition[assignment[i]][y * maxW + x] += img.getPixel(x, y);
                    }
                }
                ++countPosition[assignment[i]];
            }
            centroidsChanged = false;
            randomCentroids = false;
            for (int i = 0; i < numberOfClusters; ++i) {
                if (countPosition[i] > 0) {
                    double[] tmp = new double[maxLength];

                    for (int j = 0; j < maxLength; ++j)
                        tmp[j] = (float) sumPosition[i][j] / countPosition[i];

                    Image newCentroid = new DefaultImage(maxW, maxH, tmp);
                    if (metric.getDistanceBetween(newCentroid, centroids[i]) > EPSILON) {
                        centroidsChanged = true;
                        centroids[i] = newCentroid;
                    }
                } else if (iterationCount < numberOfIterations){
                    double[] randomImage = new double[maxLength];

                    for (int y = 0; y < maxH; ++y) {
                        for (int x = 0; x < maxW; ++x) {
                            double dist = Math.abs(max.getPixel(x, y) - min.getPixel(x, y));
                            randomImage[y * maxW + x] = (float) (min.getPixel(x, y) + random.nextDouble() * dist);
                        }
                    }

                    randomCentroids = true;
                    centroids[i] = new DefaultImage(maxW, maxH, randomImage);
                }
            }
        }
        ImageDataset output[] = new ImageDataset[centroids.length];
        for (int i = 0; i < centroids.length; ++i)
            output[i] = new ImageDatasetDefault();
        for (int i = 0; i < data.size(); ++i) {
            int tmpCluster = 0;
            double minDistance = metric.getDistanceBetween(centroids[tmpCluster], data.getImage(i));
            for (int j = 1; j < centroids.length; ++j) {
                double dist = metric.getDistanceBetween(centroids[j], data.getImage(i));
                if (metric.compare(dist, minDistance)) {
                    minDistance = dist;
                    tmpCluster = j;
                }
            }
            output[tmpCluster].add(data.getImage(i));
        }
        return output;
    }
}
