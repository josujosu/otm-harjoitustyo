/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

import java.sql.SQLException;
import texasholdem.database.ResultDao;
import texasholdem.database.Database;
import java.util.List;

/**
 *
 * @author josujosu
 */
public class User {

    private int id;
    private String username;
    private int balance;

    public User(int id, String username, int balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
    }

    public void addToBalance(int amount) {
        this.balance += amount;
    }

    public float getWinLoseRatio() {
        int wins = 0;
        int losses = 0;
        try {
            List<Result> results = new ResultDao(new Database("jdbc:sqlite:THE.db")).findAllWithSameUserId(this.id);
            for (Result result : results) {
                if (result.getBalanceChange() > 0) {
                    wins++;
                } else if (result.getBalanceChange() < 0) {
                    losses++;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        float ratio = (float) wins / (float) losses;
        return ratio;
    }

    @Override
    public String toString() {
        return "id: " + this.id + ", username: " + this.username + ", balance: " + this.balance;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

}
