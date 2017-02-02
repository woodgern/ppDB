package ppDB.db;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.sql.SQLConnection;

/**
 * @author Gustaf Nilstadius
 *         Created by Gustaf Nilstadius ( hipernx ) on 2017-02-01.
 */
public class PPDatabaseImpl implements PPDatabase {
    @Override
    public void getRandomWord(AsyncSQLClient asyncSQLClient, Future<JsonObject> fut) {
        asyncSQLClient.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection conn = res.result();
                conn.query(selectRandomPP, result-> {
                   if (result.succeeded()){
                       JsonObject sqlResult = result.result().toJson();
                       fut.complete(sqlResult.getJsonArray("rows").getJsonObject(0));
                   } else {
                       fut.fail(result.cause());
                   }
                   if (conn != null) conn.close();
                });
            } else {
                fut.fail(res.cause());
            }
        });
    }


    private final String selectRandomPP = "SELECT * FROM pp ORDER BY RAND() LIMIT 1;";
}
