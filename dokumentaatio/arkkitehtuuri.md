# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattaa seuraavanlaista kerrosarkkitehtuuria:

![Kuva ohjelman pakkausrrakenteesta](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkaus.png)

Pakkaus *texasholdem.ui* sisältää tekstikäyttöliittymän, *texasholdem.domain* sovelluslogiikan määrittävät luokat ja *tekasholdem.database* sekä *texasholdem.database.collector* tietokannan ylläpitämiseen tarvittavat luokat.

## Käyttöiittymä

Sovelluksen tekstikäyttöliittymä koostuu neljästä erillisestä näykmäoliosta:

- StartTextScene, eli aloitus valikko

- [CreateUserTextScene](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/ui/text/CreateUserTextScene.java), eli käyttäjien hallinnoimisen valikko

- UserStatsTextScene, eli käyttäjien tilastojen tarkastelemisen valkko

- PlayingTextScene, eli itse pelaamisen näkymä

Jokainen näistä näkymäolioista toteuttaa *TextScene*-rajapinnan, joka mahdollistaa näkymien saumattoman vaihtamisen. Näkymien vaihtaminen perustuu *TextScene*:n määrittämään *run()*-metodiin, joka tekstikäyttöliittymän ulkoasun ja toiminnallisuuden määrittämisen lisäksi palauttaa *return*-arvonaan seuraavan näkymäolion. *TextUI*-luokka ajaa näiden näkymäolioiden *run()*-metodeja, ja saatuaan niiden palautusarvona seuraavan näkymäolion, aloittaa *TextUI* tämän olion *run()*-metodin ajamisen. Tämä jatkuu, kunnes *TextUI* saa palautusarvona null, jolloin ohjelma lopetetaan.

## Sovelluslogiikka

Seuraava luokka/pakkauskaavio kuvaa jokaisen sovelluksessa esiintyvän, mukaanlukematta käyttöliittymän muodostamiseen tarkoiteetujen, luokan suhdetta:

![Kuva ohjelman arkkitehtuurista](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/arkkitehtuuri_uusin.png)

### Pelin kulku

Pelin kulku perustuu *Game*-luokan ja käyttöliittymän väliseen yhteistyöhön. Käyttöliittymä ottaa vastaan käyttäjän komentoja, ja välittää niiden kautta tehtäviä *Game*-oliolle, joka ylläpitää ja on perillä pelin tapahtumista. Pelin ylläpitäminen tapahtuu pääosin *Player*-olioiden ja *Deck*-olioiden välityksellä. *Player* pitää sisällään tiedot tietystä pelin pelaajasta ja *Deck* sisältää listan tiettyyn pakkaan sidotuista korteista, tai *Card*-olioista.

Riippuen kutsutusta metodista, *Player*-olio voi tehdä pokeriin liittyviä toimintoja (kuten *fold* ja *raise*) vastaamalla annettuun komentoon, tai valitsemalla sattumanvaraisesti *ComputerAI*-luokan määrittelemien metodien avulla. Tehdyn toiminnon kapseloi *Action*-oliot.

Pelaajien käsien määrittely tapahtuu *HandComparator*-luokan ja *PokerHand*-olioiden avulla. *HandComparator* määrittelee metodit, joiden avulla voi verrata *PokerHand*-olioiden *Deck*-olioista määrittelemiä pokerikäsiä. Pelaajien toimintojen jälkeen *Game*-olio käyttää kyseistä luokka parhaimman käden, ja täten parhaimman pelaajan, löytämiseksi.

Jokaiseen *Player*-olioon liittyy myös *User*-olio. *User* kapsuloi tiettyn pelaajan tekemän käyttäjän sen hetkisen saldo-tilanteen. Käyttäjien saldoihin tapahtuneet muutokset tallennetaan *Result*-olioina.

## Päätoiminnallisuudet

### Pelin kulku

#### parhaimpien käsien tunnistaminen

Olellinen osa Texas Hold'Em:ia on pelaajien parhaiden käsien tunnistus. Ohessa sekvenssikaavio, joka kuvaa pelin lopussa käytettävän *HandComparator*-luokan *getBest()*-metodin kulkua.

![Kuva ohjelman arkkitehtuurista](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/getBestHandsSequence.png)

