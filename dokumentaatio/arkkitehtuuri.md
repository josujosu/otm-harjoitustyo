# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattaa seuraavanlaista kerrosarkkitehtuuria:

![Kuva ohjelman pakkausrrakenteesta](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkaus.png)

Pakkaus *texasholdem.ui* sisältää tekstikäyttöliittymän, *texasholdem.domain* sovelluslogiikan määrittävät luokat ja *tekasholdem.database* sekä *texasholdem.database.collector* tietokannan ylläpitämiseen tarvittavat luokat.

![Kuva ohjelman arkkitehtuurista](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/arkkitehtuuri_uusin.png)

## Päätoiminnallisuudet

### Pelin kulku

#### parhaimpien käsien tunnistaminen

Olellinen osa Texas Hold'Em:ia on pelaajien parhaiden käsien tunnistus. Ohessa sekvenssikaavio, joka kuvaa pelin lopussa käytettävän *HandComparator*-luokan *getBest()*-metodin kulkua.

![Kuva ohjelman arkkitehtuurista](https://github.com/josujosu/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/getBestHandsSequence.png)

