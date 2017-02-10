package ppDB.server;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * @author Gustaf Nilstadius
 *         Created by Gustaf Nilstadius ( hipernx ) on 2017-02-02.
 */
public class Starter {
    public static void main(String[] args){
        Runtime runtime = Runtime.getRuntime();
        int cores = runtime.availableProcessors();

        VertxOptions vertxOptions = new VertxOptions()
                .setHAEnabled(false)
                .setWorkerPoolSize(cores*4);
        Vertx vertx = Vertx.vertx(vertxOptions);

        DeploymentOptions options = new DeploymentOptions()
                .setInstances(cores*2)
                .setHa(true);
        vertx.deployVerticle("ppDB.server.Server", options, res -> {
            if (res.succeeded()) {
                System.out.println("Vertices deployed successfully");
            } else {
                System.err.println("Error deploying vertices, closing Vert.X");
                res.cause().printStackTrace();
            }

        });
    }
}
