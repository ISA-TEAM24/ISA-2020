package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.CalendarDataDTO;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.LoyaltyInfo;
import rs.ac.uns.ftn.informatika.rest.model.Poseta;
import rs.ac.uns.ftn.informatika.rest.repository.LoyaltyInfoRepository;
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
    private LoyaltyInfoRepository loyaltyInfoRepository;

    @Autowired
    private PosetaService posetaService;

    public List<Poseta> findUpcomingVisitsForEmployee(String username) {
        Korisnik employee = korisnikService.findByUsername(username);

        List<Poseta> allVisits = posetaRepository.findPosetaByZaposleniID(employee.getID());
        List<Poseta> retList = new ArrayList<>();

        Date today = new Date();
        LocalTime time = LocalTime.now();

        for(Poseta p : allVisits) {
            if(p.getPacijent() != null && p.getDijagnoza().equals("")) {
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

    // pacijent dobija penal ako se ne pojavi na savetovanju
    public boolean addPenalToPatient(String mail) {
        Korisnik k = korisnikService.findByEmail(mail);

        if(k.getLoyaltyInfo() == null) {
            System.out.println("LOYALTY JE NULL ");
            LoyaltyInfo l = new LoyaltyInfo();
            l.setPenali(1);
            k.setLoyaltyInfo(l);
        } else {

            k.getLoyaltyInfo().setPenali(k.getLoyaltyInfo().getPenali() + 1);

            loyaltyInfoRepository.save(k.getLoyaltyInfo());
            System.out.println(k.getLoyaltyInfo().getPenali() + "***************************");

        }
        return true;

    }

    public boolean removePatientFromVisit(String visitId) {

        Poseta p = posetaRepository.findPosetaByID(Long.parseLong(visitId));
        p.setPacijent(null);

        posetaRepository.save(p);

        return true;
    }

    public List<Poseta> findPredefinedVisitsForDermatologist(Long idVisit) {
        Poseta poseta = posetaRepository.findPosetaByID(idVisit);

        Korisnik employee = korisnikService.findById(poseta.getZaposleni().getID());

        List<Poseta> allVisits = posetaRepository.findPosetaByZaposleniID(employee.getID());
        List<Poseta> retList = new ArrayList<>();

        Date today = new Date();
        LocalTime time = LocalTime.now();

        for(Poseta p : allVisits) {
            if(p.getPacijent() == null && p.getApoteka() == poseta.getApoteka()) {
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

    public List<CalendarDataDTO> getAllVisitsForUser(String name, Long id) {
        List<CalendarDataDTO> dtos = new ArrayList<>();

        List<Poseta> upcomingList = new ArrayList<>();
        upcomingList.addAll(findUpcomingVisitsForEmployee(name));

        List<Poseta> didList = new ArrayList<>();
        didList.addAll(posetaService.getFinishedPoseteByDermOrPharm(name));

        for(Poseta p : didList) {
            if(p.getApoteka().getID() == id) {
                CalendarDataDTO dto = new CalendarDataDTO();
                dto.setPosetaId(p.getID());
                dto.setPacijentIme(p.getPacijent().getIme());
                dto.setPacijentPrezime(p.getPacijent().getPrezime());
                dto.setDatum(p.getDatum().toString().split(" ")[0]);
                dto.setVreme(p.getVreme().toString());
                dto.setTrajanje(p.getTrajanje());
                dto.setApoteka(p.getApoteka().getNaziv());
                dto.setAktivan(false);

                dtos.add(dto);
            }
        }

        for(Poseta p : upcomingList) {
            if(p.getApoteka().getID() == id) {
                CalendarDataDTO dto = new CalendarDataDTO();
                dto.setPosetaId(p.getID());
                dto.setPacijentIme(p.getPacijent().getIme());
                dto.setPacijentPrezime(p.getPacijent().getPrezime());
                dto.setDatum(p.getDatum().toString().split(" ")[0]);
                dto.setVreme(p.getVreme().toString());
                dto.setTrajanje(p.getTrajanje());
                dto.setApoteka(p.getApoteka().getNaziv());
                dto.setAktivan(true);

                dtos.add(dto);
            }
        }

        return dtos;
    }
}
