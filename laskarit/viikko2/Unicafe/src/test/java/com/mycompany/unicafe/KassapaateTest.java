/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author josujosu
 */
public class KassapaateTest {
    
    Kassapaate paate;
    Maksukortti kortti;
    
    public KassapaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(1000);
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
    public void luodunKassapaatteenRahamaaraOikea(){
        assertTrue(paate.kassassaRahaa() == 100000);
    }
    
    @Test
    public void luodunKassapaatteenMyytyjenLounaidenMaaraOikea(){
        int lounaita = paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty();
        assertTrue(lounaita == 0);
    }
    
    @Test
    public void josMaksuRiittavaEdullisenLuonaanOstoKasvattaaRahamaaraaOikein(){
        paate.syoEdullisesti(1000);
        assertTrue(paate.kassassaRahaa() == 100240);
    }
    
    @Test
    public void josMaksuRiittavaMaukkaanLounaanOstoKasvattaaRahamaaraaOikein(){
        paate.syoMaukkaasti(1000);
        assertTrue(paate.kassassaRahaa() == 100400);
    }
    
    @Test
    public void edullisenLounaanVaihtorahanMaaraOikea(){
        assertTrue(paate.syoEdullisesti(1000) == 760);
    }
    
    @Test
    public void maukkaanLounaanVaihtorahanMaaraOikea(){
        assertTrue(paate.syoMaukkaasti(1000) == 600);
    }
    
    @Test
    public void edullisenLounaanOstaminenLisaaLounaidenMaaraa(){
        paate.syoEdullisesti(1000);
        assertTrue(paate.edullisiaLounaitaMyyty() == 1);
    }
    
    @Test
    public void maukkaanLounaanOstaminenLisaaLounaidenMaaraa(){
        paate.syoMaukkaasti(1000);
        assertTrue(paate.maukkaitaLounaitaMyyty() == 1);
    }
    
    @Test
    public void riittamatonEdullisenLounaanMaksuEiKasvataRahamaaraa(){
        paate.syoEdullisesti(10);
        assertTrue(paate.kassassaRahaa() == 100000);
    }
    
    @Test
    public void riittamatonMaukkaanLounaanMaksuEiKasvataRahamaaraa(){
        paate.syoMaukkaasti(10);
        assertTrue(paate.kassassaRahaa() == 100000);
    }
    
    @Test
    public void riittamatonEdullisenLounaanMaksuEiKasvataLounaidenMaaraa(){
        paate.syoEdullisesti(10);
        assertTrue(paate.edullisiaLounaitaMyyty() == 0);        
    }
    
    @Test
    public void riittamatonMaukkaanLounaanMaksuEiKasvataLounaidenMaaraa(){
        paate.syoMaukkaasti(10);
        assertTrue(paate.maukkaitaLounaitaMyyty() == 0);        
    }
    
    @Test
    public void riittamatonEdullisenLounaanMaksuPalauttaaKaikkiRahat(){
        assertTrue(paate.syoEdullisesti(10) == 10);
    }
    
    @Test
    public void riittamatonMaukkaanLounaanMaksuPalauttaaKaikkiRahat(){
        assertTrue(paate.syoMaukkaasti(10) == 10);
    }
    
    @Test
    public void riittavaEdullisenLounaanMaksuVeloittaaOikeanSummanKortilta(){
        paate.syoEdullisesti(kortti);
        assertEquals("saldo: 7.60", kortti.toString());
    }
    
    @Test
    public void riittavaMaukkaanLounaanMaksuVeloittaaOikeanSummanKortilta(){
        paate.syoMaukkaasti(kortti);
        assertEquals("saldo: 6.00", kortti.toString());
    }
    
    @Test
    public void riittavaEdullisenLounaanMaksuKortillaPalauttaaMetodissaTrue(){
        assertTrue(paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void riittavaMaukkaanLounaanMaksuKortillaPalauttaaMetodissaTrue(){
        assertTrue(paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void riittavaEdullisenLounaanMaksuKortillaLisaaLounaidenMaaraa(){
        paate.syoEdullisesti(kortti);
        assertTrue(paate.edullisiaLounaitaMyyty() == 1);
    }
    
    @Test
    public void riittavaMaukkaanLounaanMaksuKortillaLisaaLounaidenMaaraa(){
        paate.syoMaukkaasti(kortti);
        assertTrue(paate.maukkaitaLounaitaMyyty() == 1);
    }
    
    @Test
    public void riittamatonEdullisenLounaanMaksuEiVahennaKortinSaldoa(){
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        // Rahaa jäljellä 2.00
        paate.syoEdullisesti(kortti);
        assertEquals("saldo: 2.00", kortti.toString());
    }
    
    @Test
    public void riittamatonMaukkaanLounaanMaksuEiVahennaKortinSaldoa(){
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        // Rahaa jäljellä 2.00
        paate.syoMaukkaasti(kortti);
        assertEquals("saldo: 2.00", kortti.toString());
    }
    
    @Test
    public void riittamatonEdullisenLounaanMaksuKortillaEiLisaaLounaita(){
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        // Rahaa jäljellä 2.00
        paate.syoEdullisesti(kortti);
        assertTrue(paate.edullisiaLounaitaMyyty() == 0);
    }
    
    @Test
    public void riittamatonMaukkaanLounaanMaksuKortillaEiLisaaLounaita(){
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        // Rahaa jäljellä 2.00, maukkaita myyty 2
        paate.syoMaukkaasti(kortti);
        assertTrue(paate.maukkaitaLounaitaMyyty() == 2);
    }
    
    @Test
    public void riittamatonEdullisenLounaanMaksuKortillaPalauttaaFalse(){
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        // Rahaa jäljellä 2.00
        assertFalse(paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void riittamatonMaukkaanLounaanMaksuKortillaPalauttaaFalse(){
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        // Rahaa jäljellä 2.00
        assertFalse(paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void kassassaOlevaRahamaaraEiMuutuOstaessaEdullisenLounaanKortilla(){
        paate.syoEdullisesti(kortti);
        assertTrue(paate.kassassaRahaa() == 100000);
    }
    
    @Test
    public void kassassaOlevaRahamaaraEiMuutuOstaessaMaukkaanLounaanKortilla(){
        paate.syoMaukkaasti(kortti);
        assertTrue(paate.kassassaRahaa() == 100000);
    }
    
    @Test
    public void ladattaessaRahaaKortilleKortinSaldoKasvaaOikein(){
        paate.lataaRahaaKortille(kortti, 1000);
        assertEquals("saldo: 20.00", kortti.toString());
    }
    
    @Test
    public void negatiivisenSummanLataaminenKortilleEiKasvataSaldoa(){
        paate.lataaRahaaKortille(kortti, -10);
        assertEquals("saldo: 10.00", kortti.toString());
    }
    
    @Test
    public void ladattaessaRahaaKortilleKassanRahamaaraKasvaaOikein(){
        paate.lataaRahaaKortille(kortti, 1000);
        assertTrue(paate.kassassaRahaa() == 101000);
    }
    
    @Test
    public void negatiivisenSummanLataaminenKortilleEiKasvataKassanRahamaaraa(){
        paate.lataaRahaaKortille(kortti, -10);
        assertTrue(paate.kassassaRahaa() == 100000);
    }
}
