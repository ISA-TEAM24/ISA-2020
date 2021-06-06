package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.DermatologDTO;
import rs.ac.uns.ftn.informatika.rest.dto.HireDermatologistDTO;
import rs.ac.uns.ftn.informatika.rest.dto.UpitDTO;
import rs.ac.uns.ftn.informatika.rest.dto.UserEditDTO;
import rs.ac.uns.ftn.informatika.rest.model.*;
import rs.ac.uns.ftn.informatika.rest.repository.KorisnikRepository;
import rs.ac.uns.ftn.informatika.rest.repository.UpitRepository;

import java.util.*;

@Service
public class PharmacyAdminService {

    @Autowired
    private KorisnikRepository userRepository;

    @Autowired
    private UpitRepository upitRepository;

    @Autowired
    private ApotekaService apotekaService;

    @Autowired
    private DermatologistService dermatologistService;

    @Autowired
    private PosetaService posetaService;

    public Korisnik findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public Korisnik changeFirstLoginState(String username) {
        Korisnik korisnik = findByUsername(username);
        korisnik.setPrvoLogovanje(false);
        korisnik = this.userRepository.save(korisnik);
        return korisnik;
    }

    public Korisnik editPharmacyAdmin(UserEditDTO user) {
        Korisnik korisnik = findByEmail(user.getEmail());
        korisnik.setIme(user.getName());
        korisnik.setPrezime(user.getLastName());
        korisnik.setAdresa(user.getAddress());
        korisnik.setTelefon(user.getPhoneNumber());
        korisnik.setDrzava(user.getCountry());
        korisnik.setGrad(user.getCity());

        korisnik = this.userRepository.save(korisnik);
        return korisnik;
    }

    public Korisnik findByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    public List<DermatologDTO> createDeramtologDtos(List<Korisnik> dermatologists, Apoteka a) {
        List<DermatologDTO> dermatologDTOS = new ArrayList<>();
        for (Korisnik d : dermatologists) {
            DermatologDTO dermatologDTO = new DermatologDTO();
            dermatologDTO.setIme(d.getIme());
            dermatologDTO.setPrezime(d.getPrezime());
            dermatologDTO.setOcena(d.getOcena());
            dermatologDTO.setUsername(d.getUsername());
            dermatologDTO.setRadiUApotekama(dermatologistService.getMyPharmacies(d.getUsername()));
            if (dermatologDTO.getRadiUApotekama().contains(a)) {
                dermatologDTO.setRadiUMojoj(true);
            } else {
                dermatologDTO.setRadiUMojoj(false);
            }
            dermatologDTOS.add(dermatologDTO);
        }
        return  dermatologDTOS;
    }

    public void hireDermatologist(HireDermatologistDTO dto, Apoteka a) {

        Korisnik k = dermatologistService.findByUsername(dto.getUsername());
        a.getZaposleni().add(k);

        Period p = new Period();
        p.setOdVreme(dto.getOdVreme());
        p.setDoVreme(dto.getDoVreme());
        p.setOdDatum(dto.getOdDatum());
        p.setDoDatum(dto.getDoDatum());
        List<Period> periods = new ArrayList<>();
        periods.add(p);
        RadnoInfo ri = new RadnoInfo();
        ri.setBusinessHours(periods);
        ri.setNeradniDani(dto.getNeradniDani());

        k.getRadnoInfo().put(a.getNaziv(), ri);

        apotekaService.saveApoteka(a);
        userRepository.save(k);
    }

    public List<UpitDTO> getUnscuccessfulQueries(Apoteka apoteka) {
        List<Upit> upiti = upitRepository.findByApotekaAndUspesan(apoteka, false);
        List<UpitDTO> upitiDTO = new ArrayList<>();
        for (Upit u : upiti) {
            UpitDTO dto = new UpitDTO();
            dto.setApoteka(u.getApoteka());
            dto.setId(u.getID().toString());
            dto.setKolicina(u.getKolicina());
            dto.setLek(u.getLek());
            dto.setPosiljalac(u.getPosiljalac());

            upitiDTO.add(dto);
        }

        return upitiDTO;
    }

    public void deleteQuery(Long id) {
        upitRepository.deleteById(id);
    }

    public Map<String, Integer> getMedicineConsumptionByMonth(Apoteka apoteka) {
        List<Upit> prosliUpiti = getPreviousSuccessfullQueriesByPharmacy(apoteka);

        LinkedHashMap<String, Integer> meseci = createMonthsMap();

        for (Upit u : prosliUpiti) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(u.getDatum());

            if (cal.get(Calendar.MONTH) == Calendar.JANUARY) {
                meseci.computeIfPresent("January", (k,v) -> v + u.getKolicina());
            }
            if (cal.get(Calendar.MONTH) == Calendar.FEBRUARY) {
                meseci.computeIfPresent("February", (k,v) -> v+u.getKolicina());
            }
            if (cal.get(Calendar.MONTH) == Calendar.MARCH) {
                meseci.computeIfPresent("March", (k,v) -> v+u.getKolicina());
            }
            if (cal.get(Calendar.MONTH) == Calendar.APRIL) {
                meseci.computeIfPresent("April", (k,v) -> v+u.getKolicina());
            }
            if (cal.get(Calendar.MONTH) == Calendar.MAY) {
                meseci.computeIfPresent("May", (k,v) -> v+u.getKolicina());
            }
            if (cal.get(Calendar.MONTH) == Calendar.JUNE) {
                meseci.computeIfPresent("June", (k,v) -> v+u.getKolicina());
            }
            if (cal.get(Calendar.MONTH) == Calendar.JULY) {
                meseci.computeIfPresent("July", (k,v) -> v+u.getKolicina());
            }
            if (cal.get(Calendar.MONTH) == Calendar.AUGUST) {
                meseci.computeIfPresent("August", (k,v) -> v+u.getKolicina());
            }
            if (cal.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                meseci.computeIfPresent("September", (k,v) -> v+u.getKolicina());
            }
            if (cal.get(Calendar.MONTH) == Calendar.OCTOBER) {
                meseci.computeIfPresent("October", (k,v) -> v+u.getKolicina());
            }
            if (cal.get(Calendar.MONTH) == Calendar.NOVEMBER) {
                meseci.computeIfPresent("November", (k,v) -> v+u.getKolicina());
            }
            if (cal.get(Calendar.MONTH) == Calendar.DECEMBER){
                meseci.computeIfPresent("December", (k,v) -> v+u.getKolicina());
            }
        }

