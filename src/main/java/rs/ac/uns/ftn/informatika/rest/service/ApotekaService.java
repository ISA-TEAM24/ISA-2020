package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.*;
import rs.ac.uns.ftn.informatika.rest.dto.ApotekaEditDTO;
import rs.ac.uns.ftn.informatika.rest.dto.ApotekaWithExamsDTO;
import rs.ac.uns.ftn.informatika.rest.dto.ApotekaWithMedicineDto;
import rs.ac.uns.ftn.informatika.rest.dto.FarmaceutDTO;
import rs.ac.uns.ftn.informatika.rest.dto.IdDTO;
import rs.ac.uns.ftn.informatika.rest.model.*;
import rs.ac.uns.ftn.informatika.rest.repository.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

@Service
public class ApotekaService {

    @Autowired
    protected ApotekaRepository apotekaRepository;

    @Autowired
    private PosetaRepository posetaRepository;

    @Autowired
    protected LekRepository lekRepository;

    @Autowired
    private RezervacijaRepository rezervacijaRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private EReceptRepository eReceptRepository;

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
                System.out.println("FARMACEUT IN QUESTION -- " + farm.getID());
                if (farm.getGodisnjiInfo() == null || farm.getGodisnjiInfo().isNaGodisnjem()) {
                    System.out.println("continued");
                    continue;

                }
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

    public List<Korisnik> findPharmacyAdmin(Apoteka a) {
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

    public List<ApotekaSafeDTO> findAllPharmaciesLimitInfo() {
        List<ApotekaSafeDTO> retList = new ArrayList<>();
        for(Apoteka a : findAll()) {
            ApotekaSafeDTO dto = new ApotekaSafeDTO();
            dto.setNaziv(a.getNaziv());
            dto.setOcena(a.getOcena());
            dto.setAdresa(a.getAdresa());
            dto.setID(a.getID());
            dto.setBrojDermatologa(0);
            dto.setBrojFarmaceuta(0);
            for(Korisnik k : a.getZaposleni()) {
                for(GrantedAuthority ga : k.getAuthorities()) {
                    if (ga.getAuthority().equalsIgnoreCase("ROLE_PHARMACIST"))
                        dto.setBrojFarmaceuta(dto.getBrojFarmaceuta()+1);
                    if (ga.getAuthority().equalsIgnoreCase("ROLE_DERMATOLOGIST"))
                        dto.setBrojDermatologa(dto.getBrojDermatologa()+1);
                }
            }
            retList.add(dto);
        }
        return retList;
    }
  
    public Apoteka findByZaposleni(Korisnik zaposleni){
        return apotekaRepository.findByZaposleni(zaposleni);
    }

    public Apoteka findById(Long id) throws AccessDeniedException {
        Apoteka a = apotekaRepository.findById(id).orElseGet(null);
        return a;
    }

    public List<Lek> findMedicines(Apoteka a) {
        Map<Long, Integer> medicines = a.getMagacin();
        List<Lek> meds = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : medicines.entrySet()) {
            if (entry.getValue() > 0) {
                Lek lek = lekRepository.findLekByID(entry.getKey());
                meds.add(lek);
            }
        }
        return meds;
    }


