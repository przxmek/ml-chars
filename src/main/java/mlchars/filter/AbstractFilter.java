package mlchars.filter;

import mlchars.Image;
import mlchars.ImageDataset;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public abstract class AbstractFilter implements DatasetFilter, InstanceFilter {
    public void build(ImageDataset data) {
    }

    public void filter(ImageDataset data) {
        int dataSize = data.size();
        for (int i = 0; i < dataSize; ++i)
            filter(data.getImage(i));
    }

    public abstract void filter(Image img);
}
