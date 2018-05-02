/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class used for comparing poker hands as PokerHand objects
 * @author josujosu
 */
public class HandComparator {

    final HashMap<PokerHand.HandType, Integer> handTypes;
    private ArrayList<Integer> excluded;

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
     * Compares two PokerHand variables and figures out the better of the two. It
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
                return this.compareOnceFromNumberOfCardsThenHigh(hand1, hand2, 4);
            case FULLHOUSE:
                return this.compareTwiceFromNumberOfCardsThenHigh(hand1, hand2, 3, 2);
            case FLUSH:
                return this.compareHigh(hand1, hand2, 5);
            case STRAIGHT:
                return this.compareHighest(hand1, hand2);
            case THREE:
                return this.compareOnceFromNumberOfCardsThenHigh(hand1, hand2, 3);
            case TWOPAIRS:
                return this.compareTwiceFromNumberOfCardsThenHigh(hand1, hand2, 2, 2);
            case PAIR:
                return this.compareOnceFromNumberOfCardsThenHigh(hand1, hand2, 2);
            default:
                return this.compareHigh(hand1, hand2, 5);
        }
    }

    /**
     * A method for comparing two hands of the type "four of a kind", where the hands have
     * four cards of the same rank. Checks if the four cards have a higher rank in one
     * of the hands, if not checks which hand has a better high card.
     * @param hand1 first of the hands to be compared
     * @param hand2 second of the hands to be compared
     * @param n
     * @param excluded
     * @return A positive value if hand1 is better, negative if hand2 and 0 if they
     * are equally good
     */
    /*
    public int compareFourOfAKind(PokerHand hand1, PokerHand hand2) {
        int comparison = this.compareHighestRankFromNuberOfCards(hand1, hand2, 4, new ArrayList<>());
        if (comparison == 0) {
            return this.compareHigh(hand1, hand2, 1);
        } else {
            return comparison;
        }
    }

    
    public int compareFullHouse(PokerHand hand1, PokerHand hand2) {
        int comparison = this.compareHighestRankFromNuberOfCards(hand1, hand2, 3, new ArrayList<>());
        if (comparison != 0) {
            return comparison;
        }
        comparison = this.compareHighestRankFromNuberOfCards(hand1, hand2, 2, new ArrayList<>());
        if (comparison != 0) {
            return comparison;
        }
        return 0;
    }

    public int compareThreeOfAKind(PokerHand hand1, PokerHand hand2) {
        int comparison = this.compareHighestRankFromNuberOfCards(hand1, hand2, 3, new ArrayList<>());
        if (comparison != 0) {
            return comparison;
        } else {
            return this.compareHigh(hand1, hand2, 2);
        }
    }

    public int compareTwoPairs(PokerHand hand1, PokerHand hand2) {
        ArrayList<Integer> excluded = new ArrayList<>();
        int ownTwo = this.getHighestRankFromNumberOfCards(hand1.getRanksInHand(), 2, excluded);
        int otherTwo = this.getHighestRankFromNumberOfCards(hand2.getRanksInHand(), 2, excluded);
        if (ownTwo - otherTwo != 0) {
            return ownTwo - otherTwo;
        }
        excluded.add(ownTwo);
        int comparison = this.compareHighestRankFromNuberOfCards(hand1, hand2, 2, excluded);
        if (comparison != 0) {
            return comparison;
        }
        return this.compareHigh(hand1, hand2, 1);
    }

    public int comparePair(PokerHand hand1, PokerHand hand2) {
        int comparison = this.compareHighestRankFromNuberOfCards(hand1, hand2, 2, new ArrayList<>());
        if (comparison != 0) {
            return comparison;
        }
        return this.compareHigh(hand1, hand2, 3);
    }
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

    public int compareHighest(PokerHand hand1, PokerHand hand2) {
        for (int i = hand1.getRanksInHand().size() - 1; i >= 0; i--) {
            if (hand1.getRanksInHand().get(i) > hand2.getRanksInHand().get(i)) {
                return 1;
            } else if (hand1.getRanksInHand().get(i) > hand2.getRanksInHand().get(i)) {
                return -1;
            }
        }
        return 0;
    }

    public int compareHighestRankFromNuberOfCards(PokerHand hand1, PokerHand hand2, int n) {
        int own = this.getHighestRankFromNumberOfCards(hand1.getRanksInHand(), n);
        int other = this.getHighestRankFromNumberOfCards(hand2.getRanksInHand(), n);
        if (own - other == 0) {
            this.excluded.add(own);
            this.excluded.add(other);
            System.out.println(this.excluded);
        }
        return own - other;
    }
    
    public int compareOnceFromNumberOfCardsThenHigh(PokerHand hand1, PokerHand hand2, int n) {
        int comparison = this.compareHighestRankFromNuberOfCards(hand1, hand2, n);
        if (comparison != 0) {
            return comparison;
        } else {
            return this.compareHigh(hand1, hand2, 5 - n);
        }
    }
    
    public int compareTwiceFromNumberOfCardsThenHigh(PokerHand hand1, PokerHand hand2, int n1, int n2) {
        int comparison = this.compareHighestRankFromNuberOfCards(hand1, hand2, n1);
        if (comparison != 0) {
            return comparison;
        }
        comparison = this.compareHighestRankFromNuberOfCards(hand1, hand2, n2);
        if (comparison != 0) {
            return comparison;
        }
        return this.compareHigh(hand1, hand2, 5 - n1 - n2);
    }

    // Allows you to get eg. rank of pair in a hand from rank array, with exclusion
    // of a certain rank
    public int getHighestRankFromNumberOfCards(ArrayList<Integer> ranks, int n) {
        int rank = 0;
        for (int i = ranks.size() - 1; i >= 0; i--) {
            if ((ranks.get(i) == n) && !(this.excluded.contains(i))) {
                rank = i;
                break;
            }
        }
        return rank;
    }

    final public HashMap<PokerHand.HandType, Integer> createHandTypesArrayInOrder() {
        HashMap<PokerHand.HandType, Integer> newHandTypes = new HashMap<>();
        newHandTypes.put(PokerHand.HandType.ROYALFLUSH, 11);
        newHandTypes.put(PokerHand.HandType.STRAIGHTFLUSH, 10);
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
