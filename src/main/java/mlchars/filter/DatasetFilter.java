package mlchars.filter;

import mlchars.ImageDataset;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public interface DatasetFilter {
    void build(ImageDataset data);

    void filter(ImageDataset data);
}
