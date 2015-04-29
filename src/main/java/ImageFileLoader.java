import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.Buffer;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public class ImageFileLoader {

    private static ImageFileLoader sIntance = null;

    public static ImageFileLoader getInstance() {
        if (sIntance == null)
            sIntance = new ImageFileLoader();
        return sIntance;
    }

    private ImageFileLoader() {
    }

    public String[] getImageFileNamesInDir(File dir) {
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".png");
            }
        };

        return dir.list(filter);
    }

    public BufferedImage getImage(String imagePath) {
        try {
            return javax.imageio.ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
