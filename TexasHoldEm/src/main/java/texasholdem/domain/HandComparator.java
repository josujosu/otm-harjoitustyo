/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A class used for comparing poker hands as PokerHand objects
 * @author josujosu
 */
public class HandComparator {

    final HashMap<PokerHand.HandType, Integer> handTypes;
    private ArrayList<Integer> excluded;

    /**
     * Constructor
     */
    public HandComparator() {
        this.handTypes = this.createHandTypesArrayInOrder();
        this.excluded = new ArrayList<>();
    }
    
    /**
     * A method for obtaining the holders of the best poker hands from a number 
     * of different card decks.
     * @param map A HashMap in which the keys are the names of the holders of the decks 
     * as String and values are the decks themselves as Deck objects.
     * @return A String ArrayList containing the map keys of the decks containing
     * the best hands
     */
    public ArrayList<String> getBest(HashMap<String, Deck> map) {
        ArrayList<String> best = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        names.addAll(map.keySet());
        best.add(names.get(0));
        for (int i = 1; i < names.size(); i++) {
            int comparison = this.compare(new PokerHand(map.get(best.get(0))), new PokerHand(map.get(names.get(i))));
            if (comparison < 0) {
                best.clear();
                best.add(names.get(i));
            } else if (comparison == 0) {
                best.add(names.get(i));
            }
        }
        return best;
    }

    /**
     * A method that compares two PokerHand variables and figures out the better of the two. It
     * first checks if one of the hands has a better type. If not, the method 
     * "compareSameHands" is called.
     * @param hand1 The first hand to be compared
     * @param hand2 The second hand to be compared
     * @return A positive value if hand1 is better, negative if hand2 and 0 if they
     * are equally good
     */
    public int compare(PokerHand hand1, PokerHand hand2) {
        this.getExcluded().clear();
        if (this.handTypes.get(hand1.getHandType()) > this.handTypes.get(hand2.getHandType())) {
            return 1;
        } else if (this.handTypes.get(hand1.getHandType()) < this.handTypes.get(hand2.getHandType())) {
            return -1;
        } else {
            return this.compareSameHands(hand1, hand2);
        }
    }

    /**
     * A method for comparing hands that have same the same type. Assumes that 
     * both hands actually have the same type.
     * @param hand1 first of the hands to be compared
     * @param hand2 second of the hands to be compared
     * @return A positive value if hand1 is better, negative if hand2 and 0 if they
     * are equally good
     */
    public int compareSameHands(PokerHand hand1, PokerHand hand2) {
        switch (hand1.getHandType()) {
            case ROYALFLUSH:
                return 0;
            case STRAIGHTFLUSH:
                return this.compareHighest(hand1, hand2);
            case FOUR:
                return this.compareFromNumberOfCardsThenHigh(hand1, hand2, 4);
            case FULLHOUSE:
                return this.compareFromNumberOfCardsThenHigh(hand1, hand2, 3, 2);
            case FLUSH:
                return this.compareHigh(hand1, hand2, 5);
            case STRAIGHT:
                return this.compareHighest(hand1, hand2);
            case THREE:
                return this.compareFromNumberOfCardsThenHigh(hand1, hand2, 3);
            case TWOPAIRS:
                return this.compareFromNumberOfCardsThenHigh(hand1, hand2, 2, 2);
            case PAIR:
                return this.compareFromNumberOfCardsThenHigh(hand1, hand2, 2);
            default:
                return this.compareHigh(hand1, hand2, 5);
        }
    }

    /**
     * A method for comparing the high cards of two poker hands
     * @param hand1 First hand to be compared
     * @param hand2 Second hand to be compared
     * @param n Number of possible high cards (ex. 5 card hand has a pair, so number
     * of possible high cards is 3)
     * @return A positive value if hand1 is better, negative if hand2 and 0 if they
     * are equally good
     */
    public int compareHigh(PokerHand hand1, PokerHand hand2, int n) {
        for (int i = 0; i < n; i++) {
            int comparison = this.compareHighestRankFromNuberOfCards(hand1, hand2, 1);
            if (comparison != 0) {
                return comparison;
            }
        }
        return 0;
    }

