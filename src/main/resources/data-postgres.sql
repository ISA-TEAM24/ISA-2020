-- autoriteti

insert into authority values (1, 'ROLE_USER');
insert into authority values (2, 'ROLE_DERMATOLOGIST');
insert into authority values (3, 'ROLE_PHARMACIST');
insert into authority values (4, 'ROLE_PH_ADMIN');
insert into authority values (5, 'ROLE_ADMINISTRATOR');


-- pacijent Marko Markovic pacijentmarko (lozinke su heshirane - za sve korisnike je 'test' bez navodnika
INSERT INTO public.loyalty_info (id, klasa, penali, poeni, month_of_last_reset) VALUES (1, 0, 0, 0, 6);

INSERT INTO public.loyalty_apoteka_mapping(loyalty_id, prati, naziv_apoteke) VALUES (1, true, 'BENU');
INSERT INTO public.loyalty_apoteka_mapping(loyalty_id, prati, naziv_apoteke) VALUES (1, true, 'ZEGIN');

INSERT INTO public.korisnik(
    id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id)
VALUES (1, true, 'Prvomajska 1/25', 'Srbija', '1pacijent1@gmail.com', 'Backa Topola', 'Marko', '2021-05-27', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Markovic', false, '0631123245', 'pacijentmarko', NULL, 1);

INSERT INTO public.korisnik_alergije (korisnik_id, alergije) VALUES (1, 'Aspirin');

INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (1, 1);

-- pacijent Ana Stankovic pacijentana (lozinke su heshirane - za sve korisnike je 'test' bez navodnika

INSERT INTO public.loyalty_info (id, klasa, penali, poeni, month_of_last_reset) VALUES (2, 1, 3, 25, 4);

INSERT INTO public.loyalty_apoteka_mapping(loyalty_id, prati, naziv_apoteke) VALUES (2, true, 'BENU');
INSERT INTO public.loyalty_apoteka_mapping(loyalty_id, prati, naziv_apoteke) VALUES (2, true, 'ZEGIN');

INSERT INTO public.korisnik(
    id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id)
VALUES (2, true, 'Bulevar Cara Lazara 5/25', 'Srbija', '2pacijent2@gmail.com', 'Novi Sad', 'Ana', '2021-05-27', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Stankovic', false, '0651123245', 'pacijentana', NULL, 2);

INSERT INTO public.korisnik_alergije (korisnik_id, alergije) VALUES (2, 'Penicilin');

INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (2, 1);

-- dermatolog Pera Peric dermatologpera (lozinke su heshirane - za sve korisnike je 'test' bez navodnika

--godisnji info
INSERT INTO public.godisnji_info (id, do_datuma, na_godisnjem, od_datuma) VALUES (3, NULL, false, NULL);

INSERT INTO public.korisnik(
    id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id)
VALUES (3, true, 'Rakoci Ferenca 22', 'Srbija', '1dermatolog1@gmail.com', 'Subotica', 'Pera', '2021-05-27', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Peric', true, '0603323245', 'dermatologpera', 3, NULL);

-- radi u apoteci benu - ne radi nedeljom
INSERT INTO public.radno_info(id) VALUES (3);

INSERT INTO public.korisnik_radno_info(korisnik_id, radno_info_id, radno_info_key) VALUES (3, 3, 'BENU');
INSERT INTO public.period(id, do_datum, do_vreme, od_datum, od_vreme) VALUES (1, '2021-09-27', '20:00', '2021-05-21', '12:00');
INSERT INTO public.radno_info_business_hours(radno_info_id, business_hours_id) VALUES (3, 1);
INSERT INTO public.radno_info_neradni_dani(radno_info_id, neradni_dani) VALUES (3, 7);

-- radi u apoteci zegin - ne radi subotom
INSERT INTO public.radno_info(id) VALUES (4);

INSERT INTO public.korisnik_radno_info(korisnik_id, radno_info_id, radno_info_key) VALUES (3, 4, 'ZEGIN');
INSERT INTO public.period(id, do_datum, do_vreme, od_datum, od_vreme) VALUES (2, '2021-09-29', '11:00', '2021-05-13', '08:00');
INSERT INTO public.radno_info_business_hours(radno_info_id, business_hours_id) VALUES (4, 2);
INSERT INTO public.radno_info_neradni_dani(radno_info_id, neradni_dani) VALUES (4, 6);

INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (3, 2);

-- dermatolog Iva Budimirov  dermatologiva (lozinke su heshirane - za sve korisnike je 'test' bez navodnika

--godisnji info
INSERT INTO public.godisnji_info (id, do_datuma, na_godisnjem, od_datuma) VALUES (4, NULL, false, NULL);

INSERT INTO public.korisnik(
    id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id)
VALUES (4, true, 'Poljska 7', 'Srbija', '2dermatolog2@gmail.com', 'Novi Sad', 'Iva', '2021-05-27', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Budimirov', false, '06100023245', 'dermatologiva', 4, NULL);

-- radi u apoteci benu - ne radi ponedeljkom
INSERT INTO public.radno_info(id) VALUES (5);

INSERT INTO public.korisnik_radno_info(korisnik_id, radno_info_id, radno_info_key) VALUES (4, 5, 'BENU');
INSERT INTO public.period(id, do_datum, do_vreme, od_datum, od_vreme) VALUES (3, '2021-09-22', '21:00', '2021-04-09', '13:00');
INSERT INTO public.radno_info_business_hours(radno_info_id, business_hours_id) VALUES (5, 3);
INSERT INTO public.radno_info_neradni_dani(radno_info_id, neradni_dani) VALUES (3, 1);

INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (4, 2);

-- farmaceut Nikola Gogic  farmaceutnikola (lozinke su heshirane - za sve korisnike je 'test' bez navodnika

--godisnji info
INSERT INTO public.godisnji_info (id, do_datuma, na_godisnjem, od_datuma) VALUES (5, NULL, false, NULL);

INSERT INTO public.korisnik(
    id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id)
VALUES (5, true, 'Sutjeska 27', 'Srbija', '1farmaceut1@gmail.com', 'Novi Sad', 'Nikola', '2021-05-27', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Gogic', true, '0633334422', 'farmaceutnikola', 5, NULL);

-- radi u apoteci benu - on uvek radi
INSERT INTO public.radno_info(id) VALUES (6);
INSERT INTO public.korisnik_radno_info(korisnik_id, radno_info_id, radno_info_key) VALUES (5, 6, 'BENU');
INSERT INTO public.period(id, do_datum, do_vreme, od_datum, od_vreme) VALUES (4, '2021-09-22', '21:00', '2021-04-09', '13:00');
INSERT INTO public.radno_info_business_hours(radno_info_id, business_hours_id) VALUES (6, 4);
INSERT INTO public.radno_info_neradni_dani(radno_info_id, neradni_dani) VALUES (6, NULL);

INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (5, 3);

-- farmaceut Neda Maric farmaceutneda (lozinke su heshirane - za sve korisnike je 'test' bez navodnika

--godisnji info
INSERT INTO public.godisnji_info (id, do_datuma, na_godisnjem, od_datuma) VALUES (6, NULL, false, NULL);

INSERT INTO public.korisnik(
    id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id)
VALUES (6, true, 'Partizanska 5', 'Srbija', '2farmaceut2@gmail.com', 'Novi Sad', 'Neda', '2021-05-27', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Maric', false, '0661100520', 'farmaceutneda', 6, NULL);

-- radi u apoteci ZEGIN - ona uvek radi

INSERT INTO public.radno_info(id) VALUES (7);
INSERT INTO public.korisnik_radno_info(korisnik_id, radno_info_id, radno_info_key) VALUES (6, 7, 'ZEGIN');
INSERT INTO public.period(id, do_datum, do_vreme, od_datum, od_vreme) VALUES (5, '2021-09-22', '21:00', '2021-04-09', '13:00');
INSERT INTO public.radno_info_business_hours(radno_info_id, business_hours_id) VALUES (7, 5);
INSERT INTO public.radno_info_neradni_dani(radno_info_id, neradni_dani) VALUES (7, NULL);

INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (6, 3);


-- admin apoteke Danilo Paripovic adminapotekedanilo (lozinke su heshirane - za sve korisnike je 'test' bez navodnika
-- admin za benu apoteku

--godisnji info
INSERT INTO public.godisnji_info (id, do_datuma, na_godisnjem, od_datuma) VALUES (7, NULL, false, NULL);

INSERT INTO public.korisnik(
    id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id)
VALUES (7, true, 'Prvomajska 20', 'Srbija', '1adminapoteke1@gmail.com', 'Backa Topola', 'Danilo', '2021-05-27', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Paripovic', true, '060000001', 'adminapotekedanilo', 7, NULL);

INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (7, 4);

-- admin apoteke Milica Ivanovic adminapotekemilica (lozinke su heshirane - za sve korisnike je 'test' bez navodnika
-- admin apoteke za zegin

--godisnji info
INSERT INTO public.godisnji_info (id, do_datuma, na_godisnjem, od_datuma) VALUES (8, NULL, false, NULL);

INSERT INTO public.korisnik(
    id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id)
VALUES (8, true, 'Heroja Pinkija 36', 'Srbija', '2adminapoteke2@gmail.com', 'Backa Topola', 'Milica', '2021-05-27', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Ivanovic', false, '0625223050', 'adminapotekemilica', 8, NULL);

INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (8, 4);

-- dobavljac Boris Ognjenovic -- fali implementacija studenta 4, ali se koristi za funkcionalnosti drugih studenata

INSERT INTO public.korisnik(
    id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id)
VALUES (9, true, 'Siroka 21', 'Srbija', '1dobavljac1@gmail.com', 'Subotica', 'Boris', '2021-05-27', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Ognjenovic', false, '06522552125', 'dobavljacboris', NULL, NULL);

-- dobavljac Jelena Sapic -- fali implementacija studenta 4, ali se koristi za funkcionalnosti drugih studenata

INSERT INTO public.korisnik(
    id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id)
VALUES (10, true, 'Dimitrija Tucovica 7', 'Srbija', '2dobavljac2@gmail.com', 'Novi Sad', 'Jelena', '2021-05-27', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Sapic', false, '063333331', 'dobavljacjelena', NULL, NULL);

-- farmaceut Stojan Plavsic farmaceutstojan (lozinke su heshirane - za sve korisnike je 'test' bez navodnika

--godisnji info
INSERT INTO public.godisnji_info (id, do_datuma, na_godisnjem, od_datuma) VALUES (11, NULL, false, NULL);

INSERT INTO public.korisnik(
    id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id)
VALUES (11, true, 'Zlatiborska 15', 'Srbija', '3farmaceut3@gmail.com', 'Novi Sad', 'Stojan', '2021-05-27', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Plavsic', true, '0631100520512', 'farmaceutstojan', 11, NULL);

-- radi u apoteci zegin -- ne radi utorkom

INSERT INTO public.radno_info(id) VALUES (11);
INSERT INTO public.korisnik_radno_info(korisnik_id, radno_info_id, radno_info_key) VALUES (11, 11, 'ZEGIN');
INSERT INTO public.period(id, do_datum, do_vreme, od_datum, od_vreme) VALUES (6, '2021-09-22', '21:00', '2021-04-09', '13:00');
INSERT INTO public.radno_info_business_hours(radno_info_id, business_hours_id) VALUES (11, 6);
INSERT INTO public.radno_info_neradni_dani(radno_info_id, neradni_dani) VALUES (11, 2);

INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (11, 3);

-- lekovi

INSERT INTO public.lek(id, na_recept, napomene, naziv, oblik, ocena, poeni, proizvodjac, sastav, vrsta)
values (1, true, 'Ne preterivati', 'Aspirin', 'tableta', 5, 3, 'Hemofarm', 'diklofenak', 'analgetik' );

INSERT INTO public.lek(id, na_recept, napomene, naziv, oblik, ocena, poeni, proizvodjac, sastav, vrsta)
values (2, true, 'Ne preterivati', 'Brufen', 'tableta', 5, 3, 'Hemofarm', 'diklofenak', 'antiinflamator' );

INSERT INTO public.lek(id, na_recept, napomene, naziv, oblik, ocena, poeni, proizvodjac, sastav, vrsta)
values (3, true, 'Ne preterivati', 'Rapidol', 'prasak', 5, 3, 'Galenika', 'penicilin,diklofenak', 'analgetik' );

INSERT INTO public.lek(id, na_recept, napomene, naziv, oblik, ocena, poeni, proizvodjac, sastav, vrsta)
values (4, true, 'Ne preterivati', 'Bromazepan', 'pilula', 5, 3, 'Galenika', 'penicilin,diklofenak', 'analgetik' );

INSERT INTO public.lek(id, na_recept, napomene, naziv, oblik, ocena, poeni, proizvodjac, sastav, vrsta)
values (5, true, 'Ne preterivati', 'Raptenk', 'pilula', 5, 3, 'Hemofarm', 'frumesin,letiroksin', 'analgetik' );

INSERT INTO public.lek(id, na_recept, napomene, naziv, oblik, ocena, poeni, proizvodjac, sastav, vrsta)
values (6, true, 'Ne preterivati', 'Kafetin', 'pilula', 5, 3, 'Hemofarm', 'frumesin,letiroksin', 'analgetik' );

INSERT INTO public.lek(id, na_recept, napomene, naziv, oblik, ocena, poeni, proizvodjac, sastav, vrsta)
values (7, false, 'Ne preterivati', 'Sinacilin', 'pilula', 5, 3, 'Hemofarm', 'frumesin,letiroksin', 'analgetik' );

-- zamenski lekovi
-- raptenk kafetin
INSERT INTO public.lek_alternative(lek_id, alternative) VALUES (5, 6);
INSERT INTO public.lek_alternative(lek_id, alternative) VALUES (6, 5);

-- brufen rapidol
INSERT INTO public.lek_alternative(lek_id, alternative) VALUES (2, 3);
INSERT INTO public.lek_alternative(lek_id, alternative) VALUES (3, 2);



-- apoteka BENU

INSERT INTO public.apoteka(id, adresa, naziv, ocena, opis) VALUES (1, 'Glavna 56, Novi Sad', 'BENU', 5, 'Najbolja apoteka');

INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (1, 3);
INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (1, 4);
INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (1, 5);
INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (1, 7);

INSERT INTO public.apoteka_magacin_mapping(apoteka_id, kolicina, id_leka) VALUES (1, 50, 1);
INSERT INTO public.apoteka_magacin_mapping(apoteka_id, kolicina, id_leka) VALUES (1, 50, 2);
INSERT INTO public.apoteka_magacin_mapping(apoteka_id, kolicina, id_leka) VALUES (1, 0, 3); -- nema rapidola
INSERT INTO public.apoteka_magacin_mapping(apoteka_id, kolicina, id_leka) VALUES (1, 50, 4);

INSERT INTO public.apoteka_cenovnik_mapping(apoteka_id, cena, naziv_predmeta) VALUES (1, 2000, 'SAVETOVANJE');
INSERT INTO public.apoteka_cenovnik_mapping(apoteka_id, cena, naziv_predmeta) VALUES (1, 1500, 'PREGLED');
INSERT INTO public.apoteka_cenovnik_mapping(apoteka_id, cena, naziv_predmeta) VALUES (1, 250, 'Aspirin');
INSERT INTO public.apoteka_cenovnik_mapping(apoteka_id, cena, naziv_predmeta) VALUES (1, 320, 'Brufen');
INSERT INTO public.apoteka_cenovnik_mapping(apoteka_id, cena, naziv_predmeta) VALUES (1, 370, 'Rapidol');
INSERT INTO public.apoteka_cenovnik_mapping(apoteka_id, cena, naziv_predmeta) VALUES (1, 360, 'Bromazepan');

-- apoteka ZEGIN

INSERT INTO public.apoteka(id, adresa, naziv, ocena, opis) VALUES (2, 'Bulevar oslobodjenja 72, Novi Sad', 'ZEGIN', 5, 'Najbolja apoteka');

INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (2, 3);
INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (2, 6);
INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (2, 8);
INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (2, 11);

INSERT INTO public.apoteka_magacin_mapping(apoteka_id, kolicina, id_leka) VALUES (2, 50, 4);
INSERT INTO public.apoteka_magacin_mapping(apoteka_id, kolicina, id_leka) VALUES (2, 0, 5); -- nema kafetina
INSERT INTO public.apoteka_magacin_mapping(apoteka_id, kolicina, id_leka) VALUES (2, 50, 6);
INSERT INTO public.apoteka_magacin_mapping(apoteka_id, kolicina, id_leka) VALUES (2, 50, 7);

INSERT INTO public.apoteka_cenovnik_mapping(apoteka_id, cena, naziv_predmeta) VALUES (2, 1250, 'SAVETOVANJE');
INSERT INTO public.apoteka_cenovnik_mapping(apoteka_id, cena, naziv_predmeta) VALUES (2, 2200, 'PREGLED');
INSERT INTO public.apoteka_cenovnik_mapping(apoteka_id, cena, naziv_predmeta) VALUES (2, 390, 'Bromazepan');
INSERT INTO public.apoteka_cenovnik_mapping(apoteka_id, cena, naziv_predmeta) VALUES (2, 280, 'Raptenk');
INSERT INTO public.apoteka_cenovnik_mapping(apoteka_id, cena, naziv_predmeta) VALUES (2, 410, 'Kafetin');
INSERT INTO public.apoteka_cenovnik_mapping(apoteka_id, cena, naziv_predmeta) VALUES (2, 325, 'Sinacilin');

--Predefinisani termini dermatologa Pere Perica
-- benu
INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (1,  '2021-07-20', '', 5, 30, '15:30', 1, 1, null, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (2,  '2021-07-21', '', 5, 30, '15:30', 1, 1, null, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (3,  '2021-07-21', '', 5, 30, '16:30', 1, 1, null, 3);

-- zegin
INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (4,  '2021-07-25', '', 5, 30, '09:30', 1, 2, null, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (5,  '2021-07-26', '', 5, 30, '09:30', 1, 2, null, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (6,  '2021-07-26', '', 5, 30, '10:30', 1, 2, null, 3);

--Predefinisani termini dermatologa Ive Budimirov
-- zegin

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (7,  '2021-07-27', '', 5, 30, '13:30', 1, 1, null, 4);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (8,  '2021-08-04', '', 5, 30, '14:30', 1, 1, null, 4);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (9,  '2021-08-04', '', 5, 30, '15:30', 1, 1, null, 4);

-- 4 prosla pregleda za pacijenta Marka Markovica

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (10,  '2021-05-27', 'S00 T98', 5, 30, '13:30', 1, 1, 1, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (11,  '2021-05-20', 'S01 T96', 5, 30, '10:30', 1, 2, 1, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (12,  '2021-06-01', 'S03 Z92', 5, 30, '12:15', 1, 1, 1, 4);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (13,  '2021-04-12', 'A00 B99', 5, 30, '14:00', 1, 1, 1, 4);


-- 4 prosla savetovanja za pacijenta Marka Markovica

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (14,  '2021-03-27', 'S00 T98', 5, 30, '17:30', 0, 1, 2, 5);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (15,  '2021-03-29', 'L00 L99', 5, 30, '14:30', 0, 1, 1, 5);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (16,  '2021-04-10', 'S07 F07', 5, 30, '15:30', 0, 2, 1, 6);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (17,  '2021-05-12', 'S00 T982', 5, 30, '12:00', 0, 2, 2, 11);


-- 4 aktivna pregleda za pacijenta Marka Markovica

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (18,  '2021-06-27', '', 5, 30, '16:30', 1, 1, 1, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (19,  '2021-07-02', '', 5, 30, '10:30', 1, 1, 1, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (20,  '2021-07-02', '', 5, 30, '15:30', 1, 1, 2, 4);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (21,  '2021-07-10', '', 5, 30, '12:00', 1, 1, 2, 4);

-- 4 aktivna savetovanja za pacijenta Marka Markovica

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (22,  '2021-06-25', '', 5, 30, '16:30', 0, 1, 1, 5);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (23,  '2021-07-22', '', 5, 30, '10:30', 0, 1, 1, 5);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (24,  '2021-07-01', '', 5, 30, '15:30', 0, 2, 2, 11);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (25,  '2021-07-04', '', 5, 30, '12:00', 0, 1, 2, 6);

-- 4 prosla pregleda za pacijenta Anu Stankovic

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (26,  '2021-05-21', 'S40 Z98', 5, 30, '13:30', 1, 1, 2, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (27,  '2021-04-25', 'F01 T96', 5, 30, '10:30', 1, 1, 2, 4);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (28,  '2021-04-20', 'Q03 Z94', 5, 30, '12:15', 1, 1, 2, 4);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (29,  '2021-03-12', 'AZ4 B69', 5, 30, '14:00', 1, 1, 2, 3);

-- 4 prosla savetovanja za pacijenta Anu Stankovic

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (30,  '2021-05-20', 'K40 Z18', 5, 30, '13:30', 0, 1, 2, 5);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (31,  '2021-06-03', 'F41 T46', 5, 30, '10:30', 0, 2, 2, 6);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (32,  '2021-04-18', 'Z03 Z93', 5, 30, '12:15', 0, 2, 2, 6);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (33,  '2021-03-11', 'AZ4 B69', 5, 30, '14:00', 0, 2, 2, 11);

-- 3 aktivna pregleda za pacijenta Anu Stankovic

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (34,  '2021-08-21', '', 5, 30, '13:30', 1, 1, 2, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (35,  '2021-08-25', '', 5, 30, '10:30', 1, 2, 2, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (36,  '2021-09-02', '', 5, 30, '12:15', 1, 1, 2, 4);


-- 3 prosla savetovanja za pacijenta Anu Stankovic

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (37,  '2021-08-04', '', 5, 30, '12:30', 0, 1, 2, 5);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (38,  '2021-08-06', '', 5, 30, '13:30', 0, 2, 2, 6);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (39,  '2021-08-10', '', 5, 30, '12:00', 0, 2, 2, 11);

-- UPITI za lekove - uspesni
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (1, 5, true, 2, 1, 3, '2021-05-23');
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (2, 10, true, 1, 7, 4, '2021-04-12');
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (3, 7, true, 2, 5, 11, '2020-12-23');
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (4, 2, true, 1, 5, 5, '2021-04-05');
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (5, 2, true, 1, 5, 5, '2021-06-01');

INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (6, 5, true, 1, 1, 3, '2021-04-19');
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (7, 3, true, 2, 7, 3, '2021-03-13');
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (8, 12, true, 2, 5, 11, '2020-12-02');
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (9, 1, true, 1, 5, 6, '2021-05-05');
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (11, 6, true, 1, 5, 6, '2021-04-19');

-- UPITI za lekove - neuspesni
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (12, 7, false, 2, 3, 11, '2021-06-05');
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (13, 2, false, 1, 4, 3, '2021-03-01');
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (14, 7, false, 1, 3, 4, '2021-02-22');
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (15, 2, false, 1, 1, 6, '2021-02-11');
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (16, 7, false, 2, 1, 5, '2021-03-14');
INSERT INTO public.upit(id, kolicina, uspesan, apoteka_id, lek_id, posiljalac_id, datum) VALUES (17, 2, false, 2, 2, 6, '2021-05-15');

-- NARUDZBENICE
INSERT INTO public.narudzbenica(id, rok_za_ponudu, status, apoteka_id, kreirao_id) VALUES (1, '2021-05-29', 0, 1, 7);
INSERT INTO public.narudzbenica_lekovi_mapping(narudzbenica_id, kolicina, naziv_leka) VALUES (1, 25, 'Brufen');
INSERT INTO public.narudzbenica_lekovi_mapping(narudzbenica_id, kolicina, naziv_leka) VALUES (1, 25, 'Rapidol');

INSERT INTO public.narudzbenica(id, rok_za_ponudu, status, apoteka_id, kreirao_id) VALUES (2, '2021-05-29', 0, 2, 8);
INSERT INTO public.narudzbenica_lekovi_mapping(narudzbenica_id, kolicina, naziv_leka) VALUES (2, 30, 'Bromazepan');
INSERT INTO public.narudzbenica_lekovi_mapping(narudzbenica_id, kolicina, naziv_leka) VALUES (2, 50, 'Raptenk');

INSERT INTO public.narudzbenica(id, rok_za_ponudu, status, apoteka_id, kreirao_id) VALUES (3, '2021-06-28', 0, 1, 7);
INSERT INTO public.narudzbenica_lekovi_mapping(narudzbenica_id, kolicina, naziv_leka) VALUES (3, 15, 'Kafetin');
INSERT INTO public.narudzbenica_lekovi_mapping(narudzbenica_id, kolicina, naziv_leka) VALUES (3, 10, 'Sinacilin');

INSERT INTO public.narudzbenica(id, rok_za_ponudu, status, apoteka_id, kreirao_id) VALUES (4, '2021-04-10', 1, 2, 8);
INSERT INTO public.narudzbenica_lekovi_mapping(narudzbenica_id, kolicina, naziv_leka) VALUES (4, 35, 'Bromazepan');
INSERT INTO public.narudzbenica_lekovi_mapping(narudzbenica_id, kolicina, naziv_leka) VALUES (4, 35, 'Sinacilin');

-- PONUDE ZA NARUDZBENICU

INSERT INTO public.ponuda(id, status, ukupna_cena, narudzbenica_id, posiljalac_id) VALUES (1, 2, 2000, 1, 9);
INSERT INTO public.ponuda_lekovi_mapping(ponuda_id, kolicina, naziv_leka) VALUES (1, 25, 'Brufen');
INSERT INTO public.ponuda_lekovi_mapping(ponuda_id, kolicina, naziv_leka) VALUES (1, 25, 'Rapidol');

INSERT INTO public.ponuda(id, status, ukupna_cena, narudzbenica_id, posiljalac_id) VALUES (2, 2, 2300, 1, 10);
INSERT INTO public.ponuda_lekovi_mapping(ponuda_id, kolicina, naziv_leka) VALUES (2, 25, 'Brufen');
INSERT INTO public.ponuda_lekovi_mapping(ponuda_id, kolicina, naziv_leka) VALUES (2, 25, 'Rapidol');

INSERT INTO public.ponuda(id, status, ukupna_cena, narudzbenica_id, posiljalac_id) VALUES (3, 2, 1600, 2, 9);
INSERT INTO public.ponuda_lekovi_mapping(ponuda_id, kolicina, naziv_leka) VALUES (3, 30, 'Bromazepan');
INSERT INTO public.ponuda_lekovi_mapping(ponuda_id, kolicina, naziv_leka) VALUES (3, 50, 'Raptenk');

INSERT INTO public.ponuda(id, status, ukupna_cena, narudzbenica_id, posiljalac_id) VALUES (4, 2, 1300, 2, 10);
INSERT INTO public.ponuda_lekovi_mapping(ponuda_id, kolicina, naziv_leka) VALUES (4, 30, 'Bromazepan');
INSERT INTO public.ponuda_lekovi_mapping(ponuda_id, kolicina, naziv_leka) VALUES (4, 50, 'Raptenk');


INSERT INTO public.ponuda(id, status, ukupna_cena, narudzbenica_id, posiljalac_id) VALUES (5, 2, 1750, 3, 10);
INSERT INTO public.ponuda_lekovi_mapping(ponuda_id, kolicina, naziv_leka) VALUES (5, 15, 'Kafetin');
INSERT INTO public.ponuda_lekovi_mapping(ponuda_id, kolicina, naziv_leka) VALUES (5, 10, 'Sinacilin');

INSERT INTO public.ponuda(id, status, ukupna_cena, narudzbenica_id, posiljalac_id) VALUES (6, 2, 2000, 3, 9);
INSERT INTO public.ponuda_lekovi_mapping(ponuda_id, kolicina, naziv_leka) VALUES (6, 15, 'Kafetin');
INSERT INTO public.ponuda_lekovi_mapping(ponuda_id, kolicina, naziv_leka) VALUES (6, 10, 'Sinacilin');

--ERECEPTI za Marka Markovica
INSERT INTO public.erecept(
    id, apotekaid, datum_izdavanja, email, ime, prezime, status, trajanje_terapije)
VALUES(1, 1, '2021-04-13', '1pacijent1@gmail.com', 'Marko', 'Markovic', 0, 7);

INSERT INTO public.erecept(
    id, apotekaid, datum_izdavanja, email, ime, prezime, status, trajanje_terapije)
VALUES(2, 2, '2021-03-16', '1pacijent1@gmail.com', 'Marko', 'Markovic', 1, 3);

INSERT INTO public.erecept(
    id, apotekaid, datum_izdavanja, email, ime, prezime, status, trajanje_terapije)
VALUES(3, 1, '2021-05-22', '1pacijent1@gmail.com', 'Marko', 'Markovic', 2, 14);

INSERT INTO public.erecept_lekovi(
    erecept_id, lekovi_id)
VALUES (1, 1);

INSERT INTO public.erecept_lekovi(
    erecept_id, lekovi_id)
VALUES (2, 3);

INSERT INTO public.erecept_lekovi(
    erecept_id, lekovi_id)
VALUES (3, 2);

--ERECEPTI za Anu Stankovic
INSERT INTO public.erecept(
    id, apotekaid, datum_izdavanja, email, ime, prezime, status, trajanje_terapije)
VALUES(4, 2, '2021-04-13', '2pacijent2@gmail.com', 'Ana', 'Stankovic', 0, 10);

INSERT INTO public.erecept(
    id, apotekaid, datum_izdavanja, email, ime, prezime, status, trajanje_terapije)
VALUES(5, 1, '2021-03-16', '2pacijent2@gmail.com', 'Ana', 'Stankovic', 1, 21);

INSERT INTO public.erecept(
    id, apotekaid, datum_izdavanja, email, ime, prezime, status, trajanje_terapije)
VALUES(6, 2, '2021-05-22', '2pacijent2@gmail.com', 'Ana', 'Stankovic', 2, 14);

INSERT INTO public.erecept_lekovi(
    erecept_id, lekovi_id)
VALUES (4, 4);

INSERT INTO public.erecept_lekovi(
    erecept_id, lekovi_id)
VALUES (5, 3);

INSERT INTO public.erecept_lekovi(
    erecept_id, lekovi_id)
VALUES (6, 2);

-- REZERVACIJE za lek za Marka Markovic(pacijent)

INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id, penalized) values (1, null, '2021-05-30 0:00:00', 1, 1, 1, true);
INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id, penalized) values (2, null, '2021-05-31 0:00:00', 1, 3, 1, true);
INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id, penalized) values (3, '2021-06-10 14:12:00', '2021-07-15 0:00:00', 1, 2, 1, false);
INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id, penalized) values (4, '2021-06-10 13:12:00', '2021-07-12 0:00:00', 2, 5, 1, false);
INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id, penalized) values (5, null, '2021-08-11 0:00:00', 2, 5, 1, false);

-- REZERVACIJE za lek za Anu Stankovic(pacijent)

INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id, penalized) values (6, null, '2021-05-12 0:00:00', 2, 1, 2, true);
INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id, penalized) values (7, null, '2021-05-16 0:00:00', 2, 3, 2, true);
INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id, penalized) values (8, '2021-06-16 14:12:00', '2021-07-02 0:00:00', 2, 2, 2, false);
INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id, penalized) values (9, '2021-06-12 13:12:00', '2021-07-05 0:00:00', 1, 5, 2, false);
INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id, penalized) values (10, null, '2021-07-12 0:00:00', 1, 5, 2, false);


-- TimeOff zahtevi (GODISNJI/ODSUSTVO)  - aktivan/prihvacen/odbijen
INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (1, '2021-05-27', '2021-05-15', 'seminar', 1,1, 3);
INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (2, '2021-05-15', '2021-05-12', 'putovanje', 2,1, 3);

INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (3, '2021-02-15', '2021-02-12', 'izlet', 1,0, 4);
INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (4, '2021-03-18', '2021-03-13', 'seminar', 2,1, 4);

INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (5, '2021-07-15', '2021-07-12', 'putovanje', 0,0, 5);
INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (6, '2021-06-24', '2021-06-20', 'seminar', 0,1, 6);

INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (7, '2021-07-28', '2021-08-08', 'letovanje', 0,1, 4);



