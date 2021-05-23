select * from korisnik;
insert into authority values (1, 'ROLE_USER');
INSERT INTO public.godisnji_info (id, do_datuma, na_godisnjem, od_datuma) VALUES (1, NULL, false, NULL);
INSERT INTO public.loyalty_info (id, klasa, penali, poeni) VALUES (1, 0, 0, 0);
INSERT INTO public.korisnik (id, activated, adresa, drzava, email, grad, ime, last_password_reset_date, ocena, password, prezime, prvo_logovanje, telefon, username, godisnji_info_id, loyalty_info_id) VALUES (1, true, 'adresica', 'USA', 'mdnnpharm@gmail.com', 'New York', 'iva', '2021-05-23 11:30:17.518', 5, '$2a$10$YWVI64SHppEpbj9dbCzt1OdxtmGJRBR5wC9lhgAVAqcl/IwjuTZb2', 'ivic', true, '51561616', 'test', 1, 1);
INSERT INTO public.korisnik_authorities (korisnik_id, authorities_id) VALUES (1, 1);
INSERT INTO public.korisnik_alergije (korisnik_id, alergije) VALUES (1, 'Aspirin');
INSERT INTO public.korisnik_alergije (korisnik_id, alergije) VALUES (1, 'Brufen');