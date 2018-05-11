package domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import texasholdem.domain.Card;
import texasholdem.domain.Deck;

/**
 *
 * @author josujosu
 */
import texasholdem.domain.Game;
import texasholdem.domain.Player;
import texasholdem.domain.User;

public class GameTest {
    
    Game game;
    
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.game = new Game(new User(1, "User", 4000), 8);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void createPlayerOrderShufflesThePlayerOrderAround() {
        ArrayList<String> players = this.game.getPlayerOrder();
        assertNotEquals(players, this.game.createPlayerOrder());
    }
    
    @Test
    public void putCardsOnTableRemovesCardsFromDeck() {
        this.game.putCardsOnTable(3);
        assertEquals(33, this.game.getDeck().getCards().size()); //8*2 cards for players, 3 to table
    }
    
    @Test
    public void putCardsOnTableAddsCardsToTable() {
        this.game.putCardsOnTable(3);
        assertEquals(3, this.game.getTable().getCards().size());
    }
    
    @Test
    public void playBlindsAddsTheRightAmountOfMoneyToThePot() {
        this.game.playBlinds();
        assertEquals(15, this.game.getPot());
    }
    
    @Test
    public void playBlindsMakesTheThirdPlayerTheCurrentPlayer() {
        this.game.playBlinds();
        assertEquals(2, this.game.getCurrentPlayer());
    }
    
    @Test
    public void actPlayersCommandAddsToThePotWhenCommandIsCall() {
        this.game.setLargestBet(15);
        this.game.actPlayersCommand("Call", 0); // Adds the largest bet to the pot
        assertEquals(15, this.game.getPot());
    }
    
    @Test
    public void actPlayersCommandAddsToThePotWhenCommandIsRaise() {
        this.game.setLargestBet(10);
        this.game.actPlayersCommand("Raise", 10); // Adds the largest bet to the pot and raise
        assertEquals(20, this.game.getPot());
    }
    
    @Test
    public void actPlayerCommandAddsThePlayerToFoldedPlayersWhenCommandIsFold() {
        this.game.actPlayersCommand("Fold", 0);
        assertEquals("Player", this.game.getFoldedPlayers().get(0));
    }
    
    @Test
    public void nextPlayerWorksForPlayersNotLastInTheOrder() {
        this.game.nextPlayer();
        assertEquals(1, this.game.getCurrentPlayer());
    }
    
    @Test
    public void nextPlayerWorksForTheLastPlayerInTheOrder() {
        for (int i = 0; i < this.game.getPlayerOrder().size(); i++) {
            this.game.nextPlayer();
        }
        assertEquals(0, this.game.getCurrentPlayer());
    }
    
    @Test
    public void getPlayersWithBestHandsReturnsBestPlayerWhenOnlyOneHasBestHand() {
        HashMap<String, Player> players = new HashMap<>();
        ArrayList<Card> c1 = new ArrayList<>();
        c1.add(new Card(Card.Suit.HEARTS, 14));
        c1.add(new Card(Card.Suit.HEARTS, 13));
        c1.add(new Card(Card.Suit.HEARTS, 12));
        c1.add(new Card(Card.Suit.HEARTS, 11));
        c1.add(new Card(Card.Suit.HEARTS, 10));
        ArrayList<Card> c2 = new ArrayList<>();
        c2.add(new Card(Card.Suit.HEARTS, 13));
        c2.add(new Card(Card.Suit.HEARTS, 12));
        c2.add(new Card(Card.Suit.HEARTS, 11));
        c2.add(new Card(Card.Suit.HEARTS, 10));
        c2.add(new Card(Card.Suit.HEARTS, 9));
        ArrayList<Card> c3 = new ArrayList<>();
        c3.add(new Card(Card.Suit.HEARTS, 14));
        c3.add(new Card(Card.Suit.HEARTS, 8));
        c3.add(new Card(Card.Suit.HEARTS, 12));
        c3.add(new Card(Card.Suit.HEARTS, 11));
        c3.add(new Card(Card.Suit.HEARTS, 10));
        players.put("1", new Player(new User(1, "1", 10), new Deck(c1)));
        players.put("2", new Player(new User(1, "2", 10), new Deck(c2)));
        players.put("3", new Player(new User(1, "3", 10), new Deck(c3)));
        this.game.setPlayers(players);
        assertEquals("1", this.game.getPlayersWithBestHand().get(0).getUser().getUsername());
    }
    
    @Test
    public void getPlayersWithBestHandsReturnsBestPlayerWhenMultipleHasBestHand() {
        HashMap<String, Player> players = new HashMap<>();
        ArrayList<Card> c1 = new ArrayList<>();
        c1.add(new Card(Card.Suit.HEARTS, 14));
        c1.add(new Card(Card.Suit.HEARTS, 13));
        c1.add(new Card(Card.Suit.HEARTS, 12));
        c1.add(new Card(Card.Suit.HEARTS, 11));
        c1.add(new Card(Card.Suit.HEARTS, 10));
        ArrayList<Card> c2 = new ArrayList<>();
        c2.add(new Card(Card.Suit.HEARTS, 13));
        c2.add(new Card(Card.Suit.HEARTS, 12));
        c2.add(new Card(Card.Suit.HEARTS, 11));
        c2.add(new Card(Card.Suit.HEARTS, 10));
        c2.add(new Card(Card.Suit.HEARTS, 14));
        ArrayList<Card> c3 = new ArrayList<>();
        c3.add(new Card(Card.Suit.HEARTS, 14));
        c3.add(new Card(Card.Suit.HEARTS, 8));
        c3.add(new Card(Card.Suit.HEARTS, 12));
        c3.add(new Card(Card.Suit.HEARTS, 11));
        c3.add(new Card(Card.Suit.HEARTS, 10));
        players.put("1", new Player(new User(1, "1", 10), new Deck(c1)));
        players.put("2", new Player(new User(1, "2", 10), new Deck(c2)));
        players.put("3", new Player(new User(1, "3", 10), new Deck(c3)));
        this.game.setPlayers(players);
        assertEquals("12", this.game.getPlayersWithBestHand().get(0).getUser().getUsername() + this.game.getPlayersWithBestHand().get(1).getUser().getUsername());
    }
    
    @Test
    public void playerInRelationToCurrentReturnsRightPlayerWhenRelationIsSamllerThanIndexAndNegative() {
        this.game.setCurrentPlayer(5);
        assertEquals(this.game.getPlayerOrder().get(2), this.game.playerInRelationToCurrent(-3));
    }
    
    @Test
    public void playerInRelationToCurrentReturnsRightPlayerWhenRelationIsLargerThanIndexAndNegative() {
        this.game.setCurrentPlayer(5);
        assertEquals(this.game.getPlayerOrder().get(6), this.game.playerInRelationToCurrent(-7));
    }
    
    @Test
    public void playerInRelationToCurrentReturnsRightPlayerWhenRelationIsSamllerThanIndexAndPositive() {
        this.game.setCurrentPlayer(5);
        assertEquals(this.game.getPlayerOrder().get(6), this.game.playerInRelationToCurrent(1));
    }
    
    @Test
    public void playerInRelationToCurrentReturnsRightPlayerWhenRelationIsLargerThanIndexAndPositive() {
        this.game.setCurrentPlayer(5);
        assertEquals(this.game.getPlayerOrder().get(2), this.game.playerInRelationToCurrent(5));
    }
    
    
}
