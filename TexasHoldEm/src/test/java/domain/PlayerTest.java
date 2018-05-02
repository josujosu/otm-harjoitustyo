package domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author josujosu
 */
import texasholdem.domain.Player;
import texasholdem.domain.Deck;
import texasholdem.domain.Card;
import texasholdem.domain.User;
import java.util.ArrayList;
import texasholdem.domain.Action;

public class PlayerTest {
   
    Player player;
    Deck deck;
    static User user;
    static ArrayList<Card> cards;
    
    @BeforeClass
    public static void setUpClass() {
        cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 14));
        cards.add(new Card(Card.Suit.CLUBS, 2));
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        user = new User(1, "User", 2000);
        deck = new Deck(cards);
        player = new Player(user, deck);
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
    public void constructorWorksAsItShould() {
        assertEquals("User: id: 1, username: User, balance: 2000, bet: 0, Hand: A\u2663 2\u2663 ", player.toString());
    }
    
    @Test
    public void callingWithEnoughMoneyReturnsTheRightValue() {
        String returnValue = Integer.toString(player.call(1000));
        assertEquals("1000", returnValue);
    }
    
    @Test
    public void callingWithNotEnoughMoneyReturnsUserBalance() {
        String returnValue = Integer.toString(player.call(3000));
        assertEquals("2000", returnValue);
    }
    
    @Test
    public void callingIncreasesBet() {
        player.call(100);
        String bet = Integer.toString(player.getBet());
        assertEquals("100", bet);
    }
    
    @Test
    public void raisingWithNotEnoughMoneyReturnsAllOfBalance() {
        String returnValue = Integer.toString(player.raise(600, 1500));
        assertEquals("2000", returnValue);
    }
    
    @Test
    public void raisingWithEnoughtMoneyReturnsBothMoneyNeededForCallAndRaise() {
        String returnValue = Integer.toString(player.raise(100, 1500));
        assertEquals("1600", returnValue);
    }
    
    @Test
    public void raisingWithEnoughtMoneyIncreasesBetRight() {
        player.raise(100, 1500);
        String bet = Integer.toString(player.getBet());
        assertEquals("1600", bet);
    }
    
    @Test
    public void playReturnsAnAction() {
        Action a = player.play(100, this.deck);
        assertTrue(a != null);
    }
    
    @Test
    public void actMakesThePlayerCallWhenACallActionIsGivenAsAParameter() {
        this.player.act(new Action(Action.ActionType.CALL, 100, 0));
        assertEquals(100, player.getBet());
    }
    
    @Test
    public void actMakesThePlayerRaiseWhenARaiseActionIsGivenAsAParameter() {
        this.player.act(new Action(Action.ActionType.RAISE, 100, 10));
        assertEquals(110, this.player.getBet());
    }
    
}
