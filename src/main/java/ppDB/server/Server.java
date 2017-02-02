package ppDB.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * @author Gustaf Nilstadius
 *         Created by Gustaf Nilstadius ( hipernx ) on 2017-02-01.
 */
public class Server extends AbstractVerticle {
    public void start(Future<Void> fut) {
        Router router = Router.router(vertx);

        router.get("/pp").handler(this::getPPHandler);

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

    }

    private Integer getHttpPort(){
        if (System.getProperty("HTTP_SERVER_PORT") != null){
            return new Integer(System.getProperty("HTTP_SERVER_PORT"));
        } else if (System.getenv("HTTP_SERVER_PORT") != null) {
            return new Integer(System.getenv("HTTP_SERVER_PORT"));
        } else {
            return 3000;
        }
    }
}
