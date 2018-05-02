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
import java.util.ArrayList;

/**
 * A class depicting one of the users the player has made, or that has been
 * made for the use of an AI player
 * @author josujosu
 */
public class User {

    private int id;
    private String username;
    private int balance;
    private ResultDao dao;

    public User(int id, String username, int balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
        this.dao = new ResultDao(new Database("jdbc:sqlite:THE.db"));
    }

    /**
     * Adds to the balance associated with a User
     * @param amount the amount that will be added to the balance
     */
    public void addToBalance(int amount) {
        this.balance += amount;
    }

    /**
     * A method for calculating the win/lose ratio associated with a user.
     * Uses the "getResultsOfUser()" method to get a list of all of the users
     * results, checks sign of the balance changes and divides the amount of positive
     * changes with the amount of negative changes.
     * @return the calculated win lose ratio
     */
    public float getWinLoseRatio() {
        int wins = 0;
        int losses = 0;
        for (Result result : this.getResultsOfUser()) {
            if (result.getBalanceChange() > 0) {
                wins++;
            } else if (result.getBalanceChange() < 0) {
                losses++;
            }
        }
        float ratio = (float) wins / (float) losses;
        return ratio;
    }
    
    /**
     * A method for extracting a list of older balances connected to the user in question
     * from the appointed database.
     * @return A List containing all of the balance history
     */
    public List<Integer> getBalanceHistory() {
        List<Integer> balances = new ArrayList<>();
        for (Result result : this.getResultsOfUser()) {
            balances.add(result.getOldBalance());
        }
        balances.add(this.balance);
        return balances;
    }
    
    /**
     * A method for extracting a list of changes to the balance of the user in
     * question from the appointed database.
     * @return A List containing all of the changes to the balance
     */
    public List<Integer> getWinsAndLosses() {
        List<Integer> winnings = new ArrayList<>();
        for (Result result : this.getResultsOfUser()) {
            winnings.add(result.getBalanceChange());
        }
        return winnings;
    }
    
    /**
     * A method for extracting all of the results connected to the user in question
     * as a List of Result objects from the appointed database.
     * @return A List containing all of the results
     */
    public List<Result> getResultsOfUser() {
        List<Result> results = new ArrayList<>();
        try {
            results = this.dao.findAllWithSameUserId(this.id);
        } catch (Exception e) {
            System.out.println(e);
        } 
        return results;
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