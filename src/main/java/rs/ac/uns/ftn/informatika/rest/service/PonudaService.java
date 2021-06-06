package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.NarudzbenicaLekDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PonudaDTO;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.Narudzbenica;
import rs.ac.uns.ftn.informatika.rest.model.Ponuda;
import rs.ac.uns.ftn.informatika.rest.repository.PonudaRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PonudaService {

    @Autowired
    private PonudaRepository ponudaRepository;

    @Autowired
    private EmailService emailService;

    public List<PonudaDTO> findByNarudzbenicaAndCreateDTO(Narudzbenica n, Korisnik k) {
        List<Ponuda> ponude = ponudaRepository.findByNarudzbenica(n);
        List<PonudaDTO> ponudeDTO = new ArrayList<>();

        for (Ponuda p : ponude) {
            PonudaDTO dto = new PonudaDTO();
            dto.setDatumprosao(false);
            if (!n.getRokZaPonudu().after(new Date())) {
                dto.setDatumprosao(true);
            }
            dto.setAdmin(false);
            if (n.getKreirao().equals(k)) {
                dto.setAdmin(true);
            }
            dto.setId(p.getID().toString());
            dto.setPosiljalac(p.getPosiljalac().getUsername());
            dto.setUkupnaCena(p.getUkupnaCena());
            List<NarudzbenicaLekDTO> lekovi = new ArrayList<>();

            for (Map.Entry<String, Integer> entry : p.getSpisakLekova().entrySet()) {
                NarudzbenicaLekDTO lekDTO = new NarudzbenicaLekDTO();
                lekDTO.setNaziv(entry.getKey());
                lekDTO.setKolicina(entry.getValue());
                lekovi.add(lekDTO);
            }
            dto.setLekovi(lekovi);
            ponudeDTO.add(dto);
        }
        return ponudeDTO;
    }

    public List<Ponuda> findByNarudzbenica(Narudzbenica n) {
        return ponudaRepository.findByNarudzbenica(n);
    }

    public Ponuda acceptOffer(Long id) throws AccessDeniedException {
        Ponuda ponuda = ponudaRepository.findById(id).orElseGet(null);
        List<Ponuda> ponude = ponudaRepository.findByNarudzbenica(ponuda.getNarudzbenica());

        for (Ponuda p : ponude) {
            if (!p.getID().equals(id)) {
                p.setStatus(p.getOdbijena());
                if (emailService.sendOfferDeniedMessage(p)) {
                    System.out.println("Message sent");
                }
                else {
                    System.out.println("Email error");
                }
                ponudaRepository.save(p);
            }
        }

        ponuda.setStatus(ponuda.getPrihacena());
        if (emailService.sendOfferAcceptedMessage(ponuda)) {
            System.out.println("Message sent");
        }
        else {
            System.out.println("Email error");
        }
        ponudaRepository.save(ponuda);

        return ponuda;
    }
}
