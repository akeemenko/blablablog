package blablablog;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * Created by UNKNOWN on 22.01.2018.
 */
public class BlablablogService {
    private static final Logger log = Logger.getLogger(BlablablogService.class.getName());
    private static BlablablogService instance;


    public BlablablogService(ApplicationContext applicationContext) {
        String testBean = (String) applicationContext.getBean("test");
        instance = this;
    }

    /**
     * Init mp auth service
     *
     * @param applicationContext application context
     * @return instance of bcdb api service
     */
    public static BlablablogService init(ApplicationContext applicationContext) {
        if (instance != null) {
            log.fatal("Unable to initialize new instance of GectacoinApi service");
            return instance;
        } else {
            return new BlablablogService(applicationContext);
        }
    }

}