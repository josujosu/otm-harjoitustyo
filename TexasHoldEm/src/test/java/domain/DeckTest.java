/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.Arrays;
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
public class DeckTest {
    
    private Deck d52;
        
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.d52 = new Deck();
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
    public void constructorCreatesTheCorrectListOfCardsWhenGivenAListAsTheParameter() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 2));
        cards.add(new Card(Card.Suit.CLUBS, 6));
        cards.add(new Card(Card.Suit.CLUBS, 9));
        Deck deck = new Deck(cards);
        assertEquals(cards.toString(), deck.getCards().toString());
    }
    
    @Test
    public void create52CardDeckCreatesAnArrayListWith52Cards() {
        ArrayList<Card> cards = this.d52.getCards();
        assertEquals(52, cards.size());
    }
    
    @Test
    public void create52CardDeckCreatesOnlyUniqueCards() {
        ArrayList<Card> cards = this.d52.getCards();
        Card testCard = cards.get(0);
        boolean isSame = false;
        for (int i = 1; i < cards.size(); i++) {
            if (testCard.toString().equals(cards.get(i).toString())) {
                isSame = true;
            }
        }
        assertFalse(isSame);
    }
    
    @Test
    public void create52CardDeckCreatesAShuffledDeck() {
        ArrayList<Card> cards = this.d52.getCards();
        ArrayList<Card> cards2 = this.d52.create52CardDeck();
        assertNotEquals(cards2.toString(), cards.toString());
    }
    
    @Test
    public void takeCardsRemovesCardsFromTheDeck() {
        this.d52.takeCards(3);
        assertEquals(49, this.d52.getCards().size());
    }
    
    
    
}
