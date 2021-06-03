package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.LekDTO;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Lek;
import rs.ac.uns.ftn.informatika.rest.repository.LekRepository;
import rs.ac.uns.ftn.informatika.rest.repository.RezervacijaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LekService {

    @Autowired
    private LekRepository lekRepository;

    public Lek getLekByNaziv(String name) {
        return lekRepository.findLekByNaziv(name);
    }


    public List<LekDTO> findAllMedsAndAvailabilityInMyPharmacy(Apoteka a) {
        List<Lek> lekovi = lekRepository.findAll();
        Map<Long, Integer> lekoviUApoteci = a.getMagacin();
        List<LekDTO> lekDTOS = new ArrayList<>();

        for (Lek lek : lekovi) {
            LekDTO lekDTO = new LekDTO();
            lekDTO.setNaziv(lek.getNaziv());
            lekDTO.setOcena(lek.getOcena());
            lekDTO.setProizvodjac(lek.getProizvodjac());
            lekDTO.setSastav(lek.getSastav());
            lekDTO.setuMojojApoteci(false);
            for (Map.Entry<Long, Integer> entry : lekoviUApoteci.entrySet()) {
                if (lek.getID().equals(entry.getKey())) {
                    lekDTO.setuMojojApoteci(true);
                }
            }
            lekDTOS.add(lekDTO);
        }
        return lekDTOS;
    }
}
