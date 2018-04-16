/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.database;

import texasholdem.domain.User;
import texasholdem.database.collector.UserCollector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author josujosu
 */
public class UserDao implements Dao<User, Integer> {

    private Database db;

    public UserDao(Database db) {
        this.db = db;
    }

    @Override
    public User findOne(Integer key) throws SQLException {
        List<User> User = this.db.queryAndCollect("SELECT * FROM User WHERE id = ?", new UserCollector(), key);
        if (User.isEmpty()) {
            return null;
        } else {
            return User.get(0);
        }
    }

    @Override
    public void save(User user) throws SQLException {
        this.db.update("INSERT INTO User (username, balance) VALUES (?, ?)", user.getUsername(), user.getBalance());
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> Users = this.db.queryAndCollect("SELECT * FROM User", new UserCollector());
        return Users;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        this.db.update("DELETE FROM User WHERE id = ?", key);
    }

}
