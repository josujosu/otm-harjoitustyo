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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class Game {
    
    private HashMap<String, Player> players;
    private ArrayList<String> foldedPlayers;
    private ArrayList<String> playerOrder;
    private int currentPlayer;
    private int lastRaiser;
    private Deck deck;
    private Deck table;
    private int largestBet;
    private int pot;
    private final int bigBlind = 10;
    private final int smallBlind = 5;
    
    public Game(User playerUser, int numberOfPlayers) {
        this.largestBet = 0;
        this.pot = 0;
        this.currentPlayer = 0;
        this.lastRaiser = 0;
        this.deck = new Deck();
        this.table = new Deck(new ArrayList<>());
        this.foldedPlayers = new ArrayList<>();
        this.players = this.createPlayers(playerUser, numberOfPlayers, deck);
        this.playerOrder = this.createPlayerOrder();
    }
    
    final HashMap<String, Player> createPlayers(User playerUser, int n, Deck deck) {
        HashMap<String, Player> newPlayers = new HashMap<>();
        Deck hand = new Deck(this.deck.takeCards(2));
        newPlayers.put("Player", new Player(playerUser, hand));
        for (int i = 1; i < n; i++) {
            hand = new Deck(this.deck.takeCards(2));
            newPlayers.put("CPU" + i, new Player(new User(i, "CPU", 4000), hand));
        }
        System.out.println(newPlayers);
        return newPlayers;
    }
    
    final ArrayList<String> createPlayerOrder() {
        ArrayList<String> newPlayerOrder = new ArrayList<>();
        for (String player : this.players.keySet()) {
            newPlayerOrder.add(player);
        }
        Collections.shuffle(newPlayerOrder);
        return newPlayerOrder;
    }
    
    public void putCardsOnTable(int n) {
        this.getTable().addCards(this.deck.takeCards(n));
    }
    
    public String playBlinds() {
        this.pot += this.players.get(this.playerOrder.get(0)).raise(this.smallBlind, 0);
        this.pot += this.players.get(this.playerOrder.get(1)).raise(this.bigBlind, 0);
        this.largestBet = this.bigBlind;
        this.setLastRaiser(2);
        this.setCurrentPlayer(2);
        return this.playerOrder.get(this.currentPlayer);
    }
    
    public String playNext() {
        Action action = this.players.get(this.playerOrder.get(this.currentPlayer)).play(largestBet);
        if (action.getType() == Action.ActionType.CALL) {
            this.pot += action.getCall();
        } else if (action.getType() == Action.ActionType.RAISE) {
            this.pot += action.getCall() + action.getRaise();
            this.setLastRaiser(this.currentPlayer);
            this.largestBet += action.getRaise();
        }
        this.nextPlayer();
        if (this.currentPlayer == this.lastRaiser) {
            return "NextRound";
        } else {
            return this.playerOrder.get(this.currentPlayer);
        }   
    }
    
    public String playHuman(String command, int raise) {
               
        if (command.equals("Call")) {
            this.pot += this.players.get("Player").call(largestBet);
        } else if (command.equals("Raise")) {
            this.pot += this.players.get("Player").raise(raise, largestBet);
            this.largestBet += raise;
            this.setLastRaiser(this.currentPlayer);
        }
        this.nextPlayer();
        if (this.currentPlayer == this.lastRaiser) {
            return "NextRound";
        } else {
            return this.playerOrder.get(this.currentPlayer);
        }        
    }
    
    
    
    public void printPlayers() {
        for (String name : this.playerOrder) {
            System.out.println(this.players.get(name));
        }
    }
    
    public void nextPlayer() {
        this.currentPlayer++;
        if (this.currentPlayer >= this.playerOrder.size()) {
            this.setCurrentPlayer(0);
        }
    }
    
    public String getFirstPlayerName() {
        return this.playerOrder.get(0);
    }
    
    public Player getHumanPlayer() {
        return this.players.get("Player");
    }
    
    public String getPotAndTableString() {
        return "Pot: " + this.pot + " Table: " + this.getTable();
    }
    
    @Override
    public String toString() {
        return null;
    }

    /**
     * @return the table
     */
    public Deck getTable() {
        return table;
    }

    /**
     * @param currentPlayer the currentPlayer to set
     */
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * @param lastRaiser the lastRaiser to set
     */
    public void setLastRaiser(int lastRaiser) {
        this.lastRaiser = lastRaiser;
    }
    
    
}
