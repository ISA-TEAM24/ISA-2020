package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.TimeOffDTO;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.TimeOffZahtev;
import rs.ac.uns.ftn.informatika.rest.repository.TimeOffZahtevRepository;

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
}
