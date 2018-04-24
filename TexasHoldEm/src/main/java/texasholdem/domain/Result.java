/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

/**
 *
 * @author josujosu
 */
public class Result {
    
    private int id;
    private int userId;
    private int oldBalance;
    private int balanceChange;
    
    public Result(int id, User user, int balanceChange) {
        this.id = id;
        this.userId = user.getId();
        this.oldBalance = user.getBalance();
        this.balanceChange = balanceChange;
    }
    
    public Result(int id, int userId, int oldBalance, int balanceChange) {
        this.id = id;
        this.userId = userId;
        this.oldBalance = oldBalance;
        this.balanceChange = balanceChange;
    }
    
    public void addResult(User user, int amount) {
        
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
