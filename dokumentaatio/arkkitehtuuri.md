# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattaa seuraavanlaista kerrosarkkitehtuuria:

![Kuva ohjelman pakkausrrakenteesta](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkaus.png)

Pakkaus *texasholdem.ui* sisältää tekstikäyttöliittymän, *texasholdem.domain* sovelluslogiikan määrittävät luokat ja *tekasholdem.database* sekä *texasholdem.database.collector* tietokannan ylläpitämiseen tarvittavat luokat.

## Sovelluslogiikka

Seuraava luokka/pakkauskaavio kuvaa jokaisen sovelluksessa esiintyvän, mukaanlukematta käyttöliittymän muodostamiseen tarkoiteetujen, luokan suhdetta:

![Kuva ohjelman arkkitehtuurista](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/arkkitehtuuri_uusin.png)

### Pelin kulku

Käyttöliittymä käyttää *Game*-luokan, joka pitää kirjaa pelitilanteesta, sisältämiä metodeja pelin eri vaiheiden esittämiseen. Metodit, kuten *playNext()*, *playHuman()* ja *playShowdown()* suorittavat tietyn pelin vaiheen, jonka jälkeen käyttöliittymä voi näyttää käyttäjälle seuraavan pelitilanteen. Koska *Game* pitää huolta pelin kulusta, liittyvät siihen oliot *Player* ja *Deck*, joista *Player* kuvastaa pelissä mukava olevaa pelaajaa, oli hän sitten ihmis- tai tietokonepelaaja ja *Deck* kuvaa korttipakkaa, joka sisältää yksittäisiä kortteja kuvaavia *Card*-olioita.

## Päätoiminnallisuudet

### Pelin kulku

#### parhaimpien käsien tunnistaminen

Olellinen osa Texas Hold'Em:ia on pelaajien parhaiden käsien tunnistus. Ohessa sekvenssikaavio, joka kuvaa pelin lopussa käytettävän *HandComparator*-luokan *getBest()*-metodin kulkua.

![Kuva ohjelman arkkitehtuurista](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/getBestHandsSequence.png)

