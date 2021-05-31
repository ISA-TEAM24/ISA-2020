package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.informatika.rest.dto.DateTimeDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PosetaDTO;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.Poseta;
import rs.ac.uns.ftn.informatika.rest.repository.PosetaRepository;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PosetaService {

    @Autowired
    private PosetaRepository posetaRepository;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private ApotekaService apotekaService;

    @Autowired
    private EmailService emailService;

    public boolean addConsult(PosetaDTO dto, String pacijent) throws ParseException {

        Poseta p = new Poseta();
        Apoteka a = apotekaService.findApotekaByNaziv(dto.getApoteka());
        p.setApoteka(a);
        p.setDijagnoza("");
        p.setTrajanje(dto.getTrajanje());
        p.setVrsta(p.getSAVETOVANJE());
        p.setPoeni(dto.getPoeni());
        p.setTrajanje(dto.getTrajanje());
        Korisnik k = korisnikService.findByUsername(pacijent);
        p.setPacijent(k);
        p.setApoteka(apotekaService.findApotekaByNaziv(dto.getApoteka()));
        Korisnik z = korisnikService.findByUsername(dto.getZaposleni());
        p.setZaposleni(z);

        DateTimeDTO dt = new DateTimeDTO();
        dt.setDate(dto.getDatum());
        dt.setTime(dto.getVreme());
        p.setDatum(dt.parseDateStringToDate());
        p.setVreme(dt.parseTimeStringToLocalTime());
        posetaRepository.save(p);
        Format formatter = new SimpleDateFormat("dd-MM-YYYY");
        String s = formatter.format(p.getDatum());
        String text = "Hello " + pacijent;
        text += ". We've successfully scheduled your consult. Consult information: ";
        text += "Pharmacist: " + z.getIme() +  " " + z.getPrezime();
        text += " // ";
        text += " Pharmacy: " + a.getNaziv() + ", " + a.getAdresa() + " // ";
        text += " When: " + s + " at " + p.getVreme();
        boolean inform =  emailService.sendSimpleMessage(k.getEmail(), "New Consult", text);


        return true;
    }

    public List<Poseta> findUpcomingVisitsForUser(String name) {

        Korisnik k = korisnikService.findByUsername(name);
        List<Poseta> all_visits = posetaRepository.findPosetaByPacijentID(k.getID());
        List<Poseta> filtered_visits = new ArrayList<>();

        for(Poseta p : all_visits) {
            if (p.getDatum().after(new Date())) {
                filtered_visits.add(p);
            }
        }
        return filtered_visits;

    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int cancelVisit(Long id) {

        System.out.println("??????????????PosetaServis trying to find Poseta with ID " + id);
        Poseta p = posetaRepository.findPosetaByID(id);
        if (p == null)
            return -1;


        //posetaRepository.deletePosetaByID(id);
        LocalDate date = createLocalDateFromString(p.getDatum());
        LocalDateTime ldt = date.atTime(p.getVreme());
        LocalDateTime ldt_now = LocalDateTime.now();
        long ldt_in_seconds = ldt.toEpochSecond(ZoneOffset.UTC);
        long ldt_now_in_seconds = ldt_now.toEpochSecond(ZoneOffset.UTC);
        long seconds_in_24_hours = 24*60*60;
        System.out.println("INPUT // NOW");
        System.out.println(ldt + " // " + ldt_now);
        System.out.println(ldt_in_seconds - ldt_now_in_seconds);
        if (ldt_in_seconds - ldt_now_in_seconds < seconds_in_24_hours)
            return 1;

        posetaRepository.deletePosetaByID(id);
        if (posetaRepository.findPosetaByID(id) == null)
            System.out.println("Poseta deleted");
        return 0;

    }

    private LocalDate createLocalDateFromString(Date d) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datumPosete = dateFormat.format(d);
        LocalDate ret = LocalDate.parse(datumPosete);
        return ret;

    }

    public boolean fireAllowed(String username) {
        Korisnik k = korisnikService.findByUsername(username);
        List<Poseta> all_visits = posetaRepository.findPosetaByZaposleniID(k.getID());

        for(Poseta p : all_visits) {
            if (p.getDatum().after(new Date())) {
                return false;
            }
        }
        return true;
    }
}
