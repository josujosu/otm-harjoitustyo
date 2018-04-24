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
import texasholdem.domain.HandComparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author josujosu
 */
public class HandComparatorTest {
    
    private HandComparator comp;
    private ArrayList<Integer> ranks;
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.comp = new HandComparator();
        this.ranks = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            this.ranks.add(0);
        }
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
    public void getHighestRankFromNumberOfCardsReturnsRightValueWhenNothingIsExcluded() {
        this.ranks.set(13, 3);
        this.ranks.set(4, 5);
        this.ranks.set(10, 2);
        assertEquals(10,this.comp.getHighestRankFromNumberOfCards(ranks, 2, new ArrayList<>()));
    }
}
