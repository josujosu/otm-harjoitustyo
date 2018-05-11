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
import java.util.HashMap;
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
    final private ArrayList<PokerHand> hands = this.createListContainingAllHandTypesFromBestToWorst();
    
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
    
    public PokerHand createFlushPokerHandFromSize5Array(int...ranks) {
        if(ranks.length != 5) {
            return null;
        }
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.CLUBS, ranks[0]));
        cards.add(new Card(Card.Suit.CLUBS, ranks[1]));
        cards.add(new Card(Card.Suit.CLUBS, ranks[2]));
        cards.add(new Card(Card.Suit.CLUBS, ranks[3]));
        cards.add(new Card(Card.Suit.CLUBS, ranks[4]));
        return new PokerHand(new Deck(cards));
    }
    
    public ArrayList<PokerHand> createListContainingAllHandTypesFromBestToWorst() {
        ArrayList<PokerHand> hands = new ArrayList<>();
        hands.add(this.createFlushPokerHandFromSize5Array(14,13,12,11,10));
        hands.add(this.createFlushPokerHandFromSize5Array(10,9,8,7,6));
        hands.add(this.createNonFlushPokerHandFromSize5Array(7,7,7,7,4));
        hands.add(this.createNonFlushPokerHandFromSize5Array(6,6,6,2,2));
        hands.add(this.createFlushPokerHandFromSize5Array(4,2,8,10,9));
        hands.add(this.createNonFlushPokerHandFromSize5Array(10,9,8,7,6));
        hands.add(this.createNonFlushPokerHandFromSize5Array(3,3,3,2,6));
        hands.add(this.createNonFlushPokerHandFromSize5Array(3,3,4,4,7));
        hands.add(this.createNonFlushPokerHandFromSize5Array(2,2,6,8,4));
        hands.add(this.createNonFlushPokerHandFromSize5Array(2,7,4,9,10));
        return hands;
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
        assertEquals(11,this.comp.getHighestRankFromNumberOfCards(ranks, 2));
    }
    
    @Test
    public void getHighestRankFromNumberOfCardsReturnsRightValueWhenRanksAreExcluded() {
        this.ranks.set(13, 2);
        this.ranks.set(4, 5);
        this.ranks.set(10, 2);
        this.comp.getExcluded().add(14);
        assertEquals(11,this.comp.getHighestRankFromNumberOfCards(ranks, 2));
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
        assertEquals(new Integer(6), this.comp.getExcluded().get(0));
    }
   
    @Test
    public void comparingFourOfAKindWithDifferentFourReturnsPositiveWhenHand1HasBetterHand() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(6,6,6,6,4);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(4,4,4,4,6);
        assertEquals(2,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingFourOfAKindWithDifferentFourReturnsNegativeWhenHand2HasBetterHand() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(6,6,6,6,4);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(7,7,7,7,6);
        assertEquals(-1,this.comp.compare(hand1, hand2));
    }
    
    
    @Test
    public void comparingFourOfAKindWithSameFourReturnsPositiveWhenHand1HasBetterHand() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(9,9,9,9,8);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(9,9,9,9,6);
        assertEquals(2,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingFourOfAKindWithSameFourReturnsNegativeWhenHand2HasBetterHand() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(9,9,9,9,8);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(9,9,9,9,11);
        assertEquals(-3,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingFourOfAKindReturns0WhenBothHandsAreTheSame() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(9,9,9,9,8);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(9,9,9,9,8);
        assertEquals(0,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingFullHouseWithDifferentThreeReturnsPositiveWhenHand1HasBetterHand() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,8,2,2);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(3,3,3,8,8);
        assertEquals(5,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingFullHouseWithDifferentThreeReturnsNegativeWhenHand2HasBetterHand() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,8,2,2);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(10,10,10,8,8);
        assertEquals(-2,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingFullHouseWithSameThreeReturnsPositiveWhenHand1HasBetterHand() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,8,5,5);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(8,8,8,2,2);
        assertEquals(3,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingFullHouseWithSameThreeReturnsNegativeWhenHand2HasBetterHand() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,8,5,5);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(8,8,8,9,9);
        assertEquals(-4,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingFullHouseWithSameHandsReturns0() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,8,5,5);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(8,8,8,5,5);
        assertEquals(0,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingStraightReturnsPositiveWhenHand1HasBetterHand() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(4,6,8,7,5);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(2,3,4,6,5);
        assertEquals(1,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingStraightReturnsNegativeWhenHand2HasBetterHand() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(4,6,8,7,5);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(10,9,8,6,7);
        assertEquals(-1,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingStraightReturns0WhenBothHandsAreSame() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(4,6,8,7,5);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(4,6,8,5,7);
        assertEquals(0,this.comp.compare(hand1, hand2)); 
    }

    @Test
    public void comparingThreeOfAKindWithDifferentThreeReturnsPositiveWhenHand1IsBetter() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,8,7,5);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(4,4,4,10,7);
        assertEquals(4,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingThreeOfAKindWithDifferentThreeReturnsNegativeWhenHand2IsBetter() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,8,7,5);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(4,10,10,10,7);
        assertEquals(-2,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingThreeOfAKindWithSameThreeReturnsPositiveWhenHand1IsBetter() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,8,7,5);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(8,8,8,2,3);
        assertEquals(4,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingThreeOfAKindWithSameThreeReturnsNegativeWhenHand2IsBetter() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,8,7,5);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(8,8,8,10,7);
        assertEquals(-3,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingThreeOfAKindWithSameHandsReturns0() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,8,7,10);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(8,8,8,10,7);
        assertEquals(0,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingTwoPairsReturnsPositiveWhenBothPairsAreBetterInHand1() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,9,9,2);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(2,2,4,4,7);
        assertEquals(5,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingTwoPairsReturnsPositiveWhenOnePairIsBetterInHand1() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,10,10,2);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(2,2,9,9,7);
        assertEquals(1,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingTwoPairsReturnsNegativeWhenBothPairsAreBetterInHand2() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,9,9,2);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(10,10,14,14,7);
        assertEquals(-5,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingTwoPairsReturnsNegativeWhenOnePairIsBetterInHand2() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(8,8,10,10,2);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(2,2,14,14,7);
        assertEquals(-4,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingTwoPairsReturnssPositiveWhenPairsAreTheSameButHand1IsBetter() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(10,10,14,14,11);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(10,10,14,14,7);
        System.out.println(hand1.getRanksInHand());
        System.out.println(hand2.getRanksInHand());
        assertEquals(4,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingTwoPairsReturnsNegativeWhenPairsAreTheSameButHand2IsBetter() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(10,10,14,14,11);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(10,10,14,14,13);
        assertEquals(-2,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingTwoPairsReturns0WhenBothHandsAreSame() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(10,10,14,14,11);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(10,10,14,14,11);
        assertEquals(0,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingPairsReturnsPositiveWhenPairInHand1IsBetter() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(10,10,4,6,11);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(9,9,14,13,11);
        assertEquals(1,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingPairsReturnsNegativeWhenPairInHand2IsBetter() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(10,10,4,6,11);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(9,5,14,14,11);
        assertEquals(-4,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingPairsReturnsPositiveWhenPairIsSameButHand1IsBetter() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(14,12,4,6,14);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(9,5,14,14,11);
        assertEquals(1,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingPairsReturnsNegativeWhenPairIsSameButHand2IsBetter() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(14,12,4,6,14);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(9,5,14,14,13);
        assertEquals(-1,this.comp.compare(hand1, hand2)); 
    }
    
    @Test
    public void comparingPairsReturns0WhenBothHandsAreSame() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(14,12,4,6,14);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(12,4,14,14,6);
        assertEquals(0,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingHighReturnsPositiveWhenHand1IsBetter() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(14,11,4,6,13);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(12,4,11,13,6);
        assertEquals(1,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingHighReturnsNegativeWhenHand2IsBetter() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(2,11,4,6,3);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(12,4,11,13,6);
        assertEquals(-2,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingHighReturns0WhenBothHandsAreTheSame() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(12,11,4,6,13);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(12,4,11,13,6);
        assertEquals(0,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingFlushReturnsPositiveWhenHand1IsBetter() {
        PokerHand hand1 = this.createFlushPokerHandFromSize5Array(3,7,4,10,14);
        PokerHand hand2 = this.createFlushPokerHandFromSize5Array(3,7,4,10,12);
        assertEquals(2,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingFlushReturnsNegativeWhenHand2IsBetter() {
        PokerHand hand1 = this.createFlushPokerHandFromSize5Array(3,7,4,10,11);
        PokerHand hand2 = this.createFlushPokerHandFromSize5Array(3,7,4,10,12);
        assertEquals(-1,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingFlushReturns0WhenBothHandsAreSame() {
        PokerHand hand1 = this.createFlushPokerHandFromSize5Array(3,7,4,10,12);
        PokerHand hand2 = this.createFlushPokerHandFromSize5Array(3,7,4,10,12);
        assertEquals(0,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingStraightFlushReturnsPositiveWhenHand1IsBetter() {
        PokerHand hand1 = this.createFlushPokerHandFromSize5Array(13,9,10,11,12);
        PokerHand hand2 = this.createFlushPokerHandFromSize5Array(9,11,8,10,12);
        assertEquals(1,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingStraightFlushReturnsNegativeWhenHand2IsBetter() {
        PokerHand hand1 = this.createFlushPokerHandFromSize5Array(5,2,3,4,14);
        PokerHand hand2 = this.createFlushPokerHandFromSize5Array(9,10,11,12,13);
        System.out.println(hand1.getHandType());
        assertEquals(-1,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingStraightFlushReturns0WhenBothHandsAreSame() {
        PokerHand hand1 = this.createFlushPokerHandFromSize5Array(9,8,11,10,12);
        PokerHand hand2 = this.createFlushPokerHandFromSize5Array(8,9,11,10,12);
        assertEquals(0,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void comparingRoyalFlushReturns0() {
        PokerHand hand1 = this.createFlushPokerHandFromSize5Array(12,13,11,10,14);
        PokerHand hand2 = this.createFlushPokerHandFromSize5Array(12,13,11,10,14);
        assertEquals(0,this.comp.compare(hand1, hand2));
    }
    
    @Test
    public void compareHighWorksWhenRanksHaveToBeExcluded() {
        PokerHand hand1 = this.createNonFlushPokerHandFromSize5Array(4,2,5,7,10);
        PokerHand hand2 = this.createNonFlushPokerHandFromSize5Array(4,6,3,7,10);
        assertEquals(-1, this.comp.compareHigh(hand1, hand2, 5));
    }
    
    @Test
    public void royalFlushWinsAgainstTheRightAmountOfHandTypes() {
        int wins = 0;
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                continue;
            } 
            wins += this.comp.compare(this.hands.get(0), this.hands.get(i));
        }
    }
    
    @Test
    public void straightFlushWinsAgainstTheRightAmountOfHandTypes() {
        int wins = 0;
        for (int i = 0; i < 10; i++) {
            if (i == 1) {
                continue;
            } 
            int comparison = this.comp.compare(this.hands.get(1), this.hands.get(i));
            if (comparison > 0) {
                wins += comparison;
            }
        }
        assertEquals(8, wins);
    }
    
    @Test
    public void fourOfAKindWinsAgainstTheRightAmountOfHandTypes() {
        int wins = 0;
        for (int i = 0; i < 10; i++) {
            if (i == 2) {
                continue;
            } 
            int comparison = this.comp.compare(this.hands.get(2), this.hands.get(i));
            if (comparison > 0) {
                wins += comparison;
            }
        }
        assertEquals(7, wins);
    }
    
    @Test
    public void fullHouseWinsAgainstTheRightAmountOfHandTypes() {
        int wins = 0;
        for (int i = 0; i < 10; i++) {
            if (i == 3) {
                continue;
            } 
            int comparison = this.comp.compare(this.hands.get(3), this.hands.get(i));
            if (comparison > 0) {
                wins += comparison;
            }
        }
        assertEquals(6, wins);
    }
    
    @Test
    public void flushWinsAgainstTheRightAmountOfHandTypes() {
        int wins = 0;
        for (int i = 0; i < 10; i++) {
            if (i == 4) {
                continue;
            } 
            int comparison = this.comp.compare(this.hands.get(4), this.hands.get(i));
            if (comparison > 0) {
                wins += comparison;
            }
            System.out.println(wins);
        }
        assertEquals(5, wins);
    }
    
    @Test
    public void straightWinsAgainstTheRightAmountOfHandTypes() {
        int wins = 0;
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                continue;
            } 
            int comparison = this.comp.compare(this.hands.get(5), this.hands.get(i));
            if (comparison > 0) {
                wins += comparison;
            }
        }
        assertEquals(4, wins);
    }
    
    @Test
    public void threeOfAKindAgainstTheRightAmountOfHandTypes() {
        int wins = 0;
        for (int i = 0; i < 10; i++) {
            if (i == 6) {
                continue;
            } 
            int comparison = this.comp.compare(this.hands.get(6), this.hands.get(i));
            if (comparison > 0) {
                wins += comparison;
            }
        }
        assertEquals(3, wins);
    }
    
    @Test
    public void twoPairsWinsAgainstTheRightAmountOfHandTypes() {
        int wins = 0;
        for (int i = 0; i < 10; i++) {
            if (i == 7) {
                continue;
            } 
            int comparison = this.comp.compare(this.hands.get(7), this.hands.get(i));
            if (comparison > 0) {
                wins += comparison;
            }
        }
        assertEquals(2, wins);
    }
    
    @Test
    public void pairWinsAgainstTheRightAmountOfHandTypes() {
        int wins = 0;
        for (int i = 0; i < 10; i++) {
            if (i == 8) {
                continue;
            } 
            int comparison = this.comp.compare(this.hands.get(8), this.hands.get(i));
            if (comparison > 0) {
                wins += comparison;
            }
        }
        assertEquals(1, wins);
    }
    
    @Test
    public void highCardWinsAgainstTheRightAmountOfHandTypes() {
        int wins = 0;
        for (int i = 0; i < 10; i++) {
            if (i == 9) {
                continue;
            } 
            int comparison = this.comp.compare(this.hands.get(9), this.hands.get(i));
            if (comparison > 0) {
                wins += comparison;
            }
        }
        assertEquals(0, wins);
    }
    
    @Test
    public void getBestReturnsTheRightPlayerWhenOnlyOneBestWinner() {
        HashMap<String, Deck> decks = new HashMap<>();
        ArrayList<Card> cards1 = new ArrayList<>();
        ArrayList<Card> cards2 = new ArrayList<>();
        ArrayList<Card> cards3 = new ArrayList<>();
        cards1.add(new Card(Card.Suit.DIAMONDS, 3));
        cards1.add(new Card(Card.Suit.DIAMONDS, 6));
        cards1.add(new Card(Card.Suit.DIAMONDS, 2));
        cards1.add(new Card(Card.Suit.DIAMONDS, 9));
        cards1.add(new Card(Card.Suit.DIAMONDS, 7));
        cards2.add(new Card(Card.Suit.DIAMONDS, 3));
        cards2.add(new Card(Card.Suit.HEARTS, 3));
        cards2.add(new Card(Card.Suit.DIAMONDS, 2));
        cards2.add(new Card(Card.Suit.DIAMONDS, 9));
        cards2.add(new Card(Card.Suit.DIAMONDS, 7));
        cards3.add(new Card(Card.Suit.DIAMONDS, 3));
        cards3.add(new Card(Card.Suit.HEARTS, 3));
        cards3.add(new Card(Card.Suit.DIAMONDS, 3));
        cards3.add(new Card(Card.Suit.DIAMONDS, 10));
        cards3.add(new Card(Card.Suit.DIAMONDS, 10));
        decks.put("1", new Deck(cards1));
        decks.put("2", new Deck(cards2));
        decks.put("3", new Deck(cards3));
        assertEquals("[3]", this.comp.getBest(decks).toString());
    }
    
    @Test
    public void getBestReturnsTheRightPlayersWhenMultipleBestWinners() {
        HashMap<String, Deck> decks = new HashMap<>();
        ArrayList<Card> cards1 = new ArrayList<>();
        ArrayList<Card> cards2 = new ArrayList<>();
        ArrayList<Card> cards3 = new ArrayList<>();
        cards1.add(new Card(Card.Suit.DIAMONDS, 3));
        cards1.add(new Card(Card.Suit.DIAMONDS, 3));
        cards1.add(new Card(Card.Suit.DIAMONDS, 10));
        cards1.add(new Card(Card.Suit.CLUBS, 10));
        cards1.add(new Card(Card.Suit.DIAMONDS, 7));
        cards2.add(new Card(Card.Suit.DIAMONDS, 3));
        cards2.add(new Card(Card.Suit.HEARTS, 3));
        cards2.add(new Card(Card.Suit.DIAMONDS, 2));
        cards2.add(new Card(Card.Suit.DIAMONDS, 9));
        cards2.add(new Card(Card.Suit.DIAMONDS, 7));
        cards3.add(new Card(Card.Suit.DIAMONDS, 3));
        cards3.add(new Card(Card.Suit.HEARTS, 3));
        cards3.add(new Card(Card.Suit.DIAMONDS, 7));
        cards3.add(new Card(Card.Suit.DIAMONDS, 10));
        cards3.add(new Card(Card.Suit.DIAMONDS, 10));
        decks.put("1", new Deck(cards1));
        decks.put("2", new Deck(cards2));
        decks.put("3", new Deck(cards3));
        assertEquals("[1, 3]", this.comp.getBest(decks).toString());
    }
}
