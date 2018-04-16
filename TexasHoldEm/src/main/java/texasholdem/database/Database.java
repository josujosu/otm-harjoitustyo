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
*
* @author josujosu
*/
public class Database {

    private Connection connection;

    public Database(String databaseAddress) {
        try {
            this.connection = DriverManager.getConnection(databaseAddress);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

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
