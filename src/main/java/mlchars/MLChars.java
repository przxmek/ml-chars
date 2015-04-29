package mlchars;

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

        if (!data_path.endsWith("/"))
            data_path = data_path.concat("/");

        ImageFileLoader loader = ImageFileLoader.getInstance();
        String[] imageFiles = loader.getImageFileNamesInDir(new File(data_path));
        if (imageFiles == null) {
            System.err.print(String.format("No image files found in %s.", data_path));
            System.exit(1);
        }

        List<Image> images = new ArrayList<Image>(imageFiles.length);
        for (String imgFile : imageFiles)
            images.add(new Image(new File(data_path.concat(imgFile))));

        ImageDataset dataset = new ImageDatasetDefault();
        dataset.addAll(images);

        System.out.println(String.format("Data path: %s\nOutput file: %s\n", data_path, output_file));
    }
}
