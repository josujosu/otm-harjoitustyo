/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import texasholdem.domain.PokerHand;
import texasholdem.domain.Card;
import texasholdem.domain.Deck;

public class PokerHandTest {
    
    private ArrayList<Integer> emptyRanks;
    private PokerHand hand;
    private ArrayList<Card> cards;
    private Deck initDeck;
    
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 3));
        cards.add(new Card(Card.Suit.DIAMONDS, 4));
        cards.add(new Card(Card.Suit.CLUBS, 9));
        cards.add(new Card(Card.Suit.CLUBS, 10));
        cards.add(new Card(Card.Suit.SPADES, 14));
        cards.add(new Card(Card.Suit.CLUBS, 2));
        cards.add(new Card(Card.Suit.DIAMONDS, 7));
        this.initDeck = new Deck(cards);
        this.hand = new PokerHand(initDeck);
        this.emptyRanks = new ArrayList<>();
        for(int i = 0; i < 14; i++) {
            this.emptyRanks.add(0);
        }
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
    public void highestInStraightReturnsRightValueWhenGivenArrayHasStraight() {
        this.emptyRanks.set(11, 1);
        this.emptyRanks.set(8, 2);
        this.emptyRanks.set(7, 1);
        this.emptyRanks.set(6, 1);
        this.emptyRanks.set(5, 1);
        this.emptyRanks.set(4, 1);
        assertEquals(9, this.hand.highestInStraight(emptyRanks));
    }
    
    @Test
    public void create5CardHandFromCardArrayCanCreateHighCardHand() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 3));
        cards.add(new Card(Card.Suit.DIAMONDS, 4));
        cards.add(new Card(Card.Suit.CLUBS, 9));
        cards.add(new Card(Card.Suit.CLUBS, 10));
        cards.add(new Card(Card.Suit.SPADES, 14));
        cards.add(new Card(Card.Suit.CLUBS, 2));
        cards.add(new Card(Card.Suit.DIAMONDS, 7));
        Deck highDeck = new Deck(cards);
        this.hand.create5CardHandFromADeck(highDeck);
        assertEquals(PokerHand.HandType.HIGH, this.hand.getHandType());
    }
    
    @Test
    public void create5CardHandFromCardArrayCanCreatePairHand() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 3));
        cards.add(new Card(Card.Suit.DIAMONDS, 3));
        cards.add(new Card(Card.Suit.CLUBS, 9));
        cards.add(new Card(Card.Suit.CLUBS, 10));
        cards.add(new Card(Card.Suit.SPADES, 14));
        cards.add(new Card(Card.Suit.CLUBS, 2));
        cards.add(new Card(Card.Suit.DIAMONDS, 7));
        Deck highDeck = new Deck(cards);
        this.hand.create5CardHandFromADeck(highDeck);
        assertEquals(PokerHand.HandType.PAIR, this.hand.getHandType());
    }
    
    @Test
    public void create5CardHandFromCardArrayCanCreateTwoPairsHand() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 3));
        cards.add(new Card(Card.Suit.DIAMONDS, 3));
        cards.add(new Card(Card.Suit.CLUBS, 2));
        cards.add(new Card(Card.Suit.CLUBS, 10));
        cards.add(new Card(Card.Suit.SPADES, 14));
        cards.add(new Card(Card.Suit.CLUBS, 2));
        cards.add(new Card(Card.Suit.DIAMONDS, 7));
        Deck highDeck = new Deck(cards);
        this.hand.create5CardHandFromADeck(highDeck);
        assertEquals(PokerHand.HandType.TWOPAIRS, this.hand.getHandType());
    }
    
    @Test
    public void create5CardHandFromCardArrayCanCreateThreeOfAKindHand() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 3));
        cards.add(new Card(Card.Suit.DIAMONDS, 2));
        cards.add(new Card(Card.Suit.CLUBS, 2));
        cards.add(new Card(Card.Suit.CLUBS, 10));
        cards.add(new Card(Card.Suit.SPADES, 14));
        cards.add(new Card(Card.Suit.CLUBS, 2));
        cards.add(new Card(Card.Suit.DIAMONDS, 7));
        Deck highDeck = new Deck(cards);
        this.hand.create5CardHandFromADeck(highDeck);
        assertEquals(PokerHand.HandType.THREE, this.hand.getHandType());
    }
    
    @Test
    public void create5CardHandFromCardArrayCanCreateFourOfAKindHand() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 3));
        cards.add(new Card(Card.Suit.DIAMONDS, 3));
        cards.add(new Card(Card.Suit.CLUBS, 3));
        cards.add(new Card(Card.Suit.CLUBS, 10));
        cards.add(new Card(Card.Suit.SPADES, 14));
        cards.add(new Card(Card.Suit.CLUBS, 3));
        cards.add(new Card(Card.Suit.DIAMONDS, 7));
        Deck highDeck = new Deck(cards);
        this.hand.create5CardHandFromADeck(highDeck);
        assertEquals(PokerHand.HandType.FOUR, this.hand.getHandType());
    }
    
    @Test
    public void create5CardHandFromCardArrayCanCreateFullHouseHand() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 3));
        cards.add(new Card(Card.Suit.DIAMONDS, 3));
        cards.add(new Card(Card.Suit.CLUBS, 2));
        cards.add(new Card(Card.Suit.CLUBS, 2));
        cards.add(new Card(Card.Suit.SPADES, 3));
        cards.add(new Card(Card.Suit.CLUBS, 4));
        cards.add(new Card(Card.Suit.DIAMONDS, 4));
        Deck highDeck = new Deck(cards);
        this.hand.create5CardHandFromADeck(highDeck);
        assertEquals(PokerHand.HandType.FULLHOUSE, this.hand.getHandType());
    }
    
    @Test
    public void create5CardHandFromCardArrayCanCreateStraightHand() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 3));
        cards.add(new Card(Card.Suit.DIAMONDS, 4));
        cards.add(new Card(Card.Suit.CLUBS, 5));
        cards.add(new Card(Card.Suit.CLUBS, 6));
        cards.add(new Card(Card.Suit.SPADES, 7));
        cards.add(new Card(Card.Suit.CLUBS, 13));
        cards.add(new Card(Card.Suit.DIAMONDS, 12));
        Deck highDeck = new Deck(cards);
        this.hand.create5CardHandFromADeck(highDeck);
        assertEquals(PokerHand.HandType.STRAIGHT, this.hand.getHandType());
    }
    
    @Test
    public void create5CardHandFromCardArrayCanCreateFlushHand() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 3));
        cards.add(new Card(Card.Suit.CLUBS, 11));
        cards.add(new Card(Card.Suit.CLUBS, 5));
        cards.add(new Card(Card.Suit.CLUBS, 6));
        cards.add(new Card(Card.Suit.CLUBS, 7));
        cards.add(new Card(Card.Suit.CLUBS, 13));
        cards.add(new Card(Card.Suit.DIAMONDS, 12));
        Deck highDeck = new Deck(cards);
        this.hand.create5CardHandFromADeck(highDeck);
        assertEquals(PokerHand.HandType.FLUSH, this.hand.getHandType());
    }
    
    @Test
    public void create5CardHandFromCardArrayCanCreateStraightFlushHand() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 3));
        cards.add(new Card(Card.Suit.CLUBS, 4));
        cards.add(new Card(Card.Suit.CLUBS, 5));
        cards.add(new Card(Card.Suit.CLUBS, 6));
        cards.add(new Card(Card.Suit.CLUBS, 7));
        cards.add(new Card(Card.Suit.CLUBS, 13));
        cards.add(new Card(Card.Suit.DIAMONDS, 12));
        Deck highDeck = new Deck(cards);
        this.hand.create5CardHandFromADeck(highDeck);
        assertEquals(PokerHand.HandType.STRAIGHTFLUSH, this.hand.getHandType());
    }
    
    @Test
    public void create5CardHandFromCardArrayCanCreateRoyalFlushHand() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 2));
        cards.add(new Card(Card.Suit.CLUBS, 10));
        cards.add(new Card(Card.Suit.CLUBS, 11));
        cards.add(new Card(Card.Suit.CLUBS, 12));
        cards.add(new Card(Card.Suit.CLUBS, 13));
        cards.add(new Card(Card.Suit.CLUBS, 14));
        cards.add(new Card(Card.Suit.DIAMONDS, 12));
        Deck highDeck = new Deck(cards);
        this.hand.create5CardHandFromADeck(highDeck);
        assertEquals(PokerHand.HandType.ROYALFLUSH, this.hand.getHandType());
    }
    
    @Test
    public void create5CardHandFromCardArrayUnderstandsStraightWhenAceIsSmallest() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, 2));
        cards.add(new Card(Card.Suit.CLUBS, 14));
        cards.add(new Card(Card.Suit.SPADES, 3));
        cards.add(new Card(Card.Suit.HEARTS, 4));
        cards.add(new Card(Card.Suit.CLUBS, 5));
        cards.add(new Card(Card.Suit.CLUBS, 14));
        cards.add(new Card(Card.Suit.DIAMONDS, 12));
        Deck highDeck = new Deck(cards);
        this.hand.create5CardHandFromADeck(highDeck);
        assertEquals(PokerHand.HandType.STRAIGHT, this.hand.getHandType());
    }   
}
