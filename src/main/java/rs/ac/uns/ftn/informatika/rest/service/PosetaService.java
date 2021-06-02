package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.informatika.rest.dto.DateTimeDTO;
import rs.ac.uns.ftn.informatika.rest.dto.NewPosetaDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PosetaDTO;
import rs.ac.uns.ftn.informatika.rest.dto.ScheduleDTO;
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

        if(p.getVrsta() == p.getPREGLED()){
            p.setPacijent(null);
            return 0;
        }

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

        for (Poseta p : all_visits) {
            if (p.getDatum().after(new Date())) {
                return false;
            }
        }
        return true;
    }

    public List<Poseta> getFinishedPoseteByDermOrPharm(String username) {
        Korisnik worker = korisnikService.findByUsername(username);

        List<Poseta> posetaList = posetaRepository.findPosetaByZaposleniID(worker.getID());
        Date today = new Date();

        List<Poseta> retList = new ArrayList<Poseta>();

        for(Poseta p : posetaList) {
            System.out.println("---- TODAY IS: " + today);
            System.out.println("---- POSETA WAS ON : " + p.getDatum());

            // posetas date occurs before todays date
            if (p.getDatum().compareTo(today) < 0) {
                System.out.println("bio je pre");

                retList.add(p);
            } else {

                System.out.println("tek ce biti");

            }
        }

        return retList;

    }

    public List<Poseta> findUpcomingVisitsForDermatologInPharmacy(Korisnik user, Apoteka a) {
        List<Poseta> visits = posetaRepository.findPosetaByZaposleniID(user.getID());
        List<Poseta> filtered_visits = new ArrayList<>();

        for (Poseta p : visits) {
            if (p.getApoteka().equals(a) && p.getDatum().after(new Date())) {
                filtered_visits.add(p);
            }
        }
        return filtered_visits;
    }

    public void createNewAppointment(NewPosetaDTO dto, Apoteka a) {
        Poseta poseta = new Poseta();
        Korisnik dermatolog = korisnikService.findByUsername(dto.getDermatologist());
        poseta.setApoteka(a);
        poseta.setZaposleni(dermatolog);
        poseta.setDatum(dto.getDate());
        poseta.setVreme(dto.getTime());
        poseta.setTrajanje(dto.getDuration());
        poseta.setVrsta(poseta.getPREGLED());
        poseta.setDijagnoza(null);
        poseta.setPoeni(0);
        poseta.setPacijent(null);

        posetaRepository.save(poseta);
    }

    public int scheduleConsultByPharmacist(String pharmacistUsername, ScheduleDTO dto) throws ParseException {
        Korisnik pharmacist = korisnikService.findByUsername(pharmacistUsername);
        Poseta p = new Poseta();

        DateTimeDTO dt = new DateTimeDTO();
        dt.setDate(dto.getDatum());
        dt.setTime(dto.getVreme());

        Apoteka a = apotekaService.findByZaposleni(pharmacist);
        Korisnik pacijent = korisnikService.findByEmail(dto.getEmail());

        if(pacijent == null) {
            System.out.println("--- Ne postoji pacijent sa zadatim mailom");
            return 2;
        }

        //ako je farmaceut slobodan ...
        if(apotekaService.checkIfPharmacistIsFree(pharmacist, dt.parseDateStringToDate(), dt.parseTimeStringToLocalTime(), a)
           && checkIfUserIsFree(pacijent.getUsername(), dto.getDatum(), dto.getVreme(),dto.getTrajanje())) {
            System.out.println("FARMACEUT JE FREE ===============");

            p.setApoteka(a);
            p.setDijagnoza("");
            p.setTrajanje(dto.getTrajanje());
            p.setVrsta(p.getSAVETOVANJE());
            p.setPoeni(5);
            p.setPacijent(pacijent);
            p.setZaposleni(pharmacist);
            p.setDatum(dt.parseDateStringToDate());
            p.setVreme(dt.parseTimeStringToLocalTime());

            posetaRepository.save(p);

            //send mail
            Format formatter = new SimpleDateFormat("dd-MM-YYYY");
            String s = formatter.format(p.getDatum());

            String msg = "Dear " + pacijent.getIme() + " " + pacijent.getPrezime();
            msg += ", We've successfully scheduled your consult. Consult information: ";
            msg += "Pharmacist: " + pharmacist.getIme() +  " " + pharmacist.getPrezime();
            msg += " // ";
            msg += " Pharmacy: " + a.getNaziv() + ", " + a.getAdresa() + " // ";
            msg += " When: " + s + " at " + p.getVreme();
            boolean inform =  emailService.sendSimpleMessage(pacijent.getEmail(), "New scheduled consult", msg);
        } else {
            return 0;
        }

        return 1;
    }

    public boolean checkIfUserIsFree(String username, String datum, String vreme, int trajanje) {
        List<Poseta> upcoming = findUpcomingVisitsForUser(username);

        DateTimeDTO dt = new DateTimeDTO();
        dt.setDate(datum);
        dt.setTime(vreme);

        LocalTime newPosetaStart = dt.parseTimeStringToLocalTime();
        LocalTime newPosetaEnd = dt.parseTimeStringToLocalTime().plusMinutes(trajanje);

        for(Poseta p: upcoming) {
            if(p.getDatum().toString().split(" ")[0].equals(datum)){
                System.out.println("DATUMI SU JEDNAKI: " + p.getDatum().toString().split(" ")[0] + " == " + datum);

                LocalTime startTimePoseta = p.getVreme();
                LocalTime endTimePoseta = p.getVreme().plusMinutes(p.getTrajanje());

                if(newPosetaStart.compareTo(endTimePoseta) < 0)
                {
                    if(newPosetaStart.compareTo(startTimePoseta) < 0
                        && newPosetaEnd.compareTo(startTimePoseta)< 0) {
                        //OK

                        continue;
                    } else {
                        //BAD
                        System.out.println("FIRST fail");
                        return false;
                    }
                }

                if(newPosetaStart.compareTo(startTimePoseta) > 0) {
                    if(newPosetaStart.compareTo(endTimePoseta) > 0) {
                        // OK
                        continue;
                    } else {
                        // BAD
                        System.out.println("SECOND fail");
                        return false;
                    }
                }
            }

            return true;
        }
        return true;

    }

    public boolean isPatientFreeForExam(Poseta e, Korisnik k) {
        List<Poseta> upcoming = findUpcomingVisitsForUser(k.getUsername());
        LocalTime wanted_end = e.getVreme().plusMinutes(e.getTrajanje());

        for(Poseta p : upcoming) {
            System.out.println("isPatientFree comparing dates " + p.getDatum() + " // " + e.getDatum());
            if(p.getDatum().compareTo(e.getDatum()) != 0) {
                System.out.println("continued");
                continue;
            }


            LocalTime current_end = p.getVreme().plusMinutes(p.getTrajanje());
            System.out.println("CURRENT TIME " + p.getVreme().toString() + " -- " + current_end.toString());
            System.out.println("WANTED TIME " + e.getVreme().toString() + " -- " + wanted_end.toString());
            // wanted_start - wanted_end   // current_start - current_end
            // ili pocetak ili kraj zeljenog termina ako je unutar nekog termina onda je zauzet
            if(e.getVreme().compareTo(p.getVreme()) >= 0 || e.getVreme().compareTo(current_end) < 0)
                return false;
            if(wanted_end.compareTo(p.getVreme()) > 0 || wanted_end.compareTo(current_end) <= 0)
                return false;

        }
        return true;
    }

    public boolean addExam(Long appID, String username) {

        Poseta p = posetaRepository.findPosetaByID(appID);
        Korisnik k = korisnikService.findByUsername(username);

        if (!isPatientFreeForExam(p, k)) return false;

        p.setPacijent(k);
        posetaRepository.save(p);

        return true;
    }
}
