package mlchars.clustering;

import mlchars.ImageDataset;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public interface Clusterer {
    ImageDataset[] cluster(ImageDataset data);
}
