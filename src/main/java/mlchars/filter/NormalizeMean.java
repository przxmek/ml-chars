package mlchars.filter;

import mlchars.DefaultImage;
import mlchars.ImageDataset;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public class NormalizeMean extends AbstractFilter {

    private DefaultImage mean = null;
    private DefaultImage std = null;

    @Override
    public void filter(ImageDataset data) {
        if (data.size() == 0)
            return;

//        mean = DatasetTools.average(data);
//        std = DatasetTools.standardDeviation(data, mean);

        int dataSize = data.size();
        for (int i = 0; i < dataSize; ++i)
            filter(data.getImage(i));
    }

    @Override
    public void filter(DefaultImage img) {
//        if (mean == null || std == null)
//            throw new RuntimeException(
//                    "You should first call filterDataset for this filter, some parameters are not yet set."
//            );

//        DefaultImage i = img.minus(mean).divide(std);
//        for (int i = 0; i < img.attributesCount(); ++i)
//            img.put(i, tmp.value(i));
    }
}
