/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.database.collector;

/**
 * A class for interpreting query results as objects
 * @author josujosu
 */
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Collector<T> {
    /**
     * A method that interprets a query result as an object
     * @param rs The result of the query
     * @return The interpreted object
     * @throws SQLException 
     */
    T collect(ResultSet rs) throws SQLException;
}
