package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.TOffDTO;
import rs.ac.uns.ftn.informatika.rest.dto.TimeOffDTO;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.TimeOffZahtev;
import rs.ac.uns.ftn.informatika.rest.repository.TimeOffZahtevRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Service
public class TimeOffRequestService {

    @Autowired
    private TimeOffZahtevRepository timeOffZahtevRepository;

    @Autowired
    private KorisnikService korisnikService;

    public List<TimeOffZahtev> findAllTimeOffRequests() {
        List<TimeOffZahtev> result = timeOffZahtevRepository.findAll();
        return result;
    }

    public List<TimeOffZahtev> findAllTimeOffRequestsByPodnosilac(Korisnik k) {
        List<TimeOffZahtev> result = timeOffZahtevRepository.findAllByPodnosilac(k);
        return result;
    }

    public TimeOffZahtev saveNewTimeOffZahtev(String username, TimeOffDTO req) throws Exception {
        TimeOffZahtev t = new TimeOffZahtev();

        t.setOdDatuma(req.getOdDatuma());
        t.setDoDatuma(req.getDoDatuma());
        t.setRazlog(req.getRazlog());
        // Novo poslat zahtev je uvek aktivan dok se ne prihvati/odbije
        t.setStanjeZahteva(TimeOffZahtev.Stanje.AKTIVAN);
        if(req.getVrsta().equals("Odsustvo")) {
            t.setVrsta(TimeOffZahtev.Vrsta.ODSUSTVO);
        } else {
            t.setVrsta(TimeOffZahtev.Vrsta.GODISNJI);
        }

        Korisnik k = korisnikService.findByUsername(username);
        t.setPodnosilac(k);

        t = this.timeOffZahtevRepository.save(t);
        return t;
    }

    public List<TimeOffZahtev> getAllMyRequests(String username) {
        Korisnik k = korisnikService.findByUsername(username);
        List<TimeOffZahtev> requestList = timeOffZahtevRepository.findAllByPodnosilac(k);

        return requestList;
    }

    public List<TOffDTO> getAllTimeOffsByPharmacy(Apoteka a) {
        List<TimeOffZahtev> all = findAllTimeOffRequests();
        List<TimeOffZahtev> myWorkers = new ArrayList<>();
        List<Korisnik> users = a.getZaposleni();
        for (TimeOffZahtev tz : all) {
            if (users.contains(tz.getPodnosilac())) {
                myWorkers.add(tz);
            }
        }

        List<TOffDTO> dtos = new ArrayList<>();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");

        for (TimeOffZahtev tz : myWorkers) {
            TOffDTO dto = new TOffDTO();
            dto.setId(tz.getID().toString());
            dto.setIme(tz.getPodnosilac().getIme());
            dto.setPrezime(tz.getPodnosilac().getPrezime());

            for (GrantedAuthority auth : tz.getPodnosilac().getAuthorities()) {
                if (auth.getAuthority().equalsIgnoreCase("ROLE_PHARMACIST")) {
                    dto.setUloga("Pharmacist");
                } else {
                    dto.setUloga("Dermatologist");
                }
            }
            dto.setOdDatuma(dateFormat.format(tz.getOdDatuma()));
            dto.setDoDatuma(dateFormat.format(tz.getDoDatuma()));
            dto.setRazlog(tz.getRazlog());
            dto.setVrsta(tz.getVrsta().toString());

            dtos.add(dto);
        }
        return dtos;
    }
}
