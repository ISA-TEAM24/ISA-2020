package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.Rezervacija;
import rs.ac.uns.ftn.informatika.rest.repository.RezervacijaRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class RezervacijaService {

    @Autowired
    private RezervacijaRepository rezervacijaRepository;

    @Autowired
    private ApotekaService apotekaService;


    public Rezervacija findRezervacijaByID(Long id) {
        Rezervacija r = rezervacijaRepository.findByID(id);

        return r;
    }

    // nadji rezervaciju ako jos nije preuzet(tj nema datum za preuzimanje) i rok za preuzimanje nije kraci od 24h
    public Rezervacija findActiveRezervacijaByID(Long id, String pharmacistUsername) {
        Rezervacija r = rezervacijaRepository.findByID(id);

        if(r == null)
            return null;

        if(!isPharmacistWorkingThere(r.getApoteka().getNaziv(), pharmacistUsername)){
            return null;
        }

        if(r.getDatumPreuz() != null) {
            System.out.println("Rezervacija je vec preuzeta");
            return null;
        }

        if(!isCancellingDateMoreThenOneDay(r.getRokZaPreuzimanje())) {
            System.out.println("================== CANCELLING DATE IS LESS THEN 24 HOURS, sry");
            return null;
        }

        return r;
    }

    // proveri da li farmaceut radi u apoteci gde je rezervacija kreirana
    public boolean isPharmacistWorkingThere(String pharmacy, String pharmacistUsername) {
        Apoteka a = apotekaService.findApotekaByNaziv(pharmacy);

        for(Korisnik k : a.getZaposleni()) {
            if (k.getUsername().equals(pharmacistUsername))
                System.out.println("+++ farmaceut radi u toj apoteci");
                return true;
        }
        System.out.println("+++ farmaceut NE radi u toj apoteci");
        return false;
    }

    public boolean isCancellingDateMoreThenOneDay(Date cancelDate) {
        LocalDate date = createLocalDateFromString(cancelDate);
        LocalDateTime ldt = date.atTime(LocalTime.parse(cancelDate.toString().split(" ")[1]));
        LocalDateTime ldt_now = LocalDateTime.now();

        long ldt_in_seconds = ldt.toEpochSecond(ZoneOffset.UTC);
        long ldt_now_in_seconds = ldt_now.toEpochSecond(ZoneOffset.UTC);
        long seconds_in_24_hours = 24*60*60;
        System.out.println("INPUT <---> NOW");
        System.out.println(ldt + " <---> " + ldt_now);
        System.out.println(ldt_in_seconds - ldt_now_in_seconds);
        if (ldt_in_seconds - ldt_now_in_seconds < seconds_in_24_hours) {
            System.out.println("RETURNED FALSE");
            return false;
        }
        return true;
    }

    private LocalDate createLocalDateFromString(Date d) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datumPosete = dateFormat.format(d);
        LocalDate ret = LocalDate.parse(datumPosete);
        return ret;
    }

}
