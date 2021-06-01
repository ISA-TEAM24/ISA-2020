package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.ApotekaEditDTO;
import rs.ac.uns.ftn.informatika.rest.dto.ApotekaWithExamsDTO;
import rs.ac.uns.ftn.informatika.rest.dto.ApotekaWithMedicineDto;
import rs.ac.uns.ftn.informatika.rest.dto.FarmaceutDTO;
import rs.ac.uns.ftn.informatika.rest.dto.IdDTO;
import rs.ac.uns.ftn.informatika.rest.model.*;
import rs.ac.uns.ftn.informatika.rest.repository.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class ApotekaService {

    @Autowired
    private ApotekaRepository apotekaRepository;

    @Autowired
    private PosetaRepository posetaRepository;

    @Autowired
    protected LekRepository lekRepository;

    @Autowired
    private RezervacijaRepository rezervacijaRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    public List<Apoteka> findAll() {
        return this.apotekaRepository.findAll();
    }

    public List<Korisnik> findPharmacists(Apoteka a) {
        ArrayList<Korisnik> farmaceuti = new ArrayList<>();
        for(Korisnik k : a.getZaposleni()) {
            boolean isPharmacist = false;
            for (GrantedAuthority auth : k.getAuthorities()) {
                if (!auth.getAuthority().equalsIgnoreCase("ROLE_PHARMACIST"))
                    continue;

                isPharmacist = true;
            }
            if (isPharmacist) farmaceuti.add(k);
        }
        return farmaceuti;
    }

    public boolean checkIfPharmacistIsFree(Korisnik k, Date date, LocalTime time, Apoteka a) {

        System.out.println("IN CHECK IF PHARMACIST IS FREE");
        List<Poseta> posete = posetaRepository.findPosetaByZaposleniID(k.getID());
        RadnoInfo ri = k.getRadnoInfo().get(a.getNaziv());
        if (ri == null) {
            System.out.println("RADNO INFO IS NULL");
        }

        if (!ri.isDateInRange(date, time))
            return false;

        System.out.println("DATES ARE NOT THE SAME");

        for (Poseta p : posete) {
             if (p.isSlotTaken(date,time))
                 return false;
        }

        System.out.println("TIME IS OUT OF RANGE");

        return true;

    }

    public List<ApotekaWithExamsDTO> findApotekaWithFreePharmacist(Date date, LocalTime time) {
        // uzmi sve apoteke
        List<Apoteka> pharmacies = this.apotekaRepository.findAll();
        // nadji sve farmaceute koji su slobodni
        List<ApotekaWithExamsDTO> retList = new ArrayList<>();
        for(Apoteka a : pharmacies) {
            List<Korisnik> farmaceuti = findPharmacists(a);
            System.out.println("FARMACEUTI ZA APOTEKU " + a.getNaziv());
            System.out.println(farmaceuti.size());
            ApotekaWithExamsDTO dto = new ApotekaWithExamsDTO();
            dto.setNaziv(a.getNaziv());
            dto.setAdresa(a.getAdresa());
            dto.setCenaSavetovanja(a.getCenovnik().get("SAVETOVANJE"));
            //dto.setCenaSavetovanja(50);
            dto.setOcena(a.getOcena());
            dto.setFarmaceuti(new ArrayList<>());
            for (Korisnik farm : farmaceuti) {
                if (farm.getGodisnjiInfo().isNaGodisnjem())
                    continue;
                if (checkIfPharmacistIsFree(farm, date, time, a)) {
                    FarmaceutDTO f = new FarmaceutDTO();
                    f.setIme(farm.getIme());
                    f.setPrezime(farm.getPrezime());
                    f.setUsername(farm.getUsername());
                    f.setOcena(farm.getOcena());
                    dto.getFarmaceuti().add(f);
                }

            }
            if (dto.getFarmaceuti().size() > 0)
                retList.add(dto);
        }

        return retList;
    }

    public Apoteka findApotekaByNaziv(String naziv) {
        return apotekaRepository.findByNaziv(naziv);
    }


    public Apoteka getPharmacyByAdmin(String username) {
        List<Apoteka> pharmacies = this.apotekaRepository.findAll();
        for (Apoteka a : pharmacies) {
            List<Korisnik> pharmacyAdmins = findPharmacyAdmin(a);
            for (Korisnik phadmin : pharmacyAdmins) {
                if (phadmin.getUsername().equalsIgnoreCase(username)) {
                    return a;
                }
            }
        }
        return null;
    }

    private List<Korisnik> findPharmacyAdmin(Apoteka a) {
        ArrayList<Korisnik> pharmacyAdmins = new ArrayList<>();
        for(Korisnik k : a.getZaposleni()) {
            boolean isPharmacyAdmin = false;
            for (GrantedAuthority auth : k.getAuthorities()) {
                if (!auth.getAuthority().equalsIgnoreCase("ROLE_PH_ADMIN"))
                    continue;
                isPharmacyAdmin = true;
            }
            if (isPharmacyAdmin)
                pharmacyAdmins.add(k);
        }
        return pharmacyAdmins;
    }

    public boolean editPharmacy(ApotekaEditDTO apotekaEditDTO) {
        Apoteka apoteka = apotekaRepository.findByNaziv(apotekaEditDTO.getStarinaziv());

        if (apotekaEditDTO.isMenjannaziv() && apotekaRepository.findByNaziv(apotekaEditDTO.getNaziv()) != null) {
            return false;
        }

        apoteka.setNaziv(apotekaEditDTO.getNaziv());
        apoteka.setAdresa(apotekaEditDTO.getAdresa());
        apoteka.setOpis(apotekaEditDTO.getOpis());

        apotekaRepository.save(apoteka);

        return true;
    }

    public void hirePharmacist(Korisnik user, String naziv) {
        Apoteka a = apotekaRepository.findByNaziv(naziv);
        List<Korisnik> zaposleni = a.getZaposleni();
        zaposleni.add(user);
        a.setZaposleni(zaposleni);
        apotekaRepository.save(a);
    }


    public List<ApotekaWithMedicineDto> findPharmaciesWithMedicine(IdDTO dto, String username) {

        Korisnik k = korisnikRepository.findByUsername(username);
        List<Apoteka> apoteke = apotekaRepository.findAll();
        List<ApotekaWithMedicineDto> retList = new ArrayList<>();

        for(Apoteka a : apoteke) {
            for(Long id : a.getMagacin().keySet()) {

                Lek l = lekRepository.findLekByID(id);
                String input = dto.getId().toLowerCase();
                String naziv_leka = l.getNaziv().toLowerCase();

                if (!naziv_leka.startsWith(input))
                    continue;

                if (a.getMagacin().get(id) <= 0)
                    continue;

                boolean shouldContinue = false;
                for(Rezervacija r :
                        rezervacijaRepository.findRezervacijaByApotekaIDAndLekIDAndPacijentId(a.getID(), id, k.getID())) {
                    if (r.getDatumPreuz() == null && r.getRokZaPreuzimanje().after(new Date())) {
                        shouldContinue = true;
                        System.out.println("CANNOT RESERVE SAME MEDICINE TWICE");
                    }
                }
                if (shouldContinue) continue;

                retList.add(new ApotekaWithMedicineDto(a.getNaziv(), a.getAdresa(), l.getNaziv(), l.getID()));

            }
        }
        return retList;
    }

    public void saveApoteka(Apoteka a) {
        apotekaRepository.save(a);
    }

    public List<Korisnik> findDermatologists(Apoteka a) {
        ArrayList<Korisnik> dermatolozi = new ArrayList<>();
        for(Korisnik k : a.getZaposleni()) {
            boolean isDermatologist = false;
            for (GrantedAuthority auth : k.getAuthorities()) {
                if (!auth.getAuthority().equalsIgnoreCase("ROLE_DERMATOLOGIST"))
                    continue;

                isDermatologist = true;
            }
            if (isDermatologist) dermatolozi.add(k);
        }
        return dermatolozi;
    }

    public boolean firePharmacist(String username) {
        List<Apoteka> pharmacies = apotekaRepository.findAll();
        for (Apoteka a : pharmacies) {
            List<Korisnik> users = a.getZaposleni();
            for (Korisnik k : users) {
                if (k.getUsername().equalsIgnoreCase(username)) {
                    users.remove(k);
                    a.setZaposleni(users);
                    apotekaRepository.save(a);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean fireDermatologist(String username, Apoteka apoteka) {
        List<Korisnik> users = apoteka.getZaposleni();
        for (Korisnik k : users) {
            if (k.getUsername().equalsIgnoreCase(username)) {
                users.remove(k);
                apoteka.setZaposleni(users);
                apotekaRepository.save(apoteka);
                return true;
            }
        }
        return false;
    }

    public boolean worksHere(String username, Apoteka a) {
        for (Korisnik k : a.getZaposleni()) {
            if (k.getUsername().equalsIgnoreCase(username))
                return true;
        }
        return false;
    }
}
