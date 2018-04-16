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
public class Player {
    
    private User user;
    private Deck hand;
    private int bet;
    
    public Player(User user, Deck hand) {
        this.user = user;
        this.hand = hand;
        this.bet = 0;
    }
    
    // Returns the amount of money that will be added to the pot
    public int call(int call) {
        int calledMoney;
        if(call > this.user.getBalance()){
            calledMoney = this.user.getBalance();
            this.getUser().setBalance(0);
        } else {
            calledMoney = call;
            this.getUser().setBalance(this.getUser().getBalance() - call);
        }
        return calledMoney;
    }
    
    // Returns the amount of money needed for a call + the bet
    public int raise(int raise, int call) {
        int betMoney = this.call(call);
        if(raise > this.user.getBalance()) {
            return betMoney;
        } else {
            this.bet += raise;
            this.getUser().setBalance(this.getUser().getBalance() - call);
            return betMoney + raise;
        }
    }

    @Override
    public String toString() {
        return "User: " + this.user + "\n" + "bet: " + this.bet + "\n" + "Hand: " + this.hand;
    }

    /**
     * @return the hand
     */
    public Deck getHand() {
        return hand;
    }


    /**
     * @return the moneyBet
     */
    public int getMoneyBet() {
        return bet;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }
    
}
