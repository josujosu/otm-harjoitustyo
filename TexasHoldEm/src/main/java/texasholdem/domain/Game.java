/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

/**
 * A class that encapsulates a game of Texas Hold'Em from the players to the rules
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

    /**
     * Constructor
     * @param playerUser The User the player playing the game has picked
     * @param numberOfPlayers Number of players that will participate in the game. Note
     * that only enough players for a 52 cards can be used.
     */
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

    /**
     * A method for creating all of the Player objects in the given game
     * @param playerUser The User the player has picked to play as
     * @param n Number of players that the game will have
     * @param deck A deck containing all of the cards that will be used in the game
     * @return A HashMap containing a String as a key and the created Player objects 
     * as values
     */
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

    /**
     * A method for creating an ArrayList containing all of the player names in 
     * the order that they will be playing.
     * @return The player name ArrayList
     */
    public ArrayList<String> createPlayerOrder() {
        ArrayList<String> newPlayerOrder = new ArrayList<>();
        for (String player : this.players.keySet()) {
            newPlayerOrder.add(player);
        }
        Collections.shuffle(newPlayerOrder);
        return newPlayerOrder;
    }

    /**
     * A method for taking cards from the main Deck and putting them on the table Deck
     * @param n The number of the cards that will be put on the table
     */
    public void putCardsOnTable(int n) {
        this.getTable().addCards(this.getDeck().takeCards(n));
    }

    /**
     * A method for playing the "blinds" 
     * @return The player name of the next player to play
     */
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

    /**
     * A method that makes the AI player next in turn to play
     * @return The return value of the returnGameState() method
     */
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

    /**
     * A method that decides if the player will make an action
     * @param command The action the player will make
     * @param raise The amount of money the player wants to raise (can be 0)
     * @return The return value of the returnGameState() method
     */
    public String playHuman(String command, int raise) {
        if (!this.foldedPlayers.contains("Player")) {
            this.actPlayersCommand(command, raise);
        }
        this.nextPlayer();
        return this.returnGameState();
    }

    /**
     * A method that makes the player make an action
     * @param command The action that the player will make
     * @param raise The amount of money the player wants to raise (can be 0)
     */
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

    /**
     * A method that makes the game go through the "showdown" phase
     * @return null
     */
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

    /**
     * A method for updating the database after the game has ended
     */
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

    /**
     * A method for acquiring the players that have the best hands in the game
     * @return A List of the best players
     */
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
    
    /**
     * Adds the cards of the table Deck to the hands of the payers, so the type
     * of PokerHand they have can be inferred
     */
    public void addTableCardsToPlayerDecks() {
        for (String name : this.players.keySet()) {
            this.players.get(name).getHand().addCards(this.table.getCards());
        }
    }

    /**
     * A method for acquiring the next step that the game should make
     * @return "EndGame" if the game should end, "NextRound" if the game should
     * start the next round. Otherwise the name of the next player.
     */
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

    /**
     * Makes the current player the first in the player order that has not folded
     */
    public void setCurrentPlayerToFirstNotFolded() {
        for (int i = 0; i < this.getPlayerOrder().size(); i++) {
            if (!this.foldedPlayers.contains(this.playerOrder.get(i))) {
                this.setCurrentPlayer(i);
            }
        }
    }

    /**
     * Initialises the game for the next round
     * @return "EndGame" if there's only one player that has not folded, otherwise the 
     * name of the next player
     */
    public String initializeForNextRound() {
        this.setCurrentPlayerToFirstNotFolded();
        this.lastRaiser = this.getCurrentPlayer(); //This way the game will go through the whole round
        if (this.getFoldedPlayers().size() == this.getPlayerOrder().size() - 1) {
            return "EndGame";
        } else {
            return this.getPlayerOrder().get(this.getCurrentPlayer());
        }
    }

    /**
     * A method for initialising the game for the next round.
     */
    public void initializeNextDeal() {
        this.removePlayersWithNoMoney();
        this.getFoldedPlayers().clear();
        Collections.rotate(getPlayerOrder(), -1);
        this.pot = 0;
        this.setLargestBet(0);
        this.setCurrentPlayer(0);
        this.deck = new Deck();
        this.resetPlayers();
        this.table = new Deck(new ArrayList<>());
    }

    /**
     * A method that resets all of the Player-objects so that they have new cards
     * in their hands and so that their bets are 0
     */
    public void resetPlayers() {
        for (Player player : this.players.values()) {
            player.setHand(new Deck(this.getDeck().takeCards(2)));
            player.setBet(0);
        }
    }
    
    /**
     * Removes players that have no money from the game
     */
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

    /**
     * Prints all of the players into the terminal window.
     */
    public void printPlayers() {
        for (String name : this.getPlayerOrder()) {
            System.out.println(this.players.get(name));
        }
    }
    
    /**
     * Prints all of the players that have not folded into the terminal window.
     */
    public void printNonFoldedPlayers() {
        for (String name : this.getPlayerOrder()) {
            if (!this.foldedPlayers.contains(name)) {
                System.out.println(this.players.get(name));
            }
        } 
    }

    /**
     * Sets up the next player for playing.
     */
    public void nextPlayer() {
        this.currentPlayer++;
        if (this.getCurrentPlayer() >= this.getPlayerOrder().size()) {
            this.setCurrentPlayer(0);
        }
    }

    /**
     * A method for checking if a player has folded.
     * @param name Name of the player
     * @return true if has folded, otherwise false
     */
    public boolean hasFolded(String name) {
        return this.getFoldedPlayers().contains(name);
    }
    
    /**
     * A method for checking if there are enough players for playing the game (ie.
     * not 2).
     * @return true if enough, otherwise false 
     */
    public boolean hasEnoughPlayersForPlaying() {
        return this.getPlayerOrder().size() > 1;
    }
    
    /**
     * Checks if a user can be used to play
     * @param user The user to be checked
     * @return true if can, false if can not
     */
    public boolean playerUserCanBeUsed(User user) {
        return user.getBalance() > 0;
    }

    /**
     * A method for retrieving the name of the first player in the playing order
     * @return The name
     */
    public String getFirstPlayerName() {
        return this.getPlayerOrder().get(0);
    }

    /**
     * A method for retrieving the Player object the player playing the game has been using
     * @return The Player object
     */
    public Player getHumanPlayer() {
        return this.players.get("Player");
    }

    /**
     * A method that returns a String containing the size of the pot and the
     * cards on the table in a format used by the TextUI
     * @return The String
     */
    public String getPotAndTableString() {
        return "Pot: " + this.getPot() + " Table: " + this.getTable();
    }
    
    /**
     * A method for retrieving the name of the currently playing player
     * @return The name of the player
     */
    public String currentPlayerName() {
        return this.getPlayerOrder().get(this.getCurrentPlayer());
    }
    
    /**
     * A method for retrieving the name of a player that has a certain relation
     * to the current player in the player order
     * @param rel The position of the desired player in relation to the current player
     * (negative: before the current, positive: after)
     * @return The name of the desired player
     */
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

    /**
     * @param players the players to set
     */
    public void setPlayers(HashMap<String, Player> players) {
        this.players = players;
    }
}
