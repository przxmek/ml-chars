package mlchars;

import mlchars.clustering.*;
import mlchars.metric.PixelMetric;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public class MLChars {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Program run with invalid parameters.");
            System.exit(1);
        }

        String data_path = args[0];
        String output_file = args[1];

        System.err.println(String.format("Data path: %s\nOutput file: %s\n", data_path, output_file));

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

//        Clusterer clusterer = new DBSCAN(1, 100, new PixelMetric());
        Clusterer clusterer = new FarthestFirst(600, new PixelMetric());
//        Clusterer clusterer = new Cobweb();
//        Clusterer clusterer = new KMeans(100, 20, new PixelMetric());

        ImageDataset[] result = clusterer.cluster(dataset);

        printOutput(false, result);

        // Copy images
        organizeImages("clusters", data_path, result);
    }

    private static void printOutput(boolean nice, ImageDataset[] result) {
        for (ImageDataset group : result) {
            if (group.size() == 0)
                continue;
            for (int j = 0; j < group.size(); ++j)
                System.out.print(String.format("%s ", ((DefaultImage) group.getImage(j)).getLabel()));
            System.out.println();
        }
    }

    private static void organizeImages(String targetPath, String dataPath, ImageDataset[] result) throws IOException {
        int groupCount = 0;
        File clusterDir = new File(targetPath);
        if (clusterDir.mkdir()) {
            for (int i = 0; i < result.length; ++i) {
                if (result[i].size() == 0)
                    continue;
                ++groupCount;
                File groupDir = new File(clusterDir, Integer.toString(groupCount));
                if (groupDir.mkdir()) {
                    for (int j = 0; j < result[i].size(); ++j) {
                        String fileName = "/" + ((DefaultImage) result[i].getImage(j)).getLabel();
                        Path src = new File(dataPath.concat(fileName)).toPath();
                        Path dest = new File(groupDir.getPath().concat(fileName)).toPath();
                        Files.copy(src, dest);
                    }
                } else {
                    System.err.print(String.format("Failed to create %s directory.\n", groupDir.getPath()));
                }
            }
        } else {
            System.err.print(String.format("Failed to create %s directory.\n", clusterDir.getPath()));
        }
    }
}
