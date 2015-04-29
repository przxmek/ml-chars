import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public class MLChars {

    public static void main(String[] args) throws IOException {
        System.out.println(args.length);
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

        for (String imgFile : imageFiles) {
            BufferedImage img = loader.getImage(data_path.concat(imgFile));
            int w = img.getWidth();
            int h = img.getHeight();

            int[] pixels = img.getRGB(0, 0, w, h, null, 0, w);

            boolean[] bits = new boolean[pixels.length];
            for (int i = 0; i < pixels.length; ++i) {
                bits[i] = pixels[i] != -1;
            }

            // Printing
            System.out.println(imgFile);
            for (int y = 0; y < h; ++y) {
                for (int x = 0; x < w; ++x) {
                    if (bits[y * w + x])
                        System.out.print("#");
                    else
                        System.out.print(" ");
                }
                System.out.println();
            }
        }

        System.out.println(String.format("Data path: %s\nOutput file: %s\n", data_path, output_file));
    }
}
