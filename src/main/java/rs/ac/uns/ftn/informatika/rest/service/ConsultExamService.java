package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.Poseta;
import rs.ac.uns.ftn.informatika.rest.repository.PosetaRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class ConsultExamService {

    @Autowired
    private PosetaRepository posetaRepository;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private ApotekaService apotekaService;

    public List<Poseta> findUpcomingVisitsForEmployee(String username) {
        Korisnik employee = korisnikService.findByUsername(username);

        List<Poseta> allVisits = posetaRepository.findPosetaByZaposleniID(employee.getID());
        List<Poseta> retList = new ArrayList<>();

        Date today = new Date();
        LocalTime time = LocalTime.now();

        for(Poseta p : allVisits) {
            if(p.getPacijent() != null) {
                if (p.getDatum().compareTo(today) > 0) {
                    retList.add(p);
                }
                if (p.getDatum().compareTo(today) == 0) {
                    if (p.getVreme().compareTo(time) > 0) {
                        retList.add(p);
                    }
                }
            }
        }

        retList.sort(Comparator.comparing(Poseta::getDatum));

        return retList;
    }

    public Poseta savePosetaReport(Long id, String dijagnoza) {
        Poseta p = posetaRepository.findPosetaByID(id);

        p.setDijagnoza(dijagnoza);

        Poseta ret = posetaRepository.save(p);

        return ret;
    }

    public Poseta getPosetaByID(String id) {
        return posetaRepository.findPosetaByID(Long.parseLong(id));
    }

}
