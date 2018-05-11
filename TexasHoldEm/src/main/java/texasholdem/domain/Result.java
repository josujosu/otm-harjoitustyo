/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

/**
 * A class depicting the result of a Texas Hold'Em deal
 * @author josujosu
 */
public class Result {
    
    private int id;
    private int userId;
    private int oldBalance;
    private int balanceChange;
    
    /**
     * Constructor
     * @param id The id of the result
     * @param user The user whose result the Result in question is
     * @param balanceChange The amount the balance of the user has changed
     */
    public Result(int id, User user, int balanceChange) {
        this.id = id;
        this.userId = user.getId();
        this.oldBalance = user.getBalance();
        this.balanceChange = balanceChange;
    }
    
    /**
     * Constructor
     * @param id The id of the result
     * @param userId The id of the user whose result the Result in question is
     * @param oldBalance The balance of the user whose result the Result in question is
     * before the change depicted in the result
     * @param balanceChange The amount the balance of the user has changed 
     */
    public Result(int id, int userId, int oldBalance, int balanceChange) {
        this.id = id;
        this.userId = userId;
        this.oldBalance = oldBalance;
        this.balanceChange = balanceChange;
    }
    
    @Override
    public String toString() {
        return this.id + " " + this.userId + " " + this.oldBalance + " " + this.balanceChange;
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
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the balanceChange
     */
    public int getBalanceChange() {
        return balanceChange;
    }

    /**
     * @param balanceChange the balanceChange to set
     */
    public void setBalanceChange(int balanceChange) {
        this.balanceChange = balanceChange;
    }

    /**
     * @return the oldBalance
     */
    public int getOldBalance() {
        return oldBalance;
    }

    /**
     * @param oldBalance the oldBalance to set
     */
    public void setOldBalance(int oldBalance) {
        this.oldBalance = oldBalance;
    }

    
}
