package ppDB.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.LoggerHandler;
import ppDB.db.PPDatabase;
import ppDB.db.PPDatabaseImpl;

/**
 * @author Gustaf Nilstadius
 *         Created by Gustaf Nilstadius ( hipernx ) on 2017-02-01.
 */
public class Server extends AbstractVerticle {

    private AsyncSQLClient asyncSQLClient;
    private PPDatabase ppDatabase;
    public void start(Future<Void> fut) {
        Router router = Router.router(vertx);
        /*
            Init logger
        */
        router.route("/*").handler(LoggerHandler.create());

        /*
            Init routes
         */
        router.get("/pp").handler(this::getPPHandler);


        /*
            Init Async DB
         */
        asyncSQLClient = MySQLClient.createShared(vertx, getPPSQLOptions());
        ppDatabase = new PPDatabaseImpl();

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(getHttpPort(), result -> {
                    if(result.succeeded()) {
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });
    }

    private void getPPHandler(RoutingContext routingContext){
        Future<JsonObject> future = Future.future();
        ppDatabase.getRandomWord(asyncSQLClient, future.setHandler(res -> {
            if (res.succeeded()){
                routingContext.response()
                        .setStatusCode(200)
                        .setStatusMessage("ok")
                        .putHeader("Content-Type", "application/json")
                        .end(res.result().encode());
            } else {
                res.cause().printStackTrace();
                sendServerError(routingContext);
            }
        }));
    }

    private void sendServerError(RoutingContext routingContext){
        routingContext.response()
                .setStatusCode(500)
                .setStatusMessage("Internal server error")
                .end("Internal server error");
    }

//#######CONFIG####################################################################################

    /**
     * @return Http port from JVM properties
     */
    private Integer getHttpPort(){
        if (System.getProperty("HTTP_SERVER_PORT") != null){
            return new Integer(System.getProperty("HTTP_SERVER_PORT"));
        } else if (System.getenv("HTTP_SERVER_PORT") != null) {
            return new Integer(System.getenv("HTTP_SERVER_PORT"));
        } else {
            return 3000;
        }
    }

    /**
     * @return Async MySQL options from JVM properties
     */
    private JsonObject getPPSQLOptions() {
        if (System.getProperty("DB_HOST") != null && System.getProperty("DB_PORT") != null &&
                System.getProperty("DB_MAXPOOLSIZE") != null && System.getProperty("DB_USERNAME") != null &&
                System.getProperty("DB_PASSWORD") != null && System.getProperty("DB_DATABASE") != null) {

            return new JsonObject()
                    .put("host", System.getProperty("DB_HOST"))
                    .put("port", Integer.parseInt(System.getProperty("DB_PORT")))
                    .put("maxPoolSize", Integer.parseInt(System.getProperty("DB_MAXPOOLSIZE")))
                    .put("username", System.getProperty("DB_USERNAME"))
                    .put("password", System.getProperty("DB_PASSWORD"))
                    .put("database", System.getProperty("DB_DATABASE"));
        } else if (System.getenv("DB_HOST") != null && System.getenv("DB_PORT") != null &&
                System.getenv("DB_MAXPOOLSIZE") != null && System.getenv("DB_USERNAME") != null &&
                System.getenv("DB_PASSWORD") != null && System.getenv("DB_DATABASE") != null) {

            return new JsonObject()
                    .put("host", System.getenv("DB_HOST"))
                    .put("port", Integer.parseInt(System.getenv("DB_PORT")))
                    .put("maxPoolSize", Integer.parseInt(System.getenv("DB_MAXPOOLSIZE")))
                    .put("username", System.getenv("DB_USERNAME"))
                    .put("password", System.getenv("DB_PASSWORD"))
                    .put("database", System.getenv("DB_DATABASE"));

        } else {
            return new JsonObject()
                    .put("host", "localhost")
                    .put("port", 3306)
                    .put("maxPoolSize", 10)
                    .put("username", "ppdb")
                    .put("password", "qwerty")
                    .put("database", "pp_db");
        }

    }
}
