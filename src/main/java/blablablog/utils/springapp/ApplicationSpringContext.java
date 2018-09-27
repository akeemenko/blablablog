package blablablog.utils.springapp;

import blablablog.utils.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author Mr. IP.
 */
public class ApplicationSpringContext {

    private static final Logger log = Logger.getLogger(ApplicationSpringContext.class.getName());

    public static ApplicationContext getFromFile(String localFilePath) throws SpringApplicationException {
        log.trace("Init context from " + localFilePath);
        ApplicationContext appContext;
        File file = new File(localFilePath);
        if (!file.exists()) {
            throw new SpringApplicationException("Config file not found (" + localFilePath + ").");
        }
        if (!file.canRead()) {
            throw new SpringApplicationException("Permission access denied to read config file (" + localFilePath + ").");
        }
        if (file.length() == 0) {
            throw new SpringApplicationException("Unable to use zero length config file. (" + localFilePath + ").");
        }
        try {
            FileInputStream fis = new FileInputStream(new File(localFilePath));
            appContext = getFromInputStream(fis);
            fis.close();
            return appContext;
        } catch (IOException e) {
            throw new SpringApplicationException("Unable to construct context from file `" + localFilePath + "`. " + e.getMessage(), e);
        }
    }

    public static ApplicationContext getFromResource(AbstractSpringContextSource source) throws SpringApplicationException {
        return getFromInputStream(source.getInputStream());
    }

    public static ApplicationContext getFromInputStream(InputStream inputStream) throws SpringApplicationException {
        try {
            log.trace("Init context from input stream");
            ApplicationContext appContext;
            byte[] content = IOUtils.readFromStream(inputStream);

            appContext = new GenericXmlApplicationContext(new ByteArrayResource(content));
            return appContext;
        } catch (Exception e) {
            throw new SpringApplicationException("Unable to create context from input stream. " + e.getMessage(), e);
        }
    }

    public static ApplicationContext lookup(ArrayList<AbstractSpringContextSource> sources) throws SpringApplicationException {
        log.info("Lookup for context source from " + sources.size() + " sources.");
        ApplicationContext context = null;
        for (int i = 0; i < sources.size(); i++) {
            AbstractSpringContextSource s = sources.get(i);
            log.info("try source " + s.toString());
            if (sources.get(i).isAvailable()) {
                try {
                    context = getFromInputStream(s.getInputStream());
                    if (context != null) {
                        break;
                    }
                } catch (Exception e) {
                    log.info("Unable to build context from source. " + e.getMessage());
                }
            }
        }
        if (context == null) {
            log.warn("All sources are spent. Unable to create context.");
            throw new SpringApplicationException("Unable to create context");
        }
        return context;
    }

    public static ApplicationContext getFromEmbedSource(String path) throws SpringApplicationException {
        InputStream is = ApplicationSpringContext.class.getResourceAsStream(path);
        if (is == null) {
            throw new SpringApplicationException("Resource `" + path + "` not found.");
        }
        return getFromInputStream(is);
    }
}
