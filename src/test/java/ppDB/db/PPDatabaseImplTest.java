package ppDB.db;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gustaf Nilstadius
 *         Created by Gustaf Nilstadius ( hipernx ) on 2017-02-01.
 */
@RunWith(VertxUnitRunner.class)
public class PPDatabaseImplTest {

    private AsyncSQLClient asyncSQLClient;
    private PPDatabase ppDatabase;
    private final Vertx vertx = Vertx.vertx();

    @Before
    public void setUp(TestContext context) throws Exception {
        asyncSQLClient = MySQLClient.createShared(vertx, getSQLSettings());
        ppDatabase = new PPDatabaseImpl();
    }

    @After
    public void tearDown(TestContext context) throws Exception {
        asyncSQLClient.close();
        vertx.close();
    }

    @Test
    public void getRandomWord(TestContext context) throws Exception {
        Async async = context.async();
        Future<JsonObject> future = Future.future();
        ppDatabase.getRandomWord(asyncSQLClient, future.setHandler(res -> {
            context.assertTrue(res.succeeded());
            async.complete();
        }));
        async.awaitSuccess(10000L);
    }

    private JsonObject getSQLSettings () {
        return new JsonObject()
                .put("host", "localhost")
                .put("port", 3306)
                .put("maxPoolSize", 10)
                .put("username", "ppdb")
                .put("password", "qwerty")
                .put("database", "pp_db");
    }


}