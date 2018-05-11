# Testausdokumentti

Ohjelmaa on testattu sekä automaattisilla JUnit-testeillä, sekä manuaalisilla järjestelmätason testeillä.

## JUnit-testit

Automatisoidut JUnit-testit testaavat sovelluslogiikkaa, sekä tietokannan käsittelystä vastaavia luokkia. Testiluokat ovat seuraavat:

- [CardTest.java](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/test/java/domain/CardTest.java)
- [ComputerAITest.java](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/test/java/domain/ComputerAITest.java)
- [DeckTest.java](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/test/java/domain/DeckTest.java)
- [GameTest.java](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/test/java/domain/GameTest.java)
- [HandComparatorTest.java](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/test/java/domain/HandComparatorTest.java)
- [PlayerTest.java](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/test/java/domain/PlayerTest.java)
- [PokerHandTest.java](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/test/java/domain/PokerHandTest.java)
- [UIOperationsTest.java](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/test/java/domain/UIOperationsTest.java)
- [UserTest.java](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/test/java/domain/UserTest.java)
- [ResultDaoTest.java](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/test/java/database/ResultDaoTest.java)
- [UserDaoTest.java](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/test/java/database/UserDaoTest.java)

Jokainen testiluokka keskittyy pääosin tietyn luokan toimintojen testaamiseen, mutta testaa myös toimintoja, joihin liittyy eri luokkien välisiä toimintoja.  

### DAO-luokat

DAO-luokkien ja niiden toiminnallisuuksia käyttävien luokkien testit on toteutettu muodostamalla jokaista erillistä testiä varten väliaikainen testitietokanta. Tietokanta rakennetaan ennen testiä, muokataan testissä ja poistetaan testin jälkeen.

## Järjestelmätason testit

### Konfigurointi

Järjestelmää on testattu Linux-ympäristössä noudattaen [käyttöohjeen](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md) mukaisia ohjetta. Sovellusta on myös testattu tapauksessa, jossa *THE.db* tietokanta tiedostoa ei löydy sovelluksen juurikansiosta, jolloin sovellus on luonut sen itse.

### Toiminnalisuudet

Vaatimusmäärittelyssä määritellyt toiminnallisuudet on testattu. Sovellusta on myös testattu vastaamalla sen tekemiin komentopyyntöihin epäpätevillä komennoilla.


