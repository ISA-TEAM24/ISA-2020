package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.DateTimeDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PosetaDTO;
import rs.ac.uns.ftn.informatika.rest.model.Poseta;
import rs.ac.uns.ftn.informatika.rest.repository.PosetaRepository;

import java.text.ParseException;

@Service
public class PosetaService {

    @Autowired
    private PosetaRepository posetaRepository;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private ApotekaService apotekaService;

    public boolean addConsult(PosetaDTO dto, String pacijent) throws ParseException {

        Poseta p = new Poseta();
        p.setApoteka(apotekaService.findApotekaByNaziv(dto.getApoteka()));
        p.setDijagnoza("");
        p.setTrajanje(dto.getTrajanje());
        p.setVrsta(p.getSAVETOVANJE());
        p.setPoeni(dto.getPoeni());
        p.setTrajanje(dto.getTrajanje());

        p.setPacijent(korisnikService.findByUsername(pacijent));
        p.setApoteka(apotekaService.findApotekaByNaziv(dto.getApoteka()));
        p.setZaposleni(korisnikService.findByUsername(dto.getZaposleni()));

        DateTimeDTO dt = new DateTimeDTO();
        dt.setDate(dto.getDatum());
        dt.setTime(dto.getVreme());
        p.setDatum(dt.parseDateStringToDate());
        p.setVreme(dt.parseTimeStringToLocalTime());
        posetaRepository.save(p);
        return true;
    }

}
