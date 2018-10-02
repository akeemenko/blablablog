package blablablog;

import blablablog.entity.User;
import blablablog.mongo.MongoConfig;
import blablablog.mongo.MongoConnection;
import blablablog.entity.Employee;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.context.ApplicationContext;
import org.junit.Assert;


import java.util.List;

/**
 * Created by UNKNOWN on 22.01.2018.
 */
public class BlablablogService {
    private static final Logger log = Logger.getLogger(BlablablogService.class.getName());
    private static BlablablogService instance;
    private final Datastore datastore;


    public BlablablogService(ApplicationContext applicationContext) {
        MongoConnection conn = MongoConnection.getInstance();
        conn.init((MongoConfig) applicationContext.getBean("mongoConfiguration"));
        this.datastore = conn.getDataStore();

        firstTest();
    }

    /**
     * Init mp auth service
     *
     * @param applicationContext application context
     * @return instance of bcdb api service
     */
    public static BlablablogService init(ApplicationContext applicationContext) {
        if (instance != null) {
            log.fatal("Unable to initialize new instance of Blablablog service");
            return instance;
        } else {
            return new BlablablogService(applicationContext);
        }
    }


    public void firstTest() {


        final User user = new User("admin@admin.com", "admin", "12345", "John Doe");
        datastore.save(user);





        final Employee elmer = new Employee("Elmer Fudd", 50000.0);
        datastore.save(elmer);

        final Employee daffy = new Employee("Daffy Duck", 40000.0);
        datastore.save(daffy);

        final Employee pepe = new Employee("Pepé Le Pew", 25000.0);
        datastore.save(pepe);

        elmer.getDirectReports().add(daffy);
        elmer.getDirectReports().add(pepe);

        datastore.save(elmer);

        Query<Employee> query = datastore.find(Employee.class);
        final List<Employee> employees = query.asList();

        Assert.assertEquals(3, employees.size());

        List<Employee> underpaid = datastore.find(Employee.class)
                .filter("salary <=", 30000)
                .asList();
        Assert.assertEquals(1, underpaid.size());

        underpaid = datastore.find(Employee.class)
                .field("salary").lessThanOrEq(30000)
                .asList();
        Assert.assertEquals(1, underpaid.size());

        final Query<Employee> underPaidQuery = datastore
                .find(Employee.class)
                .filter("salary <=", 30000);
        final UpdateOperations<Employee> updateOperations = datastore.createUpdateOperations(Employee.class)
                .inc("salary", 10000);

        final UpdateResults results = datastore.update(underPaidQuery, updateOperations);

        Assert.assertEquals(1, results.getUpdatedCount());

        final Query<Employee> overPaidQuery = datastore
                .find(Employee.class)
                .filter("salary >", 100000);
        datastore.delete(overPaidQuery);

    }

}