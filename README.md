# ISA-2020
[![Build Status](https://travis-ci.com/ISA-TEAM24/ISA-2020.svg?branch=master)](https://travis-ci.com/ISA-TEAM24/ISA-2020)

<h1>MDNN Pharmacies</h1>

**Tim 24** - STUDENTI :
- Student 1 : Mark Ristic, RA115/2017, risticmark1337@gmail.com
- Student 2 : Danilo Paripovic, RA98/2017, danilo.paripovic@yahoo.com
- Student 3 : Nikola Tomik, RA116/2017, nikola.tomik12@gmail.com
- Student 4 : Nikola Aleksic RA125/2017 (u medjuvremenu odustao od odbrane projekta u junskom roku)

**Trello board** : https://trello.com/b/ehcSpXOa/isa-board

**Koriscene tehnologije i jezici**:
- PostgreSQL za bazu,
- Spring boot (java) za backend,
- JQuery/Ajax za medjusloj,
- Html,css + bootstrap za frontend.

Kao CI koristili smo Travis CI koji je na svaki push na bilo koju granu pokretao 'job' i buildovao aplikaciju, u slucaju neke greske ili buga svi clanovi
tima su dobijali notifikaciju na mejl.

**Gitflow**:
- Za svaku funkcionalnost se izvlacila posebna grana koja se posle preko pull requestova spajala sa develop granom
- Kako bi PR prosao moraju da se izvrse 2 Travis Builda (jedan za granu, jedan za PR) i mora da bude odobren od strane bar jednog kolaboratora
- Ukoliko su konflikti bili u sql script fajlovima oni su se mogli resiti i lokalno i na samom remote-u, dok su se ozbiljniji konflikti resavali
putem InteliJ IDEA radnog okruzenja.
- pravila za nazive grana :
- 'feature/neka-funkcionalnost' - grana koja u sebi sadrzi i backend i frontend i predstavlja kompletno resenje funkcionalnosti
- 'static/neka-stranica' - grana koja u sebi sadrzi frontend za odredjeni deo aplikacije
- 'fix/neka-ispravka' - grane koje sluze za pronalazenje i ispravku bugova u aplikaciji
- 'database/neki-naziv' - grana koja sluzi za modifikovanje podataka baze


Java verzija : 1.8
Maven verzija : 1.6.1

**Pokretanje**** :
- Pokrece se kao klasicna spring boot aplikacija, mi smo za razvoj koristili InteliJ IDEA, ali projekat moze da se pokrene i iz drugih okruzenja
- Resenje se pokrece lokalno
- skripte sa insert izrazima za inicijalno popunjavanje baze se nalaze u 'src/main/resources/data-postgres.sql'

**Napomene** :
- U application.properties su definisane sledece vrednosti :
    - port aplikacije '8080'
    - URL do baze , (po default-u i kod nas u application.properties na portu '5432')
    - POTREBNO je kreirati semu baze pod nazivom "jpa"
    - navedeni su kredencijali (username i password) za konektovanje na bazu - u fajlu su ostavljene default kredencijali ('username', 'root')
    - neke od operacija (navedene ispod) zahtevaju slanje mejla te je potreban odredjen broj sekundi da se zahtev izvrsi i vrati odgovor
    - preporucujemo google chrome kao pretrazivac (zbog PRIKAZA GRAFICKOG KALENDARA, datepickera i timepickera)
    - za manje ekrane ako je potrebno mozete odzumirati za 10%
    
**VAZNA NAPOMENA** :
- Kako bi se mogao koristiti email servis potrebno je postaviti sledece environment varijable u IDE (koristili smo IDE variable, a ne config
kako bi smo sprecili curenje kredencijala na javni repozitorijum) :
- Neophodne environment varijable
SECRET_EMAIL  
SECRET_PASSWORD
- Kredencijali email-a koji smo mi dostavili za ovaj projekat se nalaze na ![slici](https://media.discordapp.net/attachments/506487679849070593/851150420386381874/unknown.png)
ili na ![slici2](https://prnt.sc/14e8vh2)
- moguce je koristiti i sopstveni mejl, (mora biti gmail i mora da se ugasi security opcija za 'less secure apps')

    Za InteliJ IDEA : 
        https://www.jetbrains.com/help/objc/add-environment-variables-and-program-arguments.html#add-environment-variables

    Za Eclipse : 
        - Desni klik na main klasu > Properties > Run/Debug Settings > selektuje se konfiguracija > Edit > Environment > dodaju se varijable 


**Korisnici** koji su unapred kreirani, sve lozinke su 'test' (bez navodnika), svi ovi podaci su takodje nabrojani u sql skripti

    primer logovanja na sistem : 
        - za username se ukuca 'pacijentana' (bez navodnika) i za lozinku se stavi 'test'

    - vrsta_korisnika:
        - username_korisnika, email_korisnika (sve lozinke su 'test')

    - pacijenti : 
        pacijentmarko, 1pacijent1@gmail.com
        pacijentana, 2pacijent2@gmail.com

    - dermatolozi : 
        dermatologpera, 1dermatolog1@gmail.com
        dermatologiva, 2dermatolog2@gmail.com

    - farmaceuti :
        farmaceutnikola, 1farmaceut1@gmail.com
        farmaceutneda, 2farmaceut2@gmail.com
        farmaceutstojan, 3farmaceut3@gmail.com 
    
    - admini apoteka : 
        adminapotekedanilo, 1adminapoteke1@gmail.com
        adminapotekemilica, 2adminapoteke2@gmail.com

    - neke od preostalih uloga postoje (radi upotpunosti aplikacije), ali ih student 4 nije implementirao


Studenti 1 2 i 3 su implementirali sve funkcionalnosti za ocenu 7.
(Student 1 je implementirao neke funkcionalnosti studenta 4 kako bi se upotpunili odredjeni aspekti aplikacije
dodatne funkcionalnosti su za pacijenta, (pretplata na promocije, ostavljanje zalbi, pretraga lekova..))






