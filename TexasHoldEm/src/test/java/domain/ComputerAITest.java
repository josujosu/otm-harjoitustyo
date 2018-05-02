/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import texasholdem.domain.Card;
import texasholdem.domain.ComputerAI;
import texasholdem.domain.Deck;

/**
 *
 * @author josujosu
 */
public class ComputerAITest {
    
    private ComputerAI ai;
    private ArrayList<Card> cards;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.ai = new ComputerAI();
        this.cards = new ArrayList<>();
        this.cards.add(new Card(Card.Suit.CLUBS, 1));
        this.cards.add(new Card(Card.Suit.CLUBS, 4));
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void getRoundNumberFromCardsReturnsCorrectRoundWhenThereAreTwoCards() {
        assertEquals(1,this.ai.getRoundNumberFromNumberOfCards(new Deck(this.cards)));
    }
}
