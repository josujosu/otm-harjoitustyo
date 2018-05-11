/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import texasholdem.domain.Card;

/**
 *
 * @author josujosu
 */
public class CardTest {
    
    private Card card;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.card = new Card(Card.Suit.HEARTS, 5);
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
    public void constructorWorksAsIntended() {
        assertEquals("5"+"\u2665", this.card.toString());
    }
    
    @Test
    public void suitAsStringReturnsRightStringWhenSuitIsClubs() {
        assertEquals("\u2663", this.card.suitAsString(Card.Suit.CLUBS));
    }
    @Test
    public void suitAsStringReturnsRightStringWhenSuitIsHearts() {
        assertEquals("\u2665", this.card.suitAsString(Card.Suit.HEARTS));
    }
    
    @Test
    public void suitAsStringReturnsRightStringWhenSuitIsDiamonds() {
        assertEquals("\u2666", this.card.suitAsString(Card.Suit.DIAMONDS));
    }
    
    @Test
    public void suitAsStringReturnsRightStringWhenSuitIsSpades() {
        assertEquals("\u2660", this.card.suitAsString(Card.Suit.SPADES));
    }
    
    @Test
    public void rankAsStringReturnsRightWhenRankValueIs11() {
        assertEquals("J", this.card.rankAsString(11));
    }
    
    @Test
    public void rankAsStringReturnsRightWhenRankValueIs12() {
        assertEquals("Q", this.card.rankAsString(12));
    }
    
    @Test
    public void rankAsStringReturnsRightWhenRankValueIs13() {
        assertEquals("K", this.card.rankAsString(13));
    }
    
    @Test
    public void rankAsStringReturnsRightWhenRankValueIs14() {
        assertEquals("A", this.card.rankAsString(14));
    }
    
    @Test
    public void rankAsStringReturnsRightWhenRankValueIsUnder10() {
        assertEquals("9", this.card.rankAsString(9));
    }
}
