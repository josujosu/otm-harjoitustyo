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
import texasholdem.domain.HandComparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import texasholdem.domain.Card;
import texasholdem.domain.Deck;
import texasholdem.domain.PokerHand;

/**
 *
 * @author josujosu
 */
public class HandComparatorTest {
    
    private HandComparator comp;
    private ArrayList<Integer> ranks;
    private ArrayList<Integer> excluded;
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.comp = new HandComparator();
        this.excluded = new ArrayList<>();
        this.ranks = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            this.ranks.add(0);
        }
    }
    
    @After
    public void tearDown() {
    }
    
    public PokerHand createNonFlushPokerHandFromSize5Array(int...ranks) {
        if(ranks.length != 5) {
            return null;
        }
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, ranks[0]));
        cards.add(new Card(Card.Suit.HEARTS, ranks[1]));
        cards.add(new Card(Card.Suit.DIAMONDS, ranks[2]));
        cards.add(new Card(Card.Suit.SPADES, ranks[3]));
        cards.add(new Card(Card.Suit.CLUBS, ranks[4]));
        return new PokerHand(new Deck(cards));
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void getHighestRankFromNumberOfCardsReturnsRightValueWhenNothingIsExcluded() {
        this.ranks.set(13, 3);
        this.ranks.set(4, 5);
        this.ranks.set(10, 2);
        assertEquals(10,this.comp.getHighestRankFromNumberOfCards(ranks, 2));
    }
    
    @Test
    public void getHighestRankFromNumberOfCardsReturnsRightValueWhenRanksAreExcluded() {
        this.ranks.set(13, 2);
        this.ranks.set(4, 5);
        this.ranks.set(10, 2);
        this.comp.getExcluded().add(13);
        assertEquals(10,this.comp.getHighestRankFromNumberOfCards(ranks, 2));
    }
    
    @Test
    public void compareHighestRankFromNumberOfCardsReturnsRightValue() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(7,8,6,2,4);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(4,2,7,1,6);
        assertEquals(1,this.comp.compareHighestRankFromNuberOfCards(hand1, hand2, 1));
    }
    
    @Test
    public void compareHighestFromNumberOfCardsExclusionIsSeenInMethodUsingIt() {
        ArrayList<Card> cards1 = new ArrayList<>();
        cards1.add(new Card(Card.Suit.CLUBS, 2));
        cards1.add(new Card(Card.Suit.HEARTS, 2));
        cards1.add(new Card(Card.Suit.SPADES, 6));
        cards1.add(new Card(Card.Suit.CLUBS, 6));
        cards1.add(new Card(Card.Suit.DIAMONDS, 6));
        
        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(Card.Suit.CLUBS, 4));
        cards2.add(new Card(Card.Suit.HEARTS, 4));
        cards2.add(new Card(Card.Suit.SPADES, 6));
        cards2.add(new Card(Card.Suit.CLUBS, 6));
        cards2.add(new Card(Card.Suit.DIAMONDS, 6));
        
        this.comp.compareHighestRankFromNuberOfCards(new PokerHand(new Deck(cards1)), new PokerHand(new Deck(cards2)), 3);
        assertEquals(new Integer(5), this.comp.getExcluded().get(0));
    }
   
    @Test
    public void comparingFourOfAKindWithDifferentFourReturnsPositiveWhenHand1HasBetterHand() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(6,6,6,6,4);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(4,4,4,4,6);
        assertEquals(2,this.comp.compare(hand1, hand2));
    }
    
    /*
    @Test
    public void comparingFourOfAKindWithSameFourReturnsPositiveWhenHand1HasBetterHand() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(9,9,9,9,8);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(9,9,9,9,6);
        System.out.println(hand1.getRanksInHand());
        System.out.println(hand2.getRanksInHand());
        assertEquals(2,this.comp.compare(hand1, hand2));
    }
*/
    
    @Test
    public void compareHighWorksWhenRanksHaveToBeExcluded() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(4,2,5,7,10);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(4,6,3,7,10);
        assertEquals(-1, this.comp.compareHigh(hand1, hand2, 5));
    }

}
