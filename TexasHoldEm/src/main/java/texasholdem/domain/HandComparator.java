/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author josujosu
 */
public class HandComparator {

    final HashMap<PokerHand.HandType, Integer> handTypes;

    public HandComparator() {
        this.handTypes = this.createHandTypesArrayInOrder();
    }

    public ArrayList<String> getBest(HashMap<String, Deck> map) {
        ArrayList<String> best = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        names.addAll(map.keySet());
        best.add(names.get(0));
        HashMap<String, PokerHand> hands = new HashMap<>();
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

    // returns positive if hand1 is better, negative if hand2 is better and 0
    // if the hands are the same value
    public int compare(PokerHand hand1, PokerHand hand2) {
        if (this.handTypes.get(hand1.getHandType()) > this.handTypes.get(hand2.getHandType())) {
            return 1;
        } else if (this.handTypes.get(hand1.getHandType()) < this.handTypes.get(hand2.getHandType())) {
            return -1;
        } else {
            return this.compareSameHands(hand1, hand2);
        }
    }

    // Absolutely no reason to splice this method into smaller pieces even though
    // checkstyle would want it
    public int compareSameHands(PokerHand hand1, PokerHand hand2) {
        switch (hand1.getHandType()) {
            case ROYALFLUSH:
                return 0;
            case STRAIGHTFLUSH:
                return this.compareHighest(hand1, hand2);
            case FOUR:
                return this.compareFourOfAKind(hand1, hand2);
            case FULLHOUSE:
                return this.compareFullHouse(hand1, hand2);
            case FLUSH:
                return this.compareHigh(hand1, hand2, 5);
            case STRAIGHT:
                return this.compareHighest(hand1, hand2);
            case THREE:
                return this.compareThreeOfAKind(hand1, hand2);
            case TWOPAIRS:
                return this.compareTwoPairs(hand1, hand2);
            case PAIR:
                return this.comparePair(hand1, hand2);
            default:
                return this.compareHigh(hand1, hand2, 5);
        }
    }

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

    public int compareHigh(PokerHand hand1, PokerHand hand2, int n) {
        ArrayList<Integer> excluded = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int own = this.getHighestRankFromNumberOfCards(hand1.getRanksInHand(), 1, excluded);
            int other = this.getHighestRankFromNumberOfCards(hand2.getRanksInHand(), 1, excluded);
            if (own - other != 0) {
                return own - other;
            } else {
                excluded.add(own);
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

    public int compareHighestRankFromNuberOfCards(PokerHand hand1, PokerHand hand2, int n, ArrayList<Integer> excluded) {
        int own = this.getHighestRankFromNumberOfCards(hand1.getRanksInHand(), n, excluded);
        int other = this.getHighestRankFromNumberOfCards(hand2.getRanksInHand(), n, excluded);
        return own - other;
    }

    // Allows you to get eg. rank of pair in a hand from rank array, with exclusion
    // of a certain rank
    public int getHighestRankFromNumberOfCards(ArrayList<Integer> ranks, int n, ArrayList<Integer> excluded) {
        int rank = 0;
        for (int i = ranks.size() - 1; i >= 0; i--) {
            if (ranks.get(i) == n && !excluded.contains(i)) {
                rank = i;
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

}
