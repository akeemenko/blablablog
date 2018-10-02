package blablablog.mongo;

public class MongoConfig {

    private final String dbname;
    private final String host;
    private final int port;
    private final int connectionsPerHost;
    private final int maxConnectionIdleTime;
    private final int maxConnectionLifeTime;

    public MongoConfig(String dbname, String host, int port, int connectionsPerHost, int maxConnectionIdleTime, int maxConnectionLifeTime) {
        this.dbname = dbname;
        this.host = host;
        this.port = port;
        this.connectionsPerHost = connectionsPerHost;
        this.maxConnectionIdleTime = maxConnectionIdleTime;
        this.maxConnectionLifeTime = maxConnectionLifeTime;
    }

    public String getDbname() {
        return dbname;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getConnectionsPerHost() {
        return connectionsPerHost;
    }

    public int getMaxConnectionIdleTime() {
        return maxConnectionIdleTime;
    }

    public int getMaxConnectionLifeTime() {
        return maxConnectionLifeTime;
    }

    @Override
    public String toString() {
        return "MongoConfig{" +
                "dbname='" + dbname + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", connectionsPerHost=" + connectionsPerHost +
                ", maxConnectionIdleTime=" + maxConnectionIdleTime +
                ", maxConnectionLifeTime=" + maxConnectionLifeTime +
                '}';
    }
}
