/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.database;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import texasholdem.database.collector.Collector;

/**
* A class for directly interacting with a database
* @author josujosu
*/
public class Database {

    private Connection connection;

    /**
     * Constructor
     * @param databaseAddress Address of the database
     */
    public Database(String databaseAddress) {
        try {
            this.connection = DriverManager.getConnection(databaseAddress);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * A method for retrieving elements from a database using a SQLite query
     * @param <T> The type of the elements
     * @param query SQLite query for acquiring elements from a database
     * @param col A Collector for extracting a type of Object from the query results
     * @param params Additional parameters that might be needed in a query
     * @return A list of elements
     * @throws SQLException 
     */
    public <T> List<T> queryAndCollect(final String query, final Collector<T>
            col, final Object...params) throws SQLException {
        List<T> lines = new ArrayList<>();
        PreparedStatement stmt = this.connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            lines.add(col.collect(rs));
        }

        rs.close();
        stmt.close();
        return lines;
    }
    
    /**
     * A method for updating a database using a SQLite query
     * @param updateQuery The query for the update
     * @param params Additional parameters that might be needed for the query
     * @return The number of changes made into the database as an integer
     * @throws SQLException 
     */
    public int update(String updateQuery, Object... params) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(updateQuery);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        int changes = stmt.executeUpdate();

        stmt.close();

        return changes;
    }
    
    
}
