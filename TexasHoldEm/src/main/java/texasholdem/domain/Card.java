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
public class Card {
    
    public enum Suit {
        DIAMONDS, HEARTS, CLUBS, SPADES
    }    
    
    private Suit suit;
    private int rank; // 2 is smallest, 14 is ace
    
    public Card(Suit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    public String suitAsString(Card.Suit suit) {
        switch (suit) {
            case CLUBS:
                return "\u2663";
            case HEARTS:
                return "\u2665";
            case DIAMONDS:
                return "\u2666";
            case SPADES:
                return "\u2660";
            default:
                return null;
        }
    }
    
    public String rankAsString(int rank) {
        switch (rank) {
            case 11: 
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            case 14:
                return "A";
            default:
                return Integer.toString(rank);
        }
    }
    
    @Override
    public String toString() {
        return this.rankAsString(this.rank) + this.suitAsString(this.suit);
    }
    

    /**
     * @return the suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * @param suit the suit to set
     */
    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    /**
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(int rank) {
        this.rank = rank;
    }
    
    
    
    
    
}
