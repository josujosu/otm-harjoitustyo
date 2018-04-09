/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.database.collector;

/**
 *
 * @author josujosu
 */
import texasholdem.domain.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCollector implements Collector<User>{
    
    @Override
    public User collect(ResultSet rs) throws SQLException{
        return new User(rs.getInt("id"), rs.getString("username"), rs.getInt("balance"));
    }
    
}