    /**
     * A method for comparing the highest rank found in the given hands
     * @param hand1 First hand to be compared
     * @param hand2 Second hand to be compared
     * @return 1 if hand1 has higher card, 2 if hand 2 has higher card and 0 if
     * the highest card in both hands has the same rank
     */
    public int compareHighest(PokerHand hand1, PokerHand hand2) {
        for (int i = hand1.getRanksInHand().size() - 1; i >= 0; i--) {
            if (hand1.getRanksInHand().get(i) > hand2.getRanksInHand().get(i)) {
                return 1;
            } else if (hand1.getRanksInHand().get(i) < hand2.getRanksInHand().get(i)) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * A method that compares two PokerHands by comparing the value of ranks that 
     * appear n times in the given hands.
     * @param hand1 First hand in the comparison
     * @param hand2 Second hand in the comparison
     * @param n Number of times the same rank has to appear in a hand in order 
     * to be compared
     * @return A positive integer if hand1 is better, negative if hand2 and 0 if they
     * are equally good
     */
    public int compareHighestRankFromNuberOfCards(PokerHand hand1, PokerHand hand2, int n) {
        int own = this.getHighestRankFromNumberOfCards(hand1.getRanksInHand(), n);
        int other = this.getHighestRankFromNumberOfCards(hand2.getRanksInHand(), n);
        if (own - other == 0) {
            this.excluded.add(own);
        }
        return own - other;
    }
    
    /**
     * A method that first compares two PokerHands by the value of ranks that appear n
     * times in the given hand and then by the rank of the highest of the 
     * remaining cards.
     * @param hand1 First hand to be compared
     * @param hand2 Second hand to be compared
     * @param n Number of times a rank has to appear for it to be compared in the 
     * first type of comparisons. Giving multiple values makes the type of comparison
     * with all of them.
     * @return A positive value if hand1 is better, negative if hand2 and 0 if they
     * are equally good
     */
    public int compareFromNumberOfCardsThenHigh(PokerHand hand1, PokerHand hand2,  int...n) {        
        int cardsLeft = 5;        
        for (int i = 0; i < n.length; i++) {
            int comparison = this.compareHighestRankFromNuberOfCards(hand1, hand2, n[i]);            
            if (comparison != 0) {                
                return comparison;            
            }            
            cardsLeft -= n[i];        
        }        
        return this.compareHigh(hand1, hand2, cardsLeft);
    }

    /**
     * A method for retrieving the highest rank that appears n times in a specially
     * formatted integer ArrayList containing the ranks of a hand.
     * @param ranks ArrayList containing the number of times a certain rank appears
     * in a hand. The index of the value corresponds to the rank (0 and 13 = ace, 1...12
     * = 2...king).
     * @param n The number of times a rank has to appear.
     * @return The highest rank with n appearences as an integer.
     */
    public int getHighestRankFromNumberOfCards(ArrayList<Integer> ranks, int n) {
        int rank = 0;
        for (int i = ranks.size() - 1; i >= 0; i--) {
            if ((ranks.get(i) == n) && !(this.excluded.contains(i + 1))) {
                rank = i + 1;
                break;
            }
        }
        return rank;
    }

    /**
     * A method that creates a HashMap that contains all of the hand types as keys
     * and the value of the type as values 
     * @return The HashMap
     */
    final public HashMap<PokerHand.HandType, Integer> createHandTypesArrayInOrder() {
        HashMap<PokerHand.HandType, Integer> newHandTypes = new HashMap<>();
        newHandTypes.put(PokerHand.HandType.ROYALFLUSH, 10);
        newHandTypes.put(PokerHand.HandType.STRAIGHTFLUSH, 9);
        newHandTypes.put(PokerHand.HandType.FOUR, 8);
        newHandTypes.put(PokerHand.HandType.FULLHOUSE, 7);
        newHandTypes.put(PokerHand.HandType.FLUSH, 6);
        newHandTypes.put(PokerHand.HandType.STRAIGHT, 5);
        newHandTypes.put(PokerHand.HandType.THREE, 4);
        newHandTypes.put(PokerHand.HandType.TWOPAIRS, 3);
        newHandTypes.put(PokerHand.HandType.PAIR, 2);
        newHandTypes.put(PokerHand.HandType.HIGH, 1);
        return newHandTypes;
    }

    /**
     * @return the excluded
     */
    public ArrayList<Integer> getExcluded() {
        return excluded;
    }
    
    

}
