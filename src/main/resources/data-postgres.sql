select * from korisnik;
insert into authority values (1, 'ROLE_USER');
insert into authority values (2, 'ROLE_DERMATOLOGIST');
insert into authority values (3, 'ROLE_ADMINISTRATOR');
insert into authority values (4, 'ROLE_PHARMACIST');

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
--INSERT INTO public.apoteka(id, adresa, naziv, ocena, opis) VALUES (1, 'adresaApoteke1', 'apoteka1', 5, 'Najbolja apoteka');
--INSERT INTO public.apoteka_zaposleni(apoteka_id, zaposleni_id) VALUES (1, 4);

--INSERT INTO public.poseta(id, datum, dijagnoza, poeni, trajanje, vreme, vrsta, apoteka_id, pacijent_id, zaposleni_id)
--VALUES (100,  '2021-05-29', '', 5, 30, '19:56', 0, 1, 1, 4);

--INSERT INTO public.apoteka_cenovnik_mapping(
  --  apoteka_id, cena, naziv_predmeta)
--VALUES (1, 66, 'SAVETOVANJE');


-- farmaceut1 - pw: test
insert into authority values (4, 'ROLE_PHARMACIST');
INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (4, true, 'Rakoci Ferenca 22', 'SRB', 'farmaceut123@gmail.com', 'BG', 'Jovan', '2021-05-23 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Jovic', true, '0691100555', 'farmaceut1', NULL, NULL);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (4, 4);

-- TimeOff zahtevi
INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (20, '2021-05-27', '2021-05-15', 'seminar', 1,1, 3);
INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (21, '2021-03-12', '2021-05-15', 'putovanje', 2,1, 3);

INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (22, '2021-01-12', '2021-02-15', 'putovanje', 1,0, 4);
INSERT INTO public.time_off_zahtev(id, do_datuma, od_datuma, razlog, stanje_zahteva, vrsta, podnosilac_id) values (23, '2021-03-12', '2021-03-18', 'putovanje', 2,1, 4);

