package mlchars.filter;

import mlchars.DefaultImage;
import mlchars.Image;
import mlchars.ImageDataset;
import mlchars.util.DatasetUtil;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public class NormalizeMean extends AbstractFilter {

    private Image mean = null;
    private Image std = null;

    @Override
    public void filter(ImageDataset data) {
        if (data.size() == 0)
            return;

        mean = DatasetUtil.average(data);
        std = DatasetUtil.standardDeviation(data, mean);

        int dataSize = data.size();
        for (int i = 0; i < dataSize; ++i)
            filter(data.getImage(i));
    }

    @Override
    public void filter(Image img) {
        if (mean == null || std == null)
            throw new RuntimeException(
                    "You should first call filterDataset for this filter, some parameters are not yet set."
            );

        Image tmp = img.subtract(mean).divide(std);
        ((DefaultImage) img).setPixels(((DefaultImage) tmp).getPixels());
    }
}
