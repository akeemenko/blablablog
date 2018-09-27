package blablablog.utils.io;

import java.io.File;

/**
 * Утилита, чтобы приводить пути к папкам к нужному виду для корректной конкатинации.
 *
 * @author max
 */
public class FolderUtils {

    public static String setFolderWithSeparator(String folder) {
        return folder.endsWith(File.separator) ? folder : folder + File.separator;
    }

    public static String setFolderWithoutSeparator(String folder) {
        return folder.endsWith(File.separator) ? folder.substring(0, folder.length() - 1) : folder;
    }

}
