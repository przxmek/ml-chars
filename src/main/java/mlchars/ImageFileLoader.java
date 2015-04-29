package mlchars;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Przemysław Kuczyński on 4/29/15.
 */
public class ImageFileLoader {

    private static ImageFileLoader sIntance = null;

    private ImageFileLoader() {
    }

    public static ImageFileLoader getInstance() {
        if (sIntance == null)
            sIntance = new ImageFileLoader();
        return sIntance;
    }

    public String[] getImageFileNamesInDir(File dir) {
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".png");
            }
        };

        return dir.list(filter);
    }
}
