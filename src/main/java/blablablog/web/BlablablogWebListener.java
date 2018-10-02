package blablablog.web;

import blablablog.BlablablogService;
import blablablog.mongo.MongoConnection;
import blablablog.utils.io.IOUtils;
import blablablog.utils.springapp.ApplicationSpringContext;
import blablablog.utils.springapp.SpringApplicationException;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class BlablablogWebListener implements ServletContextListener {
    private static final Logger log = Logger.getLogger(BlablablogWebListener.class.getName());
    public static String PATH_TO_CONFIG_FILE = "/etc/blablablog/blablablog.xml";

    public BlablablogWebListener() {
    }

    /**
     * On web content initialized
     *
     * @param servletContextEvent servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.trace("Blablablog content initialized");
        try {
            ApplicationContext context;
            if (IOUtils.isFileExist(PATH_TO_CONFIG_FILE)) {
                log.info("Load configuration from " + PATH_TO_CONFIG_FILE);
                context = ApplicationSpringContext.getFromFile(PATH_TO_CONFIG_FILE);
                //initialize database service
                BlablablogService.init(context);
            } else {
                log.info("Load configuration from embed source.");
                context = ApplicationSpringContext.getFromEmbedSource(PATH_TO_CONFIG_FILE);
                //initialize database service
                BlablablogService.init(context);
            }
        } catch (SpringApplicationException e) {
            log.fatal(e);
            System.exit(0);
        }
    }

    /**
     * On web content destroyed
     *
     * @param servletContextEvent servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("Blablablog content destroyed");
        MongoConnection conn = MongoConnection.getInstance();
        conn.close();
    }
}
