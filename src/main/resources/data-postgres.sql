select * from korisnik;
insert into authority values (1, 'ROLE_USER');
insert into authority values (2, 'ROLE_DERMATOLOGIST');
insert into authority values (3, 'ROLE_ADMINISTRATOR');
insert into authority values (4, 'ROLE_PHARMACIST');
insert into authority values (5, 'ROLE_PH_ADMIN');

INSERT INTO public.godisnji_info (id, do_datuma, na_godisnjem, od_datuma) VALUES (1, NULL, false, NULL);
INSERT INTO public.loyalty_info (id, klasa, penali, poeni) VALUES (1, 0, 0, 0);
INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (1, true, 'adresica', 'USA', 'mdnnpharm@gmail.com', 'New York', 'iva', '2021-05-23 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'ivic', true, '51561616', 'test', 1, 1);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (1, 1);
INSERT INTO public.korisnik_alergije (korisnik_id, alergije) VALUES (1, 'Aspirin');
INSERT INTO public.korisnik_alergije (korisnik_id, alergije) VALUES (1, 'Brufen');

-- admninUsername - pw:test
INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (2, true, 'adresaAdmina', 'SRB', 'admin@gmail.com', 'BG', 'peroAdmin', '2021-05-23 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Peruncic', true, '11232', 'adminUsername', NULL, NULL);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (2, 3);

-- dermUsername - pw: test
INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (3, true, 'adresaDerm', 'SRB', 'derm99933211@gmail.com', 'BG', 'Predrag', '2021-05-23 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Kon', true, '11244432', 'dermUsername', NULL, NULL);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (3, 2);

INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (9, true, 'adresaaDerm', 'SRB', 'derm9993a3211@gmail.com', 'BG', 'Zika', '2021-05-23 11:30:17.518', 3, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Zoc', true, '105644432', 'de321rmUsername', NULL, NULL);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (9, 2);

INSERT INTO public.apoteka(id, adresa, naziv, ocena, opis) VALUES (7, 'adresaApoteke11', 'BENU', 4, 'Najbolja apdsaoteka');
INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (7, 9);

INSERT INTO public.radno_info(id) VALUES (1);
INSERT INTO public.korisnik_radno_info(korisnik_id, radno_info_id, radno_info_key) VALUES (9, 1, 'BENU');
INSERT INTO public.period(id, do_datum, do_vreme, od_datum, od_vreme) VALUES (1, '2021-06-27', '20:00', '2021-05-27', '12:00');
INSERT INTO public.period(id, do_datum, do_vreme, od_datum, od_vreme) VALUES (2, '2021-07-27', '16:00', '2021-6-28', '07:00');
INSERT INTO public.radno_info_business_hours(radno_info_id, business_hours_id) VALUES (1, 1);
INSERT INTO public.radno_info_business_hours(radno_info_id, business_hours_id) VALUES (1, 2);
INSERT INTO public.radno_info_neradni_dani(radno_info_id, neradni_dani) VALUES (1, null);

--skripte za test farmaceuta
--INSERT INTO public.godisnji_info (id, do_datuma, na_godisnjem, od_datuma) VALUES (4, NULL, false, NULL);
--INSERT INTO public.period(id, do_datum, do_vreme, od_datum, od_vreme) VALUES (4, '2021-08-23', '19:30', '2021-03-23',  '11:30');
--INSERT INTO public.radno_info(id) VALUES (4);
--INSERT INTO public.radno_info_business_hours(radno_info_id, business_hours_id) VALUES (4, 4);
--INSERT INTO public.radno_info_neradni_dani(radno_info_id, neradni_dani) VALUES (4, 1);
--INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (4, true, 'adresaFarm', 'SRB', 'farmaceut@gmail.com', 'BG', 'peroFarmaceut', '2021-05-23 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Peruncic', true, '1515623', 'farmacistUsername', 4, NULL);
--INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (4, 4);
--INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (4, 1);

--INSERT INTO public.korisnik_radno_info(korisnik_id, radno_info_id, radno_info_key) VALUES (4, 4, 'apoteka1');
-- skripte za apoteke
-- a1
INSERT INTO public.apoteka(id, adresa, naziv, ocena, opis) VALUES (1, 'adresaApoteke1', 'apoteka1', 5, 'Najbolja apoteka');


INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (13, true, 'adresaDedsrm', 'SRsadB', 'derm9993das3211@gmail.com', 'BGdas', 'Nenad', '2021-05-23 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Zvrk', true, '112434432', 'dermUs4ername', NULL, NULL);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (13, 2);
INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (1, 13);

--skripte za dodavanje termina dermatologu (apoteka1):

INSERT INTO public.radno_info(id) VALUES (5);
INSERT INTO public.korisnik_radno_info(korisnik_id, radno_info_id, radno_info_key) VALUES (13, 5, 'apoteka1');
INSERT INTO public.period(id, do_datum, do_vreme, od_datum, od_vreme) VALUES (300, '2021-07-31', '20:00', '2021-05-27', '12:00');
INSERT INTO public.radno_info_business_hours(radno_info_id, business_hours_id) VALUES (5, 300);
INSERT INTO public.radno_info_neradni_dani(radno_info_id, neradni_dani) VALUES (5, null);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id) VALUES (22, '2021-06-03', null, 0, 30, '14:00', 1, 1, null, 13);
INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id) VALUES (23, '2021-06-04', null, 0, 30, '15:00', 1, 1, null, 13);
INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id) VALUES (24, '2021-06-05', null, 0, 30, '16:00', 1, 1, null, 13);
INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id) VALUES (25, '2021-06-06', null, 0, 30, '17:00', 1, 1, null, 13);


