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
import java.util.List;
import java.util.Collections;

public class Deck {
    
    private ArrayList<Card> cards;
    
    public Deck(List<Card>...params) {
        this.cards = new ArrayList<>();
        for (List<Card> newCards : params) {
            this.cards.addAll(newCards);
        }
    }
    
    public Deck() {
        this.cards = this.create52CardDeck();
    }
    
    
    final ArrayList<Card> create52CardDeck() {
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
        newDeck = this.shuffleDeck(newDeck);
        return newDeck;
    }
    
    
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
    
    public void addCards(ArrayList<Card> cards) {
        this.cards.addAll(cards);
    }
    
    public ArrayList<Card> shuffleDeck(ArrayList<Card> cards) {
        Collections.shuffle(cards);
        return cards;
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