    return meseci;

    }


    public LinkedHashMap<String, Integer> createMonthsMap() {
        LinkedHashMap<String, Integer> meseci = new LinkedHashMap<>();

        meseci.put("January", 0);
        meseci.put("February", 0);
        meseci.put("March", 0);
        meseci.put("April", 0);
        meseci.put("May", 0);
        meseci.put("June", 0);
        meseci.put("July", 0);
        meseci.put("August", 0);
        meseci.put("September", 0);
        meseci.put("October", 0);
        meseci.put("November", 0);
        meseci.put("December", 0);
        return meseci;
    }

    public Map<String, Integer> getIncomeByMonth(Apoteka apoteka) {

        List<Upit> prosliUpiti = getPreviousSuccessfullQueriesByPharmacy(apoteka);

        LinkedHashMap<String, Integer> meseci = createMonthsMap();

        meseci = getIncomeFromMedicines(meseci, prosliUpiti, apoteka);

        List<Poseta> proslePosete = posetaService.getPreviousExaminationsInPharmacy(apoteka);

        meseci = getIncomeFromExaminations(apoteka, meseci, proslePosete);

        return meseci;
    }

    private LinkedHashMap<String, Integer> getIncomeFromExaminations(Apoteka apoteka, LinkedHashMap<String, Integer> meseci, List<Poseta> proslePosete) {
        for (Poseta p : proslePosete) {
            int cenaPregleda = apoteka.getCenovnik().get("PREGLED");
            int cenaSavetovanja = apoteka.getCenovnik().get("SAVETOVANJE");
            int cena;

            if (p.getVrsta() == p.getPREGLED()) {
                cena = cenaPregleda;
            } else {
                cena = cenaSavetovanja;
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(p.getDatum());

            if (cal.get(Calendar.MONTH) == Calendar.JANUARY) {
                meseci.computeIfPresent("January", (k, v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.FEBRUARY) {
                meseci.computeIfPresent("February", (k, v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.MARCH) {
                meseci.computeIfPresent("March", (k, v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.APRIL) {
                meseci.computeIfPresent("April", (k, v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.MAY) {
                meseci.computeIfPresent("May", (k, v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.JUNE) {
                meseci.computeIfPresent("June", (k, v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.JULY) {
                meseci.computeIfPresent("July", (k, v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.AUGUST) {
                meseci.computeIfPresent("August", (k, v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                meseci.computeIfPresent("September", (k, v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.OCTOBER) {
                meseci.computeIfPresent("October", (k, v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.NOVEMBER) {
                meseci.computeIfPresent("November", (k, v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.DECEMBER){
                meseci.computeIfPresent("December", (k, v) -> v + cena);
            }
        }

        return meseci;
    }

    private LinkedHashMap<String, Integer> getIncomeFromMedicines(LinkedHashMap<String, Integer> meseci, List<Upit> prosliUpiti, Apoteka apoteka) {
        for (Upit u : prosliUpiti) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(u.getDatum());

            Lek l = u.getLek();
            int cena;
            if (apoteka.getCenovnik().get(l.getNaziv()) == null) {
                cena = 0;
            } else {
                cena = apoteka.getCenovnik().get(l.getNaziv());
            }

            if (cal.get(Calendar.MONTH) == Calendar.JANUARY) {
                meseci.computeIfPresent("January", (k,v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.FEBRUARY) {
                meseci.computeIfPresent("February", (k,v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.MARCH) {
                meseci.computeIfPresent("March", (k,v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.APRIL) {
                meseci.computeIfPresent("April", (k,v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.MAY) {
                meseci.computeIfPresent("May", (k,v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.JUNE) {
                meseci.computeIfPresent("June", (k,v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.JULY) {
                meseci.computeIfPresent("July", (k,v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.AUGUST) {
                meseci.computeIfPresent("August", (k,v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                meseci.computeIfPresent("September", (k,v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.OCTOBER) {
                meseci.computeIfPresent("October", (k,v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.NOVEMBER) {
                meseci.computeIfPresent("November", (k,v) -> v + cena);
            }
            if (cal.get(Calendar.MONTH) == Calendar.DECEMBER){
                meseci.computeIfPresent("December", (k,v) -> v + cena);
            }
        }
        return meseci;
    }

    private List<Upit> getPreviousSuccessfullQueriesByPharmacy(Apoteka apoteka) {
        List<Upit> sviUpiti = upitRepository.findByApotekaAndUspesan(apoteka, true);
        List<Upit> prosliUpiti = new ArrayList<>();
        for (Upit u : sviUpiti) {
            if (u.getDatum().before(new Date())) {
                prosliUpiti.add(u);
            }
        }
        return prosliUpiti;
    }
}
