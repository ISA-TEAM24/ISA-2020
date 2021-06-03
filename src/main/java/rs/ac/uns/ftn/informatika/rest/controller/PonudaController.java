package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.OrdersDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PonudaDTO;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.Narudzbenica;
import rs.ac.uns.ftn.informatika.rest.model.Ponuda;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;
import rs.ac.uns.ftn.informatika.rest.service.NarudzbenicaService;
import rs.ac.uns.ftn.informatika.rest.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.rest.service.PonudaService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/offer", produces = MediaType.APPLICATION_JSON_VALUE)
public class PonudaController {

    @Autowired
    private PonudaService ponudaService;

    @Autowired
    private NarudzbenicaService narudzbenicaService;

    @Autowired
    private PharmacyAdminService pharmacyAdminService;

    @Autowired
    private ApotekaService apotekaService;


    @GetMapping("/getoffersfororder/{id}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public List<PonudaDTO> getOffersForOrder(@PathVariable("id") String id, Principal p) {
        Long ID = Long.parseLong(id);
        Korisnik k = pharmacyAdminService.findByUsername(p.getName());
        Narudzbenica n = narudzbenicaService.findOrderByID(ID);
        return ponudaService.findByNarudzbenicaAndCreateDTO(n, k);
    }

    @PutMapping("accept/{id}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> acceptOfferForOrder(@PathVariable("id") String id, Principal p) {
        Long ID = Long.parseLong(id);
        Ponuda ponuda = ponudaService.acceptOffer(ID);
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        narudzbenicaService.closeOrder(ponuda.getNarudzbenica());
        apotekaService.updateStorage(ponuda, a);

        return new ResponseEntity<> (null, HttpStatus.OK);
    }
}
