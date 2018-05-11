/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

/**
 * A class that depicts a player playing Texas Hold'Em
 * @author josujosu
 */
public class Player {
    
    private User user;
    private Deck hand;
    private int bet; // The amount of money the player has bet in the game
    
    /**
     * Constructor
     * @param user The user that the Player will use
     * @param hand The cards that the has in their hands as a Deck object
     */
    public Player(User user, Deck hand) {
        this.user = user;
        this.hand = hand;
        this.bet = 0;
    }
    
    /**
     * A method that makes the Player make a call action
     * @param largestBet The largest bet that has been made
     * @return The amount of money the player has to use for calling
     */
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
    
    /**
     * A method that makes the Player make a raise action
     * @param raise The amount of money that the player raises
     * @param largestBet The largest bet that has been made
     * @return The raise amount + the amount of money needed for a call
     */
    public int raise(int raise, int largestBet) {
        int betMoney = this.call(largestBet);
        // If the player doesn't have money for the raise, they go all in
        if (raise > this.user.getBalance()) {
            raise = this.user.getBalance();
        }
        this.setBet(this.bet + raise);
        this.getUser().setBalance(this.getUser().getBalance() - raise);
        return betMoney + raise;
    }
    
    /**
     * A method used by AI players to make actions
     * @param largestBet The largest bet that has been made
     * @param table The cards that are currently on the table
     * @return The action made by the AI player as an Action object
     */
    public Action play(int largestBet, Deck table) {
        Deck currentCards = new Deck(this.hand.getCards());
        currentCards.addCards(table.getCards());
        Action action = new ComputerAI().makeAction(currentCards, largestBet);
        this.act(action);
        return action;
    }
    
    /**
     * A method that makes the Player make an Action
     * @param a The Action that will be made
     */
    public void act(Action a) {
        switch (a.getType()) {
            case CALL:
                this.call(a.getCall());
                break;
            case RAISE:
                this.raise(a.getRaise(), a.getCall());
                break;
        }
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
