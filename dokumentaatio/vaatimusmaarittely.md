# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla voi pelata "Texas Hold'Em"-korttipeliä tekoälyä vastaan. Sovellukseen voi rekisteröidä useampia käyttäjiä ja tarkastella käyttäjien pelaamiin peleihin liittyviä tilastoja. Peliä ei pelata oikeasta rahasta, vaan pelkästään sovelluksen sisäisestä "virtuaalirahasta".

## Käyttäjät

Sovelluksessa on vain yhdenlaisia käyttäjiä, eli käyttäjiä ovat vain pelin pelaajat.

## Perusversion tarjoamat toiminnallisuudet

### Tunnuksen luonti

- Käyttäjä voi luoda sovellukseen oman tunnuksen
  - Tunnuksen on oltava uniikki
  - Tunnuksia voi luoda useita
- Jokaiseen tunnukseen liitetään aloitusrahamäärä (4000), jota voi kasvattaa tai vähentää pelaamalla tekoälyä vastaan
- Halutessaan pelata tietyllä tunnuksella, käyttäjä voi valita sen sovelluksessa esiintyvästä listasta

### Pelaaminen

- Käyttäjä pelaa kuutta tekoälyä vastaan
  - Tekoäly maksaa (call) ja kippaa (fold) sattumanvaraisesti 
  - Joka kierroksella tekoäly tarkistaa omaako jonkin "käden"
  - Jos tekoälyllä on jokin käsi, se maksaa todennäköisemmin
  - Jos peli on vasta alkukierroksilla, tekoäly maksaa todennäköisemmin
- Voitot ja häviöt tallennetaan tunnukseen liitettyyn rahamäärään
- Jokaisen pelin tiedot tallennetaan
  - Voittiko käyttäjä pelin
  - Käytetyn tunnuksen sen hetkinen rahamäärä
  - Voiton/häviön suuruus

### Tilastot

- Käyttäjä voi tarkastella tietyn tunnuksen tilastoja
  - Voittojen ja häviöoiden suhteen
  - Kuinka tunnukseen liitetty rahamäärä on muuttunut
  - Kuinka suuria häviöitä ja voittoja on tullut

## Jatkokehitysideoita

- Tekoälypelaajien määrän muuttaminen tietyssä pelissä
- Mahdollisuus antaa tekoälyn pelata tietyllä tunnuksella
- Tilastojen esittäminen diagrammina
- Pelaaminen muiden ihmisten kanssa
- Graaffinen käyttöliittymä


