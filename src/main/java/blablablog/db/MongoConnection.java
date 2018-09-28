package blablablog.db;

import blablablog.models.BaseMongoDO;
import com.mongodb.*;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MongoConnection {
    private static Logger logger = Logger.getLogger(MongoConnection.class);
    private static MongoConnection instance = new MongoConnection();

    private MongoClient mongo = null;
    private Datastore dataStore = null;
    private Morphia morphia = null;

    private MongoConnection() {}

    public MongoClient getMongo(MongoConfig config) throws RuntimeException {
        if (mongo == null) {
            logger.debug("Starting Mongo");
            MongoClientOptions.Builder options = MongoClientOptions.builder()
                    .connectionsPerHost(config.getConnectionsPerHost())
                    .maxConnectionIdleTime(config.getMaxConnectionIdleTime() * 1000)
                    .maxConnectionLifeTime(config.getMaxConnectionLifeTime() * 1000);
            MongoClientURI uri = new MongoClientURI("mongodb://" + config.getHost() + ":" + config.getPort() + "/" + config.getDbname(), options);
            logger.info("About to connect to MongoDB @ " + uri.toString());
            try {
                mongo = new MongoClient(uri);
                mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
            } catch (MongoException ex) {
                logger.error("An error occoured when connecting to MongoDB", ex);
            } catch (Exception ex) {
                logger.error("An error occoured when connecting to MongoDB", ex);
            }

            // To be able to wait for confirmation after writing on the DB
            mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        }

        return mongo;
    }

    public Morphia getMorphia() {
        if (morphia == null) {
            logger.debug("Starting Morphia");
            morphia = new Morphia();

            logger.debug("Mapping packages for classes within " + BaseMongoDO.class.getName());
            morphia.mapPackageFromClass(BaseMongoDO.class);
        }
        return morphia;
    }

    public Datastore getDatastore(MongoConfig config) {
        if (dataStore == null) {
            String dbName = "testdb";
            logger.debug("Starting DataStore on DB: " + dbName);
            dataStore = getMorphia().createDatastore(getMongo(config), dbName);
        }
        return dataStore;
    }

    public void init(MongoConfig config) {
        getMongo(config);
        getMorphia();
        getDatastore(config);
    }

    public void close() {
        logger.info("Closing MongoDB connection");
        if (mongo != null) {
            try {
                mongo.close();
                logger.debug("Nulling the connection dependency objects");
                mongo = null;
                morphia = null;
                dataStore = null;
            } catch (Exception e) {
                logger.error("An error occurred when closing the MongoDB connection\n " +  e.getMessage());
            }
        } else {
            logger.warn("mongo object was null, wouldn't close connection");
        }
    }

    public static MongoConnection getInstance() {
        return instance;
    }
}
