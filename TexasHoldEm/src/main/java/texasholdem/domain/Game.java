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
import java.util.Iterator;
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
    private Action latest;
    private String lastActor;

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

    public HashMap<String, Player> createPlayers(User playerUser, int n, Deck deck) {
        HashMap<String, Player> newPlayers = new HashMap<>();
        Deck hand = new Deck(this.getDeck().takeCards(2));
        newPlayers.put("Player", new Player(playerUser, hand));
        for (int i = 1; i < n; i++) {
            hand = new Deck(this.getDeck().takeCards(2));
            newPlayers.put("CPU" + i, new Player(new User(i, "CPU" + i, 4000), hand));
        }
        return newPlayers;
    }

    public ArrayList<String> createPlayerOrder() {
        ArrayList<String> newPlayerOrder = new ArrayList<>();
        for (String player : this.players.keySet()) {
            newPlayerOrder.add(player);
        }
        Collections.shuffle(newPlayerOrder);
        return newPlayerOrder;
    }

    public void putCardsOnTable(int n) {
        this.getTable().addCards(this.getDeck().takeCards(n));
    }

    public String playBlinds() {
        this.pot += this.players.get(this.getPlayerOrder().get(this.getCurrentPlayer())).raise(this.smallBlind, 0);
        this.nextPlayer();
        this.pot += this.players.get(this.getPlayerOrder().get(this.getCurrentPlayer())).raise(this.bigBlind, 0);
        this.nextPlayer();
        this.setLargestBet(this.bigBlind);
        this.setLastRaiser(this.getCurrentPlayer());
        this.setCurrentPlayer(this.getCurrentPlayer());
        return this.getPlayerOrder().get(this.getCurrentPlayer());
    }

    public String playNext() {
        if (!this.foldedPlayers.contains(this.playerOrder.get(this.currentPlayer))) {
            Action action = this.players.get(this.getPlayerOrder().get(this.getCurrentPlayer())).play(largestBet, this.table);
            switch (action.getType()) {
                case CALL:
                    this.pot += action.getCall();
                    break;
                case RAISE:
                    this.pot += action.getCall() + action.getRaise();
                    this.setLastRaiser(this.getCurrentPlayer());
                    this.setLargestBet(this.largestBet + action.getRaise());
                    break;
                case FOLD:
                    this.getFoldedPlayers().add(this.getPlayerOrder().get(this.getCurrentPlayer()));
                    break;
            }
            this.latest = action;
            this.lastActor = this.getPlayerOrder().get(this.getCurrentPlayer());
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
                this.setLargestBet(this.largestBet + raise);
                this.setLastRaiser(this.getCurrentPlayer());
                break;
            case "Fold":
                this.getFoldedPlayers().add("Player");
                break;
            default:
                this.pot += this.players.get("Player").call(largestBet);
                break;
        }
    }

    public String playShowdown() {
        this.addTableCardsToPlayerDecks();
        ArrayList<Player> winners = this.getPlayersWithBestHand();
        int potFraction = this.getPot() / winners.size();
        for (Player player : winners) {
            player.getUser().addToBalance(potFraction);
        }
        this.updatePlayerDatabase();
        return null;
    }

    public void updatePlayerDatabase() {
        UserDao userDao = new UserDao(new Database("jdbc:sqlite:THE.db"));
        ResultDao resultDao = new ResultDao(new Database("jdbc:sqlite:THE.db"));
        User userInGame = this.players.get("Player").getUser();
        try {
            User humanUser = userDao.findOne(userInGame.getId());
            int winnings = userInGame.getBalance() - userDao.findOne(humanUser.getId()).getBalance();
            resultDao.save(new Result(1, humanUser, winnings));
            userDao.addToBalance(humanUser.getId(), winnings);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public ArrayList<Player> getPlayersWithBestHand() {
        HashMap<String, Deck> playerDecks = new HashMap<>();
        ArrayList<Player> bestPlayers = new ArrayList<>();
        for (String name : this.players.keySet()) {
            if (!this.hasFolded(name)) {
                playerDecks.put(name, this.players.get(name).getHand());
            }
        }
        HandComparator comp = new HandComparator();
        for (String name : comp.getBest(playerDecks)) {
            bestPlayers.add(this.players.get(name));
        }
        return bestPlayers;
    }
    
    public void addTableCardsToPlayerDecks() {
        for (String name : this.players.keySet()) {
            this.players.get(name).getHand().addCards(this.table.getCards());
        }
    }

    // Returns "GameState" ie. the label of the next player or "NextRound" for 
    // when the game should enter the next stage.
    public String returnGameState() {
        if (this.getFoldedPlayers().size() == this.getPlayerOrder().size() - 1) {
            return "EndGame";
        }
        if (this.getCurrentPlayer() == this.lastRaiser) {
            return "NextRound";
        } else {
            return this.getPlayerOrder().get(this.getCurrentPlayer());
        }
    }

    public void setCurrentPlayerToFirstNotFolded() {
        for (int i = 0; i < this.getPlayerOrder().size(); i++) {
            if (!this.foldedPlayers.contains(this.playerOrder.get(i))) {
                this.currentPlayer = i;
            }
        }
    }

    public String initializeForNextRound() {
        this.setCurrentPlayerToFirstNotFolded();
        this.lastRaiser = this.getCurrentPlayer(); //This way the game will go through the whole round
        if (this.getFoldedPlayers().size() == this.getPlayerOrder().size() - 1) {
            return "EndGame";
        } else {
            return this.getPlayerOrder().get(this.getCurrentPlayer());
        }
    }

    public void initializeNextDeal() {
        this.removePlayersWithNoMoney();
        this.getFoldedPlayers().clear();
        Collections.rotate(getPlayerOrder(), -1);
        this.pot = 0;
        this.setLargestBet(0);
        this.currentPlayer = 0;
        this.deck = new Deck();
        this.resetPlayers();
        this.table = new Deck(new ArrayList<>());
    }

    public void resetPlayers() {
        for (Player player : this.players.values()) {
            player.setHand(new Deck(this.getDeck().takeCards(2)));
            player.setBet(0);
        }
    }
    
    public void removePlayersWithNoMoney() {
        Iterator<String> iter = this.getPlayerOrder().iterator();
        while (iter.hasNext()) {
            String name = iter.next();
            if (this.players.get(name).getUser().getBalance() <= 0) {
                iter.remove();
                this.players.remove(name);
            }
        }
    }

    public void printPlayers() {
        for (String name : this.getPlayerOrder()) {
            System.out.println(this.players.get(name));
        }
    }
    
    public void printNonFoldedPlayers() {
        for (String name : this.getPlayerOrder()) {
            if (!this.foldedPlayers.contains(name)) {
                System.out.println(this.players.get(name));
            }
        } 
    }

    public void nextPlayer() {
        this.currentPlayer++;
        if (this.getCurrentPlayer() >= this.getPlayerOrder().size()) {
            this.setCurrentPlayer(0);
        }
        /*if (this.foldedPlayers.contains(this.playerOrder.get(this.currentPlayer))) {
            this.nextPlayer();
        }*/
    }

    public boolean hasFolded(String name) {
        return this.getFoldedPlayers().contains(name);
    }
    
    public boolean hasEnoughPlayersForPlaying() {
        return this.getPlayerOrder().size() > 1;
    }
    
    public boolean playerUserCanBeUsed(User user) {
        return user.getBalance() > 0;
    }

    public String getFirstPlayerName() {
        return this.getPlayerOrder().get(0);
    }

    public Player getHumanPlayer() {
        return this.players.get("Player");
    }

    public String getPotAndTableString() {
        return "Pot: " + this.getPot() + " Table: " + this.getTable();
    }
    
    public String currentPlayerName() {
        return this.getPlayerOrder().get(this.getCurrentPlayer());
    }
    
    public String playerInRelationToCurrent(int rel) {
        int i = this.getCurrentPlayer() + rel;
        if (i < 0) {
            i += this.getPlayerOrder().size();
        } else if (i >= this.getPlayerOrder().size()) {
            i -= this.getPlayerOrder().size();
        }
        return this.getPlayerOrder().get(i);
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

    /**
     * @return the latest
     */
    public Action getLatest() {
        return latest;
    }

    /**
     * @return the lastActor
     */
    public String getLastActor() {
        return lastActor;
    }

    /**
     * @return the playerOrder
     */
    public ArrayList<String> getPlayerOrder() {
        return playerOrder;
    }

    /**
     * @return the deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * @return the pot
     */
    public int getPot() {
        return pot;
    }

    /**
     * @return the currentPlayer
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return the foldedPlayers
     */
    public ArrayList<String> getFoldedPlayers() {
        return foldedPlayers;
    }

    /**
     * @param largestBet the largestBet to set
     */
    public void setLargestBet(int largestBet) {
        this.largestBet = largestBet;
    }
    
    

}
