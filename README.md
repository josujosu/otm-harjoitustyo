# TexasHold'Em

Sovelluksen avulla voi pelata *Texas Hold 'Em*-korttipeliä tekoälyä vastaan. Käyttäjä voi luoda sovellukseen tunnuksen, jonka kautta voi tarkastella erinäisiä pelattuihin peleihin liittyviä tilastoja.

## Julkaisut

[Ensimmäinen julkaisu](https://github.com/josujosu/otm-harjoitustyo/releases/tag/viikko5)

## Dokumentaatio

[Työaikakirjanpito](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Vaatimusmäärittely](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuuri](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

## Komentorivitoiminnot

### Ohjelman ajaminen

Ohjelman voi ajaa komentoriviltä komennolla

    mvn compile exec:java -Dexec.mainClass=texasholdem.ui.Main

### Testaus

Testit voi suorittaa komennolla

    mvn test

Testikattavuusraportin voi luoda komennolla

    mvn jacoco:report

Testikattavuusraporttia voi tarkastella avaamalla tiedoston *target/site/jacoco/index.html* selaimella

### Checkstyle

Tiedostoon checkstyle.xml määritetyt tarkistukset voi suorittaa komennolla

    mvn jxr:jxr checkstyle:checkstyle
    
Virheilmoitukset voi tarkastaa avaamalla tiedoston *target/site/checkstyle.html* selaimella

