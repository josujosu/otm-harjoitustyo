/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

/**
 *
 * @author josujosu
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;

public class PokerHand {

    public enum HandType {
        ROYALFLUSH, STRAIGHTFLUSH, FOUR, FULLHOUSE, FLUSH, STRAIGHT, THREE,
        TWOPAIRS, PAIR, HIGH
    }

    private HandType handType;
    private ArrayList<Integer> ranksInHand;

    public PokerHand(Deck deck) {
        this.create5CardHandFromADeck(deck);
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

    final public void create5CardHandFromADeck(Deck deck) {
        ArrayList<Card> cards = deck.getCards();
        HashMap<Card.Suit, Integer> suits = this.suitMapFromCardArray(cards);
        ArrayList<Integer> ranks = this.rankArrayFromCardArray(cards);
        if (this.checkFlushes(cards, ranks, suits)) {
            return;
        } else {
            this.checkNonFlushes(cards, ranks);
        }

    }

    public boolean checkFlushes(ArrayList<Card> cards, ArrayList<Integer> ranks, HashMap<Card.Suit, Integer> suits) {
        Card.Suit flushSuit = this.flushSuit(suits);
        if (flushSuit != null) {
            int h = this.highestInStraightFlush(flushSuit, ranks, cards);
            if (h < 0) {
                this.handType = HandType.FLUSH;
                this.ranksInHand = this.createRankArrayFromArrayContainingRanks(this.ranksOfHighCards(ranks, 5, new ArrayList<>()));
            } else if (h == 14) {
                this.handType = HandType.ROYALFLUSH;
                this.ranksInHand = this.createRankArrayFromArrayContainingRanks(new ArrayList<>(Arrays.asList(14, 13, 12, 11, 10)));
            } else {
                this.handType = HandType.STRAIGHTFLUSH;
                this.ranksInHand = this.createRankArrayFromArrayContainingRanks(new ArrayList<>(Arrays.asList(h, h - 1, h - 2, h - 3, h - 4)));
            }
            return true;
        }
        return false;
    }

    public void checkNonFlushes(ArrayList<Card> cards, ArrayList<Integer> ranks) {
        int h = this.highestInStraight(ranks);
        if (h > 0) {
            this.handType = HandType.STRAIGHT;
            this.ranksInHand = this.createRankArrayFromArrayContainingRanks(new ArrayList<>(Arrays.asList(h, h - 1, h - 2, h - 3, h - 4)));
        } else {
            if (this.checkFourOfAKind(ranks)) {
                return;
            }
            this.checkThreesAndPairs(ranks);
        }
    }

    public boolean checkFourOfAKind(ArrayList<Integer> ranks) {
        ArrayList<Integer> excluded = new ArrayList<>();
        int f = this.rankOfFourOfAKind(ranks);
        if (f > 0) {
            excluded.add(f);
            this.handType = HandType.FOUR;
            ArrayList<Integer> handRanks = new ArrayList<>(Arrays.asList(f, f, f, f));
            handRanks.addAll(this.ranksOfHighCards(ranks, 1, excluded));
            this.ranksInHand = this.createRankArrayFromArrayContainingRanks(handRanks);
            return true;
        }
        return false;
    }

    public boolean checkThreesAndPairs(ArrayList<Integer> ranks) {
        ArrayList<Integer> threes = this.rankOfMultipleThreeOfAKind(ranks);
        ArrayList<Integer> pairs = this.rankOfMultiplePairs(ranks);
        ArrayList<Integer> excluded = new ArrayList<>();
        Collections.sort(threes);
        Collections.sort(pairs);
        if (threes.size() > 0) {
            this.checkThreeAndFullHouse(ranks, threes, pairs, excluded);
        } else if (pairs.size() == 1) {
            this.checkPair(ranks, pairs, excluded);
        } else if (pairs.size() > 1) {
            this.checkTwoPairs(ranks, pairs, excluded);
        } else {
            this.handType = HandType.HIGH;
            ArrayList<Integer> handRanks = this.ranksOfHighCards(ranks, 5, excluded);
            this.ranksInHand = this.createRankArrayFromArrayContainingRanks(handRanks);
        }
        return true;
    }

    public void checkThreeAndFullHouse(ArrayList<Integer> ranks, ArrayList<Integer> threes, ArrayList<Integer> pairs, ArrayList<Integer> excluded) {
        if (pairs.size() > 0) {
            this.handType = HandType.FULLHOUSE;
            int t = threes.get(threes.size() - 1);
            int p = pairs.get(pairs.size() - 1);
            ArrayList<Integer> handRanks = new ArrayList<>(Arrays.asList(t, t, t, p, p));
            this.ranksInHand = this.createRankArrayFromArrayContainingRanks(handRanks);
        } else {
            this.handType = HandType.THREE;
            int t = threes.get(threes.size() - 1);
            excluded.add(t);
            ArrayList<Integer> handRanks = new ArrayList<>(Arrays.asList(t, t, t));
            handRanks.addAll(this.ranksOfHighCards(ranks, 2, excluded));
            this.ranksInHand = this.createRankArrayFromArrayContainingRanks(handRanks);
        }
    }

    public void checkPair(ArrayList<Integer> ranks, ArrayList<Integer> pairs, ArrayList<Integer> excluded) {
        this.handType = HandType.PAIR;
        int p = pairs.get(pairs.size() - 1);
        excluded.add(p);
        ArrayList<Integer> handRanks = new ArrayList<>(Arrays.asList(p, p));
        handRanks.addAll(this.ranksOfHighCards(ranks, 3, excluded));
        this.ranksInHand = this.createRankArrayFromArrayContainingRanks(handRanks);
    }

    public void checkTwoPairs(ArrayList<Integer> ranks, ArrayList<Integer> pairs, ArrayList<Integer> excluded) {
        this.handType = HandType.TWOPAIRS;
        int p1 = pairs.get(pairs.size() - 1);
        int p2 = pairs.get(pairs.size() - 2);
        excluded.add(p1);
        excluded.add(p2);
        ArrayList<Integer> handRanks = new ArrayList<>(Arrays.asList(p1, p1, p2, p2));
        handRanks.addAll(this.ranksOfHighCards(ranks, 1, excluded));
        this.ranksInHand = this.createRankArrayFromArrayContainingRanks(handRanks);
    }

    public Card.Suit flushSuit(HashMap<Card.Suit, Integer> suits) {
        for (Card.Suit suit : suits.keySet()) {
            if (suits.get(suit) >= 5) {
                return suit;
            }
        }
        return null;
    }

    public int highestInStraight(ArrayList<Integer> ranks) {
        int highest = -1;
        for (int i = ranks.size() - 1; i >= 4; i--) {
            for (int j = 0; j < 4; j++) {
                if (ranks.get(i - j) == 0) {
                    highest = -1;
                    break;
                } else {
                    highest = i + 1;
                }
            }
            if (highest >= 0) {
                return highest;
            }
        }
        return -1;
    }

    public int rankOfFourOfAKind(ArrayList<Integer> ranks) {
        for (int i = 1; i < 14; i++) {
            if (ranks.get(i) == 4) {
                return i + 1;
            }
        }
        return -1;
    }

    public ArrayList<Integer> rankOfMultipleThreeOfAKind(ArrayList<Integer> ranks) {
        ArrayList<Integer> ranksOfToaK = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            if (ranks.get(i) == 3) {
                ranksOfToaK.add(i + 1);
            }
        }
        return ranksOfToaK;
    }

    public ArrayList<Integer> rankOfMultiplePairs(ArrayList<Integer> ranks) {
        ArrayList<Integer> ranksOfPairs = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            if (ranks.get(i) == 2) {
                ranksOfPairs.add(i + 1);
            }
        }
        return ranksOfPairs;
    }

    public ArrayList<Integer> ranksOfHighCards(ArrayList<Integer> ranks, int n, ArrayList<Integer> excludedRanks) {
        ArrayList<Integer> highRanks = new ArrayList<>();
        for (int i = ranks.size() - 1; i >= 1; i--) {
            if (ranks.get(i) != 0 && !excludedRanks.contains(ranks.get(i))) {
                highRanks.add(ranks.get(i));
            }
            if (highRanks.size() >= n) {
                break;
            }
        }
        return highRanks;
    }

    public int highestInStraightFlush(Card.Suit flushSuit, ArrayList<Integer> ranks, ArrayList<Card> cards) {
        ArrayList<Integer> ranksWithFlushSuit = this.listRanksWithACertainSuit(flushSuit, cards);
        int highest = -1;
        for (int i = ranks.size() - 1; i >= 4; i--) {
            for (int j = 0; j < 4; j++) {
                if (ranks.get(i - j) == 0 || !ranksWithFlushSuit.contains(i - j)) {
                    highest = -1;
                    break;
                } else {
                    highest = i + 1;
                }
            }
            if (highest >= 0) {
                return highest;
            }
        }
        return -1;
    }

    public ArrayList<Integer> listRanksWithACertainSuit(Card.Suit suit, ArrayList<Card> cards) {
        ArrayList<Integer> ranksWithSuit = new ArrayList<>();
        for (Card card : cards) {
            if (card.getSuit() == suit) {
                ranksWithSuit.add(card.getRank());
            }
        }
        return ranksWithSuit;
    }

    public ArrayList<Integer> createRankArrayFromArrayContainingRanks(ArrayList<Integer> ranks) {
        ArrayList<Integer> rankArray = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            rankArray.add(0);
        }
        for (int i : ranks) {
            rankArray.set(i - 1, rankArray.get(i - 1) + 1);
        }
        return rankArray;
    }

    public HashMap<Card.Suit, Integer> suitMapFromCardArray(ArrayList<Card> cards) {
        HashMap<Card.Suit, Integer> suits = new HashMap<>();
        suits.put(Card.Suit.HEARTS, 0);
        suits.put(Card.Suit.CLUBS, 0);
        suits.put(Card.Suit.SPADES, 0);
        suits.put(Card.Suit.DIAMONDS, 0);
        for (Card card : cards) {
            suits.put(card.getSuit(), suits.get(card.getSuit()) + 1);
        }
        return suits;
    }

    public ArrayList<Integer> rankArrayFromCardArray(ArrayList<Card> cards) {
        ArrayList<Integer> ranks = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            ranks.add(0);
        }
        for (Card card : cards) {
            ranks.set(card.getRank() - 1, ranks.get(card.getRank() - 1) + 1);
        }
        ranks.set(0, ranks.get(13));
        return ranks;
    }

    /**
     * @return the handType
     */
    public HandType getHandType() {
        return handType;
    }

    /**
     * @return the ranksInHand
     */
    public ArrayList<Integer> getRanksInHand() {
        return ranksInHand;
    }

}
