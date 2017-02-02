package ppDB.db;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;

/**
 * @author Gustaf Nilstadius
 *         Created by Gustaf Nilstadius ( hipernx ) on 2017-02-01.
 */
public interface PPDatabase {
    void getRandomWord(AsyncSQLClient asyncSQLClient, Future<JsonObject> fut);
}
