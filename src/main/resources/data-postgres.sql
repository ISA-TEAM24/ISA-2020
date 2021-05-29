select * from korisnik;
insert into authority values (1, 'ROLE_USER');
insert into authority values (2, 'ROLE_DERMATOLOGIST');
insert into authority values (3, 'ROLE_ADMINISTRATOR');
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

-- farmaceut1 - pw: test
insert into authority values (4, 'ROLE_PHARMACIST');
INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (4, true, 'Rakoci Ferenca 22', 'SRB', 'farmaceut123@gmail.com', 'BG', 'Jovan', '2021-05-23 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Jovic', true, '0691100555', 'farmaceut1', NULL, NULL);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (4, 4);

-- admin apoteke: - pw: test
insert into authority values (5, 'ROLE_PH_ADMIN');
INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (5, true, 'Prvomajska 20', 'Srbija', 'phadmin123@gmail.com', 'Backa Topola', 'Danilo', '2021-05-23 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'Paripovic', false, '066451299', 'phadmin', NULL, NULL);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (5, 5);