--INSERT INTO public.apoteka_cenovnik_mapping(
  --  apoteka_id, cena, naziv_predmeta)
--VALUES (1, 66, 'SAVETOVANJE');


-- farmaceut1 - pw: test

--insert into authority values (4, 'ROLE_PHARMACIST');
INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (4, true, 'Rakoci Ferenca 22', 'SRB', 'farmaceut123@gmail.com', 'BG', 'Boban', '2021-05-23 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Bobic', true, '0691100555', 'farmaceut1', NULL, NULL);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (4, 4);
INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (1, 4);

INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (6, true, 'Rakoci Ferenca 32', 'SRB', 'farmaceut1234@gmail.com', 'BG', 'Jovanko', '2021-05-23 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Jovicic', true, '0691100525', 'farmaceut2', NULL, NULL);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (6, 4);
INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (1, 6);

INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (7, true, 'Rakoci Ferenca 12', 'SRB', 'farmaceut12345@gmail.com', 'BG', 'Arsen', '2021-05-23 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Zdravkovic', true, '0611100525', 'farmaceut23', NULL, NULL);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (7, 4);
INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (1, 7);

-- admin apoteke: - pw: test
INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (5, true, 'Prvomajska 20', 'Srbija', 'phadmin123@gmail.com', 'Backa Topola', 'Danilo', '2021-05-23 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Paripovic', false, '066451299', 'phadmin', NULL, NULL);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (5, 5);
INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (1, 5);

-- TimeOff zahtevi
INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (20, '2021-05-27', '2021-05-15', 'seminar', 1,1, 3);
INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (21, '2021-03-12', '2021-05-15', 'putovanje', 2,1, 3);

INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (22, '2021-01-12', '2021-02-15', 'putovanje', 1,0, 4);
INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (23, '2021-03-12', '2021-03-18', 'putovanje', 2,1, 4);


INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (100,  '2021-06-25', '', 5, 30, '19:56', 0, 1, 1, 4);

