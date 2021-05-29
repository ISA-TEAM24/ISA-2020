package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.ApotekaWithExamsDTO;
import rs.ac.uns.ftn.informatika.rest.dto.FarmaceutDTO;
import rs.ac.uns.ftn.informatika.rest.model.*;
import rs.ac.uns.ftn.informatika.rest.repository.ApotekaRepository;
import rs.ac.uns.ftn.informatika.rest.repository.PosetaRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ApotekaService {

    @Autowired
    private ApotekaRepository apotekaRepository;

    @Autowired
    private PosetaRepository posetaRepository;

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

}
