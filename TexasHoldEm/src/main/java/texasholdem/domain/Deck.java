/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

/**
 * A class depicting a deck of cards
 * @author josujosu
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Deck {
    
    private ArrayList<Card> cards;
    
    /**
     * Constructor
     * @param params Lists of cards that make up the deck
     */
    public Deck(List<Card>...params) {
        this.cards = new ArrayList<>();
        for (List<Card> newCards : params) {
            this.cards.addAll(newCards);
        }
    }
    
    /**
     * Constructor that makes the deck a basic 52 card deck
     */
    public Deck() {
        this.cards = this.create52CardDeck();
    }
    
    
    /**
     * A method that creates an ArrayList containing all the 52 playing cards of
     * a standard deck in a random order. The cards are listed as Card objects.
     * @return An ArrayList containing all of the 52 cards as Card objects
     */
    public ArrayList<Card> create52CardDeck() {
        
        ArrayList<Card> newDeck = new ArrayList<>();
        List<Card.Suit> suits = new ArrayList<>();
        
        suits.add(Card.Suit.HEARTS);
        suits.add(Card.Suit.DIAMONDS);
        suits.add(Card.Suit.CLUBS);
        suits.add(Card.Suit.SPADES);
        
        for (int i = 0; i < 4; i++) {
            Card.Suit suit = suits.get(i);
            for (int rank = 2; rank < 15; rank++) {
                newDeck.add(new Card(suit, rank));
            }
        }
        
        Collections.shuffle(newDeck);
        return newDeck;
    }
    
    /**
     * A method that depicts the act of taking cards from a deck. The cards taken are 
     * removed from the deck.
     * @param n The amount of cards that will be taken from the deck
     * @return An ArrayList containing all the taken cards as Card objects
     */
    public ArrayList<Card> takeCards(int n) {
        
        ArrayList<Card> taken = new ArrayList<>();
        
        if (n <= this.cards.size()) {
            
            for (int i = 0; i < n; i++) {
                taken.add(this.cards.get(0));
                this.cards.remove(0);
            }
        
        }
        
        return taken;
    }
    
    /**
     * A method that adds cards to the deck
     * @param cards The cards to be added to the deck as an ArrayList of Card
     * objects
     */
    public void addCards(ArrayList<Card> cards) {
        this.cards.addAll(cards);
    }
    
    @Override
    public String toString() {
        
        String string = "";
        
        for (Card card : this.cards) {
            string += card + " ";
        }
        
        return string;
    }

    /**
     * @return the cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * @param cards the cards to set
     */
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
    
    
    
}
