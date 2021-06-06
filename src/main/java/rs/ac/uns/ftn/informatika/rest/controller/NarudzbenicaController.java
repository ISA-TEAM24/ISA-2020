package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.NarudzbenicaDTO;
import rs.ac.uns.ftn.informatika.rest.dto.OrderWithDetailsDTO;
import rs.ac.uns.ftn.informatika.rest.dto.OrdersDTO;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.Narudzbenica;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;
import rs.ac.uns.ftn.informatika.rest.service.NarudzbenicaService;
import rs.ac.uns.ftn.informatika.rest.service.PharmacyAdminService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class NarudzbenicaController {

    @Autowired
    private NarudzbenicaService narudzbenicaService;

    @Autowired
    private ApotekaService apotekaService;

    @Autowired
    private PharmacyAdminService pharmacyAdminService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody NarudzbenicaDTO narudzbenicaDTO, Principal p) {
        if (narudzbenicaDTO.getLekovi().isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        Korisnik k = pharmacyAdminService.findByUsername(p.getName());
        narudzbenicaService.createPurchaseOrder(narudzbenicaDTO, a, k);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/getordersfrompharmacy")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public List<OrdersDTO> getActiveOrdersFromPharmacy(Principal p) {
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        return narudzbenicaService.getAllOrdersFromPharmacy(a);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") String id) {
        Long ID = Long.parseLong(id);
        if (narudzbenicaService.deleteIfPossible(ID)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getorderbyid/{id}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public OrderWithDetailsDTO findOrderByID(@PathVariable("id") String id) {
        Long ID = Long.parseLong(id);
        return narudzbenicaService.findOrderDTOByID(ID);
    }
}
