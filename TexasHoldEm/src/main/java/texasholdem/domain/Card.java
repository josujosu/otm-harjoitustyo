/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

/**
 * A Class representing a card in Texas Hold'Em
 * @author josujosu
 */
public class Card {
    
    /**
     * Enumerator that represents the different suits found in Texas Hold'Em
     */
    public enum Suit {
        DIAMONDS, HEARTS, CLUBS, SPADES
    }    
    
    private Suit suit;
    private int rank; // 2 is smallest, 14 is ace
    
    /**
     * Constructor
     * @param suit Suit of the card
     * @param rank Value of the rank of the card as an integer
     */
    public Card(Suit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    /**
     * A method for converting a Card.Suit into a String
     * @param suit The suit
     * @return The unicode for a symbol of the corresponding suit as a String
     */
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
    
    /**
     * A method for converting a card rank from an integer to String
     * @param rank The value of the rank as integer
     * @return J if rank value = 11, Q if rank value = 12, K if rank value = 13
     * A if rank value = 14 or the value as String if none of the former
     */
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
