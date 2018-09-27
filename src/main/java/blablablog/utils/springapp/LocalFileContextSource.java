package blablablog.utils.springapp;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LocalFileContextSource extends AbstractSpringContextSource {

    private static final Logger log = Logger.getLogger(LocalFileContextSource.class.getName());
    public final File file;

    public LocalFileContextSource(File file) {
        this.file = file;
    }

    public LocalFileContextSource(String file) {
        this(new File(file));
    }


    @Override
    public boolean isAvailable() {
        return file.exists() && file.canRead() && file.isFile();
    }

    @Override
    public InputStream getInputStream() throws SpringApplicationException {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new SpringApplicationException("Unable to open file input stream. " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "LocalFileContextSource{" +
                "file=" + file.getAbsolutePath() +
                '}';
    }
}