-- Rezervacije test
INSERT INTO public.apoteka(id, adresa, naziv, ocena, opis) VALUES (2, 'adresaApoteke2', 'apoteka2', 5, 'Najbolja apoteka2');
INSERT INTO public.lek(id, na_recept, napomene, naziv, oblik, ocena, poeni, proizvodjac, sastav, vrsta) values (55, true, 'napomena1', 'Bromazepan', 'tableta', 5, 2, 'Hemofarm', 'sastav', 'vrsta' );
INSERT INTO public.lek(id, na_recept, napomene, naziv, oblik, ocena, poeni, proizvodjac, sastav, vrsta) values (56, true, 'napomena2', 'Rapidol', 'tableta', 4, 3, 'Hemofarm', 'sastav', 'vrsta' );
INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id) values (155, null, '2021-05-30 0:00:00', 1, 55, 1);
INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id) values (156, null, '2021-05-31 0:00:00', 1, 55, 1);
INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id) values (157, '2021-06-10 14:12:00', '2021-06-15 0:00:00', 1, 56, 1);
INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id) values (158, '2021-06-10 13:12:00', '2021-06-12 0:00:00', 2, 56, 1);
INSERT INTO public.rezervacija(id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id) values (159, null, '2021-06-12 0:00:00', 2, 56, 1);



INSERT INTO public.apoteka_magacin_mapping(apoteka_id, kolicina, id_leka) VALUES (1, 50, 55);
INSERT INTO public.apoteka_magacin_mapping(apoteka_id, kolicina, id_leka) VALUES (2, 50, 55);
INSERT INTO public.apoteka_magacin_mapping(apoteka_id, kolicina, id_leka) VALUES (1, 50, 56);
INSERT INTO public.apoteka_magacin_mapping(apoteka_id, kolicina, id_leka) VALUES (2, 50, 56);
--INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (1, 4);  -- farmaceut radi u apoteci1

--Pretraga korisnika - istorija poseta (Derm, Farm)
INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (1, 3);  --Dermatolog radi u apoteci1

INSERT INTO public.loyalty_info (id, klasa, penali, poeni) VALUES (2, 0, 0, 0);
INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (250, true, 'adresica', 'USA', 'pswtim24@gmail.com', 'New York', 'Jovan', '2021-05-22 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Jovic', true, '321', 'pacijent2', null, 2);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (250, 1);

--farmaceutovi pregledi
INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (500,  '2021-05-29', 'A00 B99', 5, 30, '08:00', 0, 1, 1, 4);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (501,  '2021-06-29', '', 5, 30, '09:30', 0, 1, 250, 4);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (502,  '2021-05-12', 'L00 L99', 5, 30, '07:30', 0, 1, 250, 4);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (503,  '2021-04-10', 'S00 T98', 5, 30, '10:00', 0, 1, 1, 4);

--pregledi dermatologa

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (504,  '2021-05-29', 'A00 B99', 5, 30, '08:00', 1, 1, 250, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (505,  '2021-06-29', 'H00 H59', 5, 30, '12:30', 1, 1, 1, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (506,  '2021-05-12', 'L00 L99', 5, 30, '07:30', 1, 1, 1, 3);

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
VALUES (507,  '2021-04-10', 'S00 T98', 5, 30, '10:00', 1, 1, 1, 3);


--Pharmacist scheduling test
INSERT INTO public.godisnji_info (id, do_datuma, na_godisnjem, od_datuma) VALUES (4, NULL, false, NULL);
INSERT INTO public.period(id, do_datum, do_vreme, od_datum, od_vreme) VALUES (4, '2021-08-23', '19:30', '2021-03-23',  '11:30');
INSERT INTO public.radno_info(id) VALUES (4);
INSERT INTO public.radno_info_business_hours(radno_info_id, business_hours_id) VALUES (4, 4);
INSERT INTO public.radno_info_neradni_dani(radno_info_id, neradni_dani) VALUES (4, 1);

INSERT INTO public.korisnik_radno_info(korisnik_id, radno_info_id, radno_info_key) VALUES (4, 4, 'apoteka1');

INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
    VALUES (555,  '2021-05-29', '', 5, 30, '19:56', 0, 1, 1, 4);

INSERT INTO public.apoteka_cenovnik_mapping(
  apoteka_id, cena, naziv_predmeta)
    VALUES (1, 66, 'SAVETOVANJE');

