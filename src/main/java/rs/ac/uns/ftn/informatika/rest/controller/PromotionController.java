package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.rest.dto.AllergiesDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PromocijaDTO;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Promocija;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;
import rs.ac.uns.ftn.informatika.rest.service.PromotionService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/promotion", produces = MediaType.APPLICATION_JSON_VALUE)
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private ApotekaService apotekaService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity createPromotion(Principal p, @RequestBody PromocijaDTO dto) {
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        Promocija promocija = promotionService.create(dto, a);
        promotionService.sendPromotion(promocija);

        return new ResponseEntity(null, HttpStatus.OK);
    }

}