    public List<TerminDTO> findAvailableAppointments(Apoteka a) {
        List<Poseta> posete = posetaRepository.findAll();
        List<Poseta> availableAppointments = new ArrayList<>();
        for (Poseta p : posete) {
            if (p.getApoteka().equals(a) && p.getVrsta() == p.getPREGLED() && p.getPacijent() == null && p.getDatum().after(new Date())) {
                availableAppointments.add(p);
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<TerminDTO> termini = new ArrayList<>();

        for (Poseta p : availableAppointments) {
            TerminDTO dto = new TerminDTO();
            dto.setID(p.getID());
            dto.setZaposleni(p.getZaposleni());
            dto.setDatum(dateFormat.format(p.getDatum()));
            dto.setVreme(p.getVreme().toString());
            dto.setCena(p.getApoteka().getCenovnik().get("PREGLED"));
            termini.add(dto);
        }
        return termini;
    }

    //samo ukoliko je bar jednom rezervisao i preuzeo lek ili mu je
    //prepisan putem eRecepta ili je imao održan bar jedan pregled ili jedno
    //savetovanje u toj apoteci
    public List<ApotekaGradeDTO> getInteractedPharmaciesForUser(String username) {

        List<ApotekaGradeDTO> retList = new ArrayList<>();
        Korisnik k = korisnikRepository.findByUsername(username);
        List<Rezervacija> rezervacije = rezervacijaRepository.findAllByPacijentID(k.getID());
        List<ERecept> recepti = eReceptRepository.findAllByEmail(k.getEmail());
        List<Poseta> posete = posetaRepository.findPosetaByPacijentID(k.getID());

        for(Apoteka a : findAll()){
            boolean isAdded = false; // nismo jos dodali apoteku u listu
            for(Rezervacija r : rezervacije) {
                if (r.getApoteka().getID() == a.getID() && r.getDatumPreuz() != null && r.getPacijent().getID() == k.getID()) {
                    retList.add(createApotekaGradeFromApoteka(a));
                    isAdded = true;
                    break; // break from rezervacija for loop
                }
            }

            if (isAdded) continue;

            for(ERecept er : recepti) {
                if (er.getApotekaID() == a.getID() && er.getEmail().equals(k.getEmail())) {
                    retList.add(createApotekaGradeFromApoteka(a));
                    isAdded = true;
                    break; // break from rezervacija for loop
                }
            }

            if (isAdded) continue;

            for(Poseta p : posete) {
                if(p.getApoteka().getID() == a.getID() && p.getPacijent().getID() == k.getID()) {
                    retList.add(createApotekaGradeFromApoteka(a));
                    isAdded = true;
                    break; // break from rezervacija for loop
                }
            }
        }

        return retList;
    }

    private ApotekaGradeDTO createApotekaGradeFromApoteka(Apoteka a) {

        ApotekaGradeDTO dto = new ApotekaGradeDTO();
        dto.setAdresa(a.getAdresa());
        dto.setID(a.getID());
        dto.setNaziv(a.getNaziv());
        return dto;
    }

    public List<MedicineDTO> getInteractedMedicineForUser(String username) {

        List<MedicineDTO> retList = new ArrayList<>();

        Korisnik k = korisnikRepository.findByUsername(username);
        List<Rezervacija> rezervacije = rezervacijaRepository.findAllByPacijentID(k.getID());
        List<ERecept> recepti = eReceptRepository.findAllByEmail(k.getEmail());

        for(Lek l : lekRepository.findAll()) {

            boolean isAdded = false;

            for(Rezervacija r : rezervacije) {
                if (r.getDatumPreuz() != null && r.getLek().getID() == l.getID()){
                    isAdded = true;
                    retList.add(createMedicineDTOFromLek(l));
                    break;
                }
            }

            if(isAdded) continue;

            for(ERecept er : recepti) {
                if (er.getLekovi().contains(l)) {
                    isAdded = true;
                    retList.add(createMedicineDTOFromLek(l));
                    break;
                }
            }
        }
        return retList;
    }

    private MedicineDTO createMedicineDTOFromLek(Lek l) {
        MedicineDTO dto = new MedicineDTO();
        dto.setID(l.getID());
        dto.setNaziv(l.getNaziv());
        return dto;
    }

    public List<ZaposleniDTO> getInteractedEmployeesForUser(String username, boolean lookingForDermatologists) {

        Poseta.VrstaPosete vp;

        if (lookingForDermatologists)
            vp = Poseta.VrstaPosete.PREGLED;
        else
            vp = Poseta.VrstaPosete.SAVETOVANJE;

        List<ZaposleniDTO> retList = new ArrayList<>();
        Korisnik k = korisnikRepository.findByUsername(username);
        List<Poseta> posete = posetaRepository.findPosetaByPacijentID(k.getID());
        Set<Korisnik> dermatolozi = new HashSet<>();

        for(Poseta p : posete) {
            if(p.getVrsta() == vp)
                dermatolozi.add(p.getZaposleni());
        }

        for (Korisnik z : dermatolozi) {
            retList.add(createZaposleniDTOFromKorisnik(z));
        }

        return retList;
    }

    private ZaposleniDTO createZaposleniDTOFromKorisnik(Korisnik z) {

        ZaposleniDTO dto = new ZaposleniDTO();
        dto.setIme(z.getIme());
        dto.setPrezime(z.getPrezime());
        dto.setID(z.getID());
        return dto;
    }

    public List<ZaposleniDTO> getInteractedPharmacistsForUser(String username) {

        return getInteractedEmployeesForUser(username, false);
    }

    public List<ZaposleniDTO> getInteractedDermatologistsForUser(String username) {

        return getInteractedEmployeesForUser(username, true);
    }

    public List<PharmacyMedicineDTO> findMedicineWithPriceAndAmount(Apoteka a) {
        Map<Long, Integer> magacin = a.getMagacin();
        Map<String, Integer> cenovnik = a.getCenovnik();
        List<PharmacyMedicineDTO> meds = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : magacin.entrySet()) {
            PharmacyMedicineDTO dto = new PharmacyMedicineDTO();
            Lek lek = lekRepository.findLekByID(entry.getKey());
            dto.setID(lek.getID());
            dto.setLek(lek.getNaziv());
            dto.setKolicina(entry.getValue());
            for (Map.Entry<String, Integer> entry2 : cenovnik.entrySet()) {
                if (entry2.getKey().equalsIgnoreCase(dto.getLek())) {
                    dto.setCena(entry2.getValue());
                }
            }
            meds.add(dto);
        }
        return meds;
    }

    public void removeMedicineFromPharmacy(Apoteka a, Long id) {
        a.getMagacin().remove(id);
        Lek l = lekRepository.findLekByID(id);
        a.getCenovnik().remove(l.getNaziv());

        apotekaRepository.save(a);
    }

    public void addNewMedicineInStorage(Lek lek, Apoteka a) {
        a.getMagacin().put(lek.getID(), 0);
        a.getCenovnik().put(lek.getNaziv(), 0);

        apotekaRepository.save(a);
    }

    public void checkDoesPharmacyHaveMedicine(Apoteka a, Lek lek) {
        if (!a.getMagacin().containsKey(lek.getID())) {
            a.getMagacin().put(lek.getID(), 0);
            a.getCenovnik().put(lek.getNaziv(), 0);
            apotekaRepository.save(a);
        }
    }

    public void updateStorage(Ponuda p, Apoteka a) {
        Map<String, Integer> lekovi = p.getSpisakLekova();
        Map<Long, Integer> magacin = a.getMagacin();
        for (Map.Entry<String, Integer> entry1 : lekovi.entrySet()) {
            Lek l = lekRepository.findLekByNaziv(entry1.getKey());
            if (magacin.containsKey(l.getID())) {
                int oldValue = magacin.get(l.getID());
                int newValue = oldValue + entry1.getValue();
                magacin.replace(l.getID(), newValue);
            } else {
                magacin.put(l.getID(), entry1.getValue());
            }
        }
        apotekaRepository.save(a);
    }

    public List<Lek> findAllMedicinesInPharmacy(Apoteka a) {
        Map<Long, Integer> medicines = a.getMagacin();
        List<Lek> meds = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : medicines.entrySet()) {
            Lek lek = lekRepository.findLekByID(entry.getKey());
            meds.add(lek);
        }
        return meds;
    }
}
