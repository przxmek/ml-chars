package mlchars;

import mlchars.clustering.Clusterer;
import mlchars.clustering.Cobweb;
import mlchars.clustering.KMeans;
import mlchars.metric.PixelMetric;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public class MLChars {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Program run with invalid parameters.");
            System.exit(1);
        }

        String data_path = args[0];
        String output_file = args[1];

        System.out.println(String.format("Data path: %s\nOutput file: %s\n", data_path, output_file));

        if (!data_path.endsWith("/"))
            data_path = data_path.concat("/");

        ImageFileLoader loader = ImageFileLoader.getInstance();
        String[] imageFiles = loader.getImageFileNamesInDir(new File(data_path));
        if (imageFiles == null) {
            System.err.print(String.format("No image files found in %s.", data_path));
            System.exit(1);
        }

        List<DefaultImage> images = new ArrayList<DefaultImage>(imageFiles.length);
        for (String imgFile : imageFiles)
            images.add(new DefaultImage(new File(data_path.concat(imgFile))));

        ImageDataset dataset = new ImageDatasetDefault();
        dataset.addAll(images);
        Clusterer clusterer = new Cobweb();
//        Clusterer clusterer = new KMeans(100, 20, new PixelMetric());
        ImageDataset[] result = clusterer.cluster(dataset);

        int groupCount = 0;
        for (int i = 0; i < result.length; ++i) {
            if (result[i].size() == 0)
                continue;
            ++groupCount;
            System.out.print(String.format("GROUP %d: ", groupCount));
            for (int j = 0; j < result[i].size(); ++j)
                System.out.print(String.format("%s, ", ((DefaultImage) result[i].getImage(j)).getLabel()));
            System.out.println();
        }
    }
}
