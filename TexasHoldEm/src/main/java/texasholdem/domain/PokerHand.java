/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

/**
 * A class for depicting poker hands
 * @author josujosu
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;

public class PokerHand {

    /**
     * Enumerator that defines the type of the hand
     */
    public enum HandType {
        ROYALFLUSH, STRAIGHTFLUSH, FOUR, FULLHOUSE, FLUSH, STRAIGHT, THREE,
        TWOPAIRS, PAIR, HIGH
    }

    private HandType handType;
    private ArrayList<Integer> ranksInHand;

    /**
     * Constructor
     * @param deck The deck of cards from which the deck will be made. (Only really works
     * for decks that have 7 or less cards) 
     */
    public PokerHand(Deck deck) {
        
        this.create5CardHandFromADeck(deck);
    
    }

    /**
     * A method for defining the parameters that define the poker hand encapsulated 
     * in the PokerHand object in question
     * @param deck The deck of cards from which the parameters will be defined
     */
    final public void create5CardHandFromADeck(Deck deck) {
        
        ArrayList<Card> cards = deck.getCards();
        ArrayList<Integer> ranks = this.rankArrayFromCardArray(cards);
        Card.Suit flushSuit = this.flushSuit(cards);
        
        if (flushSuit != null) {            
            this.checkFlushes(flushSuit, cards, ranks);        
        } else {            
            this.checkNonFlushes(cards, ranks);        
        }        
    }

    /**
     * A method for checking which type of flush the poker hand is
     * @param flushSuit The suit of the flush
     * @param cards The cards from which the hand will be defined from
     * @param ranks An array in which the index corresponds to the rank with the value
     * of the index+1, and the value corresponds to the number of cards in the hand that
     * have the rank in question
     */
    public void checkFlushes(Card.Suit flushSuit, ArrayList<Card> cards, ArrayList<Integer> ranks) {        
        
        int h = this.highestInStraight(flushSuit, cards);
        
        if (h < 0) {
            
            this.handType = HandType.FLUSH;
            this.ranksInHand = this.rankArrayFromArrayContainingRanks(this.ranksOfHighCards(ranks, 5, new ArrayList<>()));
        
        } else if (h == 14) {
            
            this.handType = HandType.ROYALFLUSH;
            this.ranksInHand = this.rankArrayFromArrayContainingRanks(new ArrayList<>(Arrays.asList(14, 13, 12, 11, 10)));
        
        } else {
            
            this.handType = HandType.STRAIGHTFLUSH;
            this.ranksInHand = this.rankArrayFromArrayContainingRanks(new ArrayList<>(Arrays.asList(h, h - 1, h - 2, h - 3, h - 4)));
        
        }
    }

    /**
     * A method for checking which type of non-flush-hand the poker hand is
     * @param cards The cards from which the hand will be defined from
     * @param ranks An array in which the index corresponds to the rank with the value
     * of the index+1, and the value corresponds to the number of cards in the hand that
     * have the rank in question
     */
    public void checkNonFlushes(ArrayList<Card> cards, ArrayList<Integer> ranks) {
        
        int h = this.highestInStraight(null, cards);
        
        if (h > 0) {
            
            this.handType = HandType.STRAIGHT;
            this.ranksInHand = this.rankArrayFromArrayContainingRanks(new ArrayList<>(Arrays.asList(h, h - 1, h - 2, h - 3, h - 4)));
        
        } else {
        
            this.checkMultipleCardHands(ranks);
        
        }
    }
    
    /**
     * A method for checking which type of poker hand containing multiple instances 
     * of the same card the poker hand is
     * @param ranks An array in which the index corresponds to the rank with the value
     * of the index+1, and the value corresponds to the number of cards in the hand that
     * have the rank in question
     */
    public void checkMultipleCardHands(ArrayList<Integer> ranks) {
        
        if (ranks.contains(4)) {
            
            this.handType = HandType.FOUR;
            this.makeMultipleCardHand(ranks, 4);
        
        } else if (ranks.contains(3) && ranks.contains(2)) {
            
            this.handType = HandType.FULLHOUSE;
            this.makeMultipleCardHand(ranks, 3, 2);
        
        } else if (ranks.contains(3)) {
            
            this.handType = HandType.THREE;
            this.makeMultipleCardHand(ranks, 3);
        
        } else if (ranks.contains(2)) {
            
            this.makeMultipleCardHand(ranks, 2);
            this.checkPairType();
        
        } else {
            
            this.handType = HandType.HIGH;
            this.makeMultipleCardHand(ranks, 1);
        
        }
    }
    
    /**
     * Checks if the PokerHand in question has two pairs or one pair.
     */
    public void checkPairType() {
        int pairs = 0;
        
        for (int i = 1; i < this.ranksInHand.size(); i++) {
            if (this.ranksInHand.get(i) == 2) {
                pairs++;
            }
        }
        
        if (pairs >= 2) {
            this.handType = HandType.TWOPAIRS;
        } else {
            this.handType = HandType.PAIR;
        }
        
    }
    
    /**
     * Makes the PokerHand in question into a hand containing a certain amount of
     * instances of the same card
     * @param ranks An array in which the index corresponds to the rank with the value
     * of the index+1, and the value corresponds to the number of cards in the hand that
     * have the rank in question* @param ranks
     * @param n The number of instances a card should have
     */
    public void makeMultipleCardHand(ArrayList<Integer> ranks, int...n) {
        
        ArrayList<Integer> excluded = new ArrayList<>();
        ArrayList<Integer> handRanks = new ArrayList<>();
        int cardsLeft = 5;
        
        for (int i = 0; i < n.length; i++) {
            ArrayList<Integer> nRanks = this.ranksOfNCards(ranks, n[i]);
            
            for (int j = 0; j < nRanks.size(); j++) {
                
                if (cardsLeft - n[i] < 0) {
                    break;
                }                
                
                for (int k = 0; k < n[i]; k++) {
                    handRanks.add(nRanks.get(nRanks.size() - 1 - j));  
                    excluded.add(nRanks.get(nRanks.size() - 1 - j));
                }
                
                cardsLeft -= n[i];
            
            }
        }
        
        handRanks.addAll(this.ranksOfHighCards(ranks, cardsLeft, excluded));
        this.ranksInHand = this.rankArrayFromArrayContainingRanks(handRanks);
    }

    /**
     * A method for defining the suit of a possible flush contained in a list of cards
     * @param cards The list of cards
     * @return The suit of the flush if the cards contain a flush, null otherwise
     */
    public Card.Suit flushSuit(ArrayList<Card> cards) {
        
        HashMap<Card.Suit, Integer> suits = this.suitMapFromCardArray(cards);
        
        for (Card.Suit suit : suits.keySet()) {            
            if (suits.get(suit) >= 5) {
                return suit;
            }        
        }
        
        return null;
    }

    /**
     * A method for acquiring the rank of similiarily ranked cards that appear a certain
     * amount of instances
     * @param ranks An array in which the index corresponds to the rank with the value
     * of the index+1, and the value corresponds to the number of cards in the hand that
     * have the rank in question
     * @param n The number of instances a rank should appear
     * @return The ranks as an ArrayList
     */
    public ArrayList<Integer> ranksOfNCards(ArrayList<Integer> ranks, int n) {
        
        ArrayList<Integer> ranksOfN = new ArrayList<>();
        
        for (int i = 1; i < 14; i++) {
            if (ranks.get(i) == n) {
                ranksOfN.add(i + 1);
            }
        }
        
        Collections.sort(ranksOfN);
        return ranksOfN;
    }

    
    /**
     * A method for acquiring the ranks of the possible high cards
     * @param ranks An array in which the index corresponds to the rank with the value
     * of the index+1, and the value corresponds to the number of cards in the hand that
     * have the rank in question
     * @param n Number of high cards needed
     * @param excludedRanks Ranks that should be excluded from the possible high cards
     * @return The possible high cards as an ArrayList
     */
    public ArrayList<Integer> ranksOfHighCards(ArrayList<Integer> ranks, int n, ArrayList<Integer> excludedRanks) {
        
        ArrayList<Integer> highRanks = new ArrayList<>();
        
        for (int i = ranks.size() - 1; i >= 1; i--) {
            
            if ((ranks.get(i) != 0) && (!excludedRanks.contains(i + 1))) {
                highRanks.add(i + 1);
            }
            
            if (highRanks.size() >= n) {
                break;
            }
        
        }
        
        return highRanks;
    }

    /**
     * A method for acquiring the highest rank in a straight
     * @param flushSuit The suit of the straight flush, if only checking for straight
     * suit shuold be null
     * @param cards ArrayList containign all the cards from which the straight will be 
     * checked
     * @return The highest rank if the cards contain a straight, otherwise -1
     */
    public int highestInStraight(Card.Suit flushSuit, ArrayList<Card> cards) {
        
        ArrayList<Integer> ranks = this.rankArrayFromCardArray(cards);
        ArrayList<Integer> ranksWithFlushSuit = this.listRanksWithACertainSuit(flushSuit, cards);
        int highest = -1;
        
        for (int i = ranks.size() - 1; i >= 4; i--) {
            
            for (int j = 0; j < 5; j++) {
                
                if (ranks.get(i - j) == 0 || !ranksWithFlushSuit.contains(i - j + 1)) {
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

    /**
     * A method for listing ranks of cards that have a certain suit
     * @param suit The suit
     * @param cards The list of cards
     * @return ArrayList containing all of the ranks
     */
    public ArrayList<Integer> listRanksWithACertainSuit(Card.Suit suit, ArrayList<Card> cards) {
        
        ArrayList<Integer> ranksWithSuit = new ArrayList<>();
        
        for (Card card : cards) {
            
            if (card.getSuit() == suit || suit == null) {
                ranksWithSuit.add(card.getRank());                
                if (card.getRank() == 14) {
                    ranksWithSuit.add(1);
                }            
            }
        
        }
        
        return ranksWithSuit;
    }

    /**
     * Creates a specially formatted rank array, where indeces correspond to ranks and the values
     * correspond to the number instances those ranks appear, from an array that
     * contains the values of ranks
     * @param ranks ArrayList that contains the values of ranks
     * @return The specially formatted rank array
     */
    public ArrayList<Integer> rankArrayFromArrayContainingRanks(ArrayList<Integer> ranks) {
        
        ArrayList<Integer> rankArray = this.emptyRankArray();
        
        for (int i : ranks) {
            rankArray.set(i - 1, rankArray.get(i - 1) + 1);
        }
        
        rankArray.set(0, rankArray.get(13));        
        return rankArray;
    }
    
    /**
     * Creates a specially formatted rank array, where indeces correspond to ranks and the values
     * correspond to the number instances those ranks appear, from an array that
     * contains cards
     * @param cards ArrayList that contains the cards
     * @return The specially formatted rank array
     */
    public ArrayList<Integer> rankArrayFromCardArray(ArrayList<Card> cards) {
        
        ArrayList<Integer> ranks = this.emptyRankArray();
        
        for (Card card : cards) {
            ranks.set(card.getRank() - 1, ranks.get(card.getRank() - 1) + 1);
        }
        
        ranks.set(0, ranks.get(13));
        return ranks;
    }

    /**
     * A method for creating a HashMap in which the keys correspond to card suits
     * and the values correspond to the number of instences the suits appear in an
     * array of cards
     * @param cards ArrayList containing cards
     * @return The HashMap
     */
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

    
    /**
     * Creates an empty rank array where indeces correspond to ranks and the values
     * correspond to the number of instances those ranks appear
     * @return The rank array
     */
    public ArrayList<Integer> emptyRankArray() {
        
        ArrayList<Integer> empty = new ArrayList<>();
        
        for (int i = 0; i < 14; i++) {
            empty.add(0);
        }
        
        return empty;
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
