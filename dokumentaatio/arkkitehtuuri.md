# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattaa seuraavanlaista kerrosarkkitehtuuria:

![Kuva ohjelman pakkausrrakenteesta](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkaus.png)

Pakkaus *texasholdem.ui* sisältää tekstikäyttöliittymän, *texasholdem.domain* sovelluslogiikan määrittävät luokat ja *tekasholdem.database* sekä *texasholdem.database.collector* tietokannan ylläpitämiseen tarvittavat luokat.

## Käyttöiittymä

Sovelluksen tekstikäyttöliittymä koostuu neljästä erillisestä näykmäoliosta:

- [StartTextScene](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/ui/text/StartTextScene.java), eli aloitus valikko

- [CreateUserTextScene](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/ui/text/CreateUserTextScene.java), eli käyttäjien hallinnoimisen valikko

- [UserStatsTextScene](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/ui/text/UserStatsTextScene.java), eli käyttäjien tilastojen tarkastelemisen valkko

- [PlayingTextScene](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/ui/text/PlayingTextScene.java), eli itse pelaamisen näkymä

Jokainen näistä näkymäolioista toteuttaa [TextScene](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/ui/text/TextScene.java)-rajapinnan, joka mahdollistaa näkymien saumattoman vaihtamisen. Näkymien vaihtaminen perustuu [TextScene](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/ui/text/TextScene.java):n määrittämään *run()*-metodiin, joka tekstikäyttöliittymän ulkoasun ja toiminnallisuuden määrittämisen lisäksi palauttaa *return*-arvonaan seuraavan näkymäolion. [TextUI](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/ui/text/TextUi.java)-luokka ajaa näiden näkymäolioiden *run()*-metodeja, ja saatuaan niiden palautusarvona seuraavan näkymäolion, aloittaa [TextUI](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/ui/text/TextUi.java) tämän olion *run()*-metodin ajamisen. Tämä jatkuu, kunnes [TextUI](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/ui/text/TextUi.java) saa palautusarvona null, jolloin ohjelma lopetetaan.

## Sovelluslogiikka

Seuraava luokka/pakkauskaavio kuvaa jokaisen sovelluksessa esiintyvän, mukaanlukematta käyttöliittymän muodostamiseen tarkoiteetujen, luokan suhdetta:

![Kuva ohjelman arkkitehtuurista](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/arkkitehtuuri_uusin.png)

Pelin kulku perustuu [Game](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Game.java)-luokan ja käyttöliittymän väliseen yhteistyöhön. Käyttöliittymä ottaa vastaan käyttäjän komentoja, ja välittää niiden kautta tehtäviä [Game](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Game.java)-oliolle, joka ylläpitää ja on perillä pelin tapahtumista. Pelin ylläpitäminen tapahtuu pääosin [Player](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Player.java)-olioiden ja [Deck](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Deck.java)-olioiden välityksellä. [Player](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Player.java) pitää sisällään tiedot tietystä pelin pelaajasta ja [Deck](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Deck.java) sisältää listan tiettyyn pakkaan sidotuista korteista, tai [Card](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Card.java)-olioista.

Riippuen kutsutusta metodista, [Player](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Player.java)-olio voi tehdä pokeriin liittyviä toimintoja (kuten *fold* ja *raise*) vastaamalla annettuun komentoon, tai valitsemalla sattumanvaraisesti [ComputerAI](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/ComputerAI.java)-luokan määrittelemien metodien avulla. Tehdyn toiminnon kapseloi [Action](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Action.java)-oliot.

Pelaajien käsien määrittely tapahtuu [HandComparator](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/HandComparator.java)-luokan ja [PokerHand](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/PokerHand.java)-olioiden avulla. [HandComparator](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/HandComparator.java) määrittelee metodit, joiden avulla voi verrata [PokerHand](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/PokerHand.java)-olioiden [Deck](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Deck.java)-olioista määrittelemiä pokerikäsiä. Pelaajien toimintojen jälkeen [Game](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Game.java)-olio käyttää kyseistä luokka parhaimman käden, ja täten parhaimman pelaajan, löytämiseksi.

Jokaiseen [Player](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Player.java)-olioon liittyy myös [User](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/User.java)-olio. [User](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/User.java) kapsuloi tiettyn pelaajan tekemän käyttäjän sen hetkisen saldo-tilanteen. Käyttäjien saldoihin tapahtuneet muutokset tallennetaan [Result](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Result.java)-olioina.

## Tietojen tallennus tietokantaan

Sovelluksen looginen datamalli koostuu kahdesta luokasta: [User](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/User.java) ja [Result](https://github.com/josujosu/otm-harjoitustyo/blob/master/TexasHoldEm/src/main/java/texasholdem/domain/Result.java). Nämä kuvaavat vastaavasti sovellukseen kirjattuja käyttäjiä ja niihin liitettyjä tuloksia. Käyttäjät ja tulokset tallennetaan ennaltamäärättyyn, sovelluksen juuresta löytyvään, tietokantaan: *THE.db*. Tietokantaan tallentaminen ja sen tietojen tarkastelu käyttävät hyväkseen rajapinnan *Dao*, sekä *UserDao*- ja *ResultDao*-luokkien määrittelemiä toiminnallisuuksia, jotka taas toimivat *Collector*-rajapinnan, *UserCollector*- ja *ResultCollector*-luokkien, sekä *Database*-luokan määrittelemien toimintojen avulla.

### Tietokanta

Sovelluksen tietokanta on SQLite-tietokanta, joka koostuu seuraavan tyyppisistä taulukoista:

#### User

id|username|balance 
--|--------|-------
1|Kalle|2000
2|Joonas|1200
...|...|...

#### Result

id|userId|oldBalance|balanceChange
--|------|----------|-------------
1|1|100|-70
2|2|100|70
3|1|30|1970
4|2|170|-170
...|...|...|...

## Päätoiminnallisuudet

### Pelin kulku

#### parhaimpien käsien tunnistaminen

Olellinen osa Texas Hold'Em:ia on pelaajien parhaiden käsien tunnistus. Ohessa sekvenssikaavio, joka kuvaa pelin lopussa käytettävän *HandComparator*-luokan *getBest()*-metodin kulkua.

![Kuva ohjelman arkkitehtuurista](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/getBestHandsSequence.png)

