package mlchars.clustering;

/**
 * Created by Przemysław Kuczyński on 30.04.15.
 */
import mlchars.Image;
import mlchars.ImageDataset;
import mlchars.ImageDatasetDefault;
import mlchars.metric.Metric;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

/**
 * @author Markus
 *
 */
public class DBSCAN implements Clusterer {

    double epsilon;
    int minPoints;
    int[] cluster;

    private Metric metric;

    public DBSCAN(double epsilon, int minPoints, Metric metric) {
        this.epsilon = epsilon;
        this.minPoints = minPoints;
        this.metric = metric;
    }

    public ImageDataset[] cluster(ImageDataset data) {
        if (data.size() == 0)
            throw new RuntimeException("The dataset should not be empty.");

        int dataSize = data.size();
        int currentClusterId = 2;
        cluster = new int[dataSize + 2];

        for (int i = 0; i < dataSize; ++i) {
            Image img = data.getImage(i);
            if (0 == cluster[i]) {
                if (expandCluster(i, currentClusterId, epsilon, minPoints, data)) //; FIXME was this bug?
                    ++currentClusterId;
            }
        }

        ImageDataset output[] = new ImageDataset[currentClusterId + 1];
        for (int i = 0; i < output.length; ++i)
            output[i] = new ImageDatasetDefault();

        for (int i = 0; i < dataSize; ++i)
            output[cluster[i]].add(data.getImage(i));

        return output;
    }

    private boolean expandCluster(int pointNumber, int currentClusterId, double gamma2, int minPoints2, ImageDataset data) {
        LinkedList<Integer> seeds = (LinkedList<Integer>) getNeighbours(data, pointNumber);
        if (seeds.size() < this.minPoints){ //no core point
            cluster[pointNumber] = 1;
            return false;
        }
        else{
            for (Integer i : seeds) {
                cluster[i] = currentClusterId;
            }
            seeds.remove(pointNumber);

            while (!seeds.isEmpty()){
                int currentPoint = seeds.getFirst();
                Collection<Integer> result = this.getNeighbours(data, currentPoint);

                if (result.size() >= this.minPoints){
                    for (int resultPId : result) {
                        if (cluster[resultPId] == 0){
                            seeds.addLast(resultPId);
                            cluster[resultPId] = currentClusterId;
                        }
                        if (cluster[resultPId] == 1){
                            cluster[resultPId] = currentClusterId;
                        }
                    }
                }
                seeds.remove((Integer)currentPoint);
            }
            return true;
        }

    }
    /**
     * This method queries the dataset for the neighbours of the passed element (id) that
     * satisfy  [dist (id, x) <= epsilon for x element of dataset] .
     * The method returns the ids of the points that fulfill the condition
     * @param data
     * @param id
     * @return list of ids that are in the epsilon neighborhood
     */
    private Collection<Integer> getNeighbours(ImageDataset data, int id) {
        SortedMap<Double, List<Integer>> neigbourList = ((ImageDatasetDefault) data).getNeighbourMatrix(metric).get(id);
        List<Integer> result = new LinkedList<>();
        //because the api returns strictly smaller we add  a small value.
        Collection <List<Integer>>closepoints = neigbourList.headMap(epsilon +0.0000000000000001f).values();
        for (List<Integer> list : closepoints) {
            result.addAll(list);
        }
        return result;

    }
}

