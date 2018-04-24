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
import texasholdem.database.Database;
import texasholdem.database.UserDao;
import texasholdem.database.ResultDao;

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
            newPlayers.put("CPU" + i, new Player(new User(i, "CPU" + i, 4000), hand));
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
        if (!this.foldedPlayers.contains(this.playerOrder.get(this.currentPlayer))) {
            Action action = this.players.get(this.playerOrder.get(this.currentPlayer)).play(largestBet);
            switch (action.getType()) {
                case CALL:
                    this.pot += action.getCall();
                    break;
                case RAISE:
                    this.pot += action.getCall() + action.getRaise();
                    this.setLastRaiser(this.currentPlayer);
                    this.largestBet += action.getRaise();
                    break;
                case FOLD:
                    this.foldedPlayers.add(this.playerOrder.get(this.currentPlayer));
                    break;
            }
        }
        this.nextPlayer();
        return this.returnGameState();
    }

    public String playHuman(String command, int raise) {
        if (!this.foldedPlayers.contains("Player")) {
            this.actPlayersCommand(command, raise);
        }
        this.nextPlayer();
        return this.returnGameState();
    }

    // An unnecessary method that could be included clearly in "play human", but
    // is it's own method because otherwise "play human" would've been a line
    // too long for checkstyle
    public void actPlayersCommand(String command, int raise) {
        switch (command) {
            case "Call":
                this.pot += this.players.get("Player").call(largestBet);
                break;
            case "Raise":
                this.pot += this.players.get("Player").raise(raise, largestBet);
                this.largestBet += raise;
                this.setLastRaiser(this.currentPlayer);
                break;
            case "Fold":
                this.foldedPlayers.add("Player");
                break;
            default:
                this.pot += this.players.get("Player").call(largestBet);
                break;
        }
    }

    public String playShowdown() {
        ArrayList<Player> winners = this.getPlayersWithBestHand();
        int potFraction = this.pot / winners.size();
        for (Player player : winners) {
            player.getUser().addToBalance(potFraction);
        }
        this.updatePlayerDatabase();
        return null;
    }

    public void updatePlayerDatabase() {
        Player humanPlayer = this.players.get("Player");
        UserDao userDao = new UserDao(new Database("jdbc:sqlite:THE.db"));
        ResultDao resultDao = new ResultDao(new Database("jdbc:sqlite:THE.db"));
        try {
            int winnings = humanPlayer.getUser().getBalance() - userDao.findOne(humanPlayer.getUser().getId()).getBalance();
            resultDao.save(new Result(1, humanPlayer.getUser(), winnings));
            userDao.addToBalance(humanPlayer.getUser().getId(), winnings);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public ArrayList<Player> getPlayersWithBestHand() {
        HashMap<String, Deck> playerDecks = new HashMap<>();
        ArrayList<Player> bestPlayers = new ArrayList<>();
        for (String name : this.players.keySet()) {
            this.players.get(name).getHand().addCards(this.table.getCards());
            if (!this.foldedPlayers.contains(name)) {
                playerDecks.put(name, this.players.get(name).getHand());
            }
        }
        HandComparator comp = new HandComparator();
        for (String name : comp.getBest(playerDecks)) {
            bestPlayers.add(this.players.get(name));
        }
        return bestPlayers;
    }

    // Returns "GameState" ie. the label of the next player or "NextRound" for 
    // when the game should enter the next stage.
    public String returnGameState() {
        if (this.foldedPlayers.size() == this.playerOrder.size() - 1) {
            return "EndGame";
        }
        if (this.currentPlayer == this.lastRaiser) {
            return "NextRound";
        } else {
            return this.playerOrder.get(this.currentPlayer);
        }
    }

    public void setCurrentPlayerToFirstNotFolded() {
        for (int i = 0; i < this.playerOrder.size(); i++) {
            if (!this.foldedPlayers.contains(this.playerOrder.get(i))) {
                this.currentPlayer = i;
            }
        }
    }

    public String initializeForNextRound() {
        this.setCurrentPlayerToFirstNotFolded();
        this.lastRaiser = this.currentPlayer; //This way the game will go through the whole round
        if (this.foldedPlayers.size() == this.playerOrder.size() - 1) {
            return "EndGame";
        } else {
            return this.playerOrder.get(this.currentPlayer);
        }
    }

    public void initializeNextDeal() {
        this.foldedPlayers.clear();
        Collections.rotate(playerOrder, -1);
        this.pot = 0;
        this.largestBet = 0;
        this.deck = new Deck();
        this.resetPlayers();
        this.table = new Deck(new ArrayList<>());
    }

    public void resetPlayers() {
        for (Player player : this.players.values()) {
            player.setHand(new Deck(this.deck.takeCards(2)));
            player.setBet(0);
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
        /*if (this.foldedPlayers.contains(this.playerOrder.get(this.currentPlayer))) {
            this.nextPlayer();
        }*/
    }

    public boolean playerHasFolded() {
        return this.foldedPlayers.contains("Player");
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
