package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.controller.PromotionController;
import rs.ac.uns.ftn.informatika.rest.dto.PromocijaDTO;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.Promocija;
import rs.ac.uns.ftn.informatika.rest.repository.PromocijaRepository;

import java.util.List;
import java.util.Map;

@Service
public class PromotionService {

    @Autowired
    private PromocijaRepository promocijaRepository;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private EmailService emailService;

    public Promocija create(PromocijaDTO dto, Apoteka a) {
        Promocija promocija = new Promocija();
        promocija.setApoteka(a);
        promocija.setNaslov(dto.getNaslov());
        promocija.setTekst(dto.getTekst());
        promocija.setVaziDo(dto.getDatum());

        promocijaRepository.save(promocija);

        return promocija;
    }

    public boolean sendPromotion(Promocija promocija) {
        List<Korisnik> users = korisnikService.findAll();
        for (Korisnik user : users) {
            if (user.getLoyaltyInfo() != null) {
                for (Map.Entry<String, Boolean> entry : user.getLoyaltyInfo().getPratiPromocije().entrySet()) {
                    if (entry.getKey().equalsIgnoreCase(promocija.getApoteka().getNaziv())) {
                        if (entry.getValue()) {
                            if (!emailService.sendPromotionMessage(user, promocija))
                                return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
