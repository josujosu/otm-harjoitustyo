package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein(){
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein(){
        kortti.lataaRahaa(2);
        assertEquals("saldo: 0.12", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikeinJosRahaaTarpeeksi(){
        kortti.otaRahaa(5);
        assertEquals("saldo: 0.05", kortti.toString());
    }
    
    @Test
    public void saldoEiVaheneJosRahaaEiOleTarpeeksi(){
        kortti.otaRahaa(11);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosRahatRiittavat(){
        assertTrue(kortti.otaRahaa(9));        
    }
    
    @Test
    public void otaRahaaPalauttaaFalseJosRahatEivatRiita(){
        assertFalse(kortti.otaRahaa(11));
    }
    
    @Test
    public void saldoMetodiPalauttaaOikeanSaldon(){
        assertTrue(kortti.saldo() == 10);
    }
}
