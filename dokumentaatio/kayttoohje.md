# Käyttöohje
Lataa tiedosto ...
## Konfigurointi
Ohjelma olettaa, että *database*-tiedosto *THE.db* löytyy sovelluksen käynnistyshakemistosta. Tämä tiedosto pitää sisällään pelaajien tekemät käyttäjät ja niiden saldoihin liittyvät.
## Ohjelman käynnistäminen
Ohjelman käynnistäminen tapahtuu komennolla

    java -jar texasholdem.jar
    
## Ohjelman sisällä liikkuminen

Tarvittaessaan komennon, sovellus esittä vaihtoehdol esimerkiksi seuraavalla tavalla:

    ----Main Menu-----
    What do you want to do? 
    1: Manage users
    2: Play
    3: Chack user stats
    x: End program
    >
    
Käyttäjän on tällöin kirjoitettava haluamansa komento komentoriville ja painettava rivinvaihtoa.

## Käyttäjien muokkaaminen

Antamalla komennon *1* päänäkymässä, pääsee muokkaamaan käyttäjiä. Komentoriville tulostuu seuraava näkymä:

    -----Create User Menu-----
    1: Create user
    2: List all created users
    3: Remove user
    x: Back to start screen
    >
    
Valitsemalla komennon *1*, käyttäjältä kysytään käyttäjänimeä. Käyttäjänimen antaminen luo uuden käyttäjän ja antaa tämän aloitussaldoksi 4000. Komento *2* listaa kaikki luodut käyttäjät *id*-tunnuksineen ja tämän hetkisine saldoineen. Komennolla *3* käyttäjä voi poistaa tallennetun käyttäjän antamalla sovellukselle kyseisen näyttäjän *id*:n. *x* palauttaa päänäkymän.

## Pelaaminen

Antamalla komennon *2* päänäkymässä pääsee pelaamaan Texas Hold'Em:ia. Ennen kuin voi aloittaa pelin, sovellus vaatii pelaajaa antamaan hänen käyttämänsä käyttäjän *id*-tunnuksen. Itse pelinäkymä näyttää esimerkiksi tältä:

    CPU4: Folded
    CPU5: Called/Checked
    CPU2: Folded
    CPU7: Folded
    CPU1: Called/Checked
    CPU5: Called/Checked
    CPU1: Called/Checked
    CPU6: Folded
    Pot: 75 Table: 4♠ K♣ 2♥ 
    User: id: 3, username: Joonas, balance: 10784, bet: 10, Hand: 4♣ 2♠ 
    Actions: 
     Call, Raise, Fold
    What will you do? 

Sovellus tulostaa tekoälypelaajien tekemät toiminnot, sen hetikisen potin, pöydällä olevat kortit, sekä pelaajan käyttämän käyttäjän tiedot. Pelaaja voi antaa komennoksi *Call*, *Raise* tai *Fold* (komennon oltava identtinen esitetyn muodon kanssa, väärät komennot toimivat *Call* komennon tavoin). Pelin loputtua sovellus tulostaa jokaisen loppuun asti pelanneen pelaajan tiedot. Pelin tapahtumien tiedot tallentuvat automaattisesti tietokantaan.

## Käyttäjätilastojen tarkastelu

Antamalla komennon *3* aloitusnäkymässä, käyttäjä pääsee tutkimaan tallennettujen käyttäjien tilastoja. Sovellus pyytää sovelluksen käyttäjää antamaan tarkasteltavan käyttäjän *id*-tunnuksen. Tämän jälkeen seuraava näkymä tulostuu komentoriville:

    What do you want to check?
    1: Win/Lose ratio
    2: Balance history
    3: Wins and Losses
    x: Stop
    >

Komento *1* tulostaa komentoriville voitettujen ja hävittyjen käsien suhteen, komento *2* näyttää kaikki saldot, joita käyttäjän nimiin on tallennettu ja komento *3* tulostaa kaikki käyttäjän häviöt ja voitot (häviöt näkyvät negatiivisina lukuina ja voitot positiivisina). *x* palauttaa päänäkymän.



