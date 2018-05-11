/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.database;

import texasholdem.domain.User;
import texasholdem.database.collector.UserCollector;


import java.sql.SQLException;
import java.util.List;

/**
 * A class for managing objects of the type "User" in a database
 * @author josujosu
 */
public class UserDao implements Dao<User, Integer> {

    private Database db;

    /**
     * Constructor
     * @param db  The database which will be managed
     */
    public UserDao(Database db) {
        this.db = db;
        this.init();
    }

    @Override
    public User findOne(Integer key) throws SQLException {
        List<User> user = this.db.queryAndCollect("SELECT * FROM User WHERE id = ?", new UserCollector(), key);
        if (user.isEmpty()) {
            return null;
        } else {
            return user.get(0);
        }
    }

    @Override
    public void save(User user) throws SQLException {
        this.db.update("INSERT INTO User (username, balance) VALUES (?, ?)", user.getUsername(), user.getBalance());
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> users = this.db.queryAndCollect("SELECT * FROM User", new UserCollector());
        return users;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        this.db.update("DELETE FROM User WHERE id = ?", key);
    }
    
    @Override
    public void init() {
        try {
            this.db.update("CREATE TABLE IF NOT EXISTS User (id integer PRIMARY KEY, username varchar(60), balance integer)");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * A method for adding to the balance parameter of a User in the database
     * @param key The id of the user
     * @param amount The amount that will be added
     * @throws SQLException 
     */
    public void addToBalance(Integer key, Integer amount) throws SQLException {
        this.db.update("UPDATE User SET balance = balance + ? WHERE id =  ?", amount, key);
    }

}
