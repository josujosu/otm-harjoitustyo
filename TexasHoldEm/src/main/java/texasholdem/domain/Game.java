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
    private int lastBetter;
    private Deck deck;
    private Deck table;
    private int bet;
    private int pot;
    private final int bigBlind = 10;
    private final int smallBlind = 5;
    
    public Game(User playerUser, int numberOfPlayers) {
        this.bet = 0;
        this.pot = 0;
        this.currentPlayer = 0;
        this.lastBetter = 0;
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
        for(int i = 1; i < n; i++) {
            hand = new Deck(this.deck.takeCards(2));
            newPlayers.put("CPU" + i, new Player(new User(i, "CPU", 4000), hand));
        }
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
        this.table.addCards(this.deck.takeCards(n));
    }
    
    public void playPreFlopRound() {
        this.pot += this.players.get(this.playerOrder.get(0)).raise(this.smallBlind, 0);
        this.pot += this.players.get(this.playerOrder.get(1)).raise(this.bigBlind, 0);
        this.currentPlayer = 2;
    }
    
    public void printPlayers() {
        for (String name : this.players.keySet()) {
            System.out.println(this.players.get(name));
        }
    }
}
