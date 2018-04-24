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
    private int bet; // The amount of money the player has bet in the game
    
    public Player(User user, Deck hand) {
        this.user = user;
        this.hand = hand;
        this.bet = 0;
    }
    
    // Returns the amount of money that will be added to the pot
    public int call(int largestBet) {
        int calledMoney;
        int call = largestBet - this.bet;
        if (call > this.user.getBalance()) {
            calledMoney = this.user.getBalance();
            this.getUser().setBalance(0);
        } else {
            calledMoney = call;
            this.getUser().setBalance(this.getUser().getBalance() - call);
        }
        this.setBet(this.bet + calledMoney);
        return calledMoney;
    }
    
    // Returns the amount of money needed for a call + the bet
    public int raise(int raise, int largestBet) {
        int betMoney = this.call(largestBet);
        // If the player doesn't have money for the raise, they just call
        if (raise > this.user.getBalance()) {
            return betMoney;
        } else {
            this.setBet(this.bet + raise);
            this.getUser().setBalance(this.getUser().getBalance() - raise);
            return betMoney + raise;
        }
    }
    
    public Action play(int largestBet) {
        int additionalBet =  this.call(largestBet);
        return new Action(Action.ActionType.CALL, additionalBet, 0);
    }

    @Override
    public String toString() {
        return "User: " + this.user + ", " + "bet: " + this.bet + ", " + "Hand: " + this.hand;
    }

    /**
     * @return the hand
     */
    public Deck getHand() {
        return hand;
    }


    /**
     * @return the bet
     */
    public int getBet() {
        return bet;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param hand the hand to set
     */
    public void setHand(Deck hand) {
        this.hand = hand;
    }

    /**
     * @param bet the bet to set
     */
    public void setBet(int bet) {
        this.bet = bet;
    }
    
    
}
