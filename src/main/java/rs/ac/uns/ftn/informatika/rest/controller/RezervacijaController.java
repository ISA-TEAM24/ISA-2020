package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.rest.model.Rezervacija;
import rs.ac.uns.ftn.informatika.rest.service.KorisnikService;
import rs.ac.uns.ftn.informatika.rest.service.RezervacijaService;

import java.security.Principal;

@RestController
@RequestMapping(value = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class RezervacijaController {

    @Autowired
    private RezervacijaService rezervacijaService;


    @PreAuthorize("hasRole('PHARMACIST')")
    @GetMapping("/find/{id}")
    public ResponseEntity<Rezervacija> getReservation(Principal pharmacist, @PathVariable Long id) {
        Rezervacija r = rezervacijaService.findActiveRezervacijaByID(id, pharmacist.getName());

        if(r == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(r, HttpStatus.OK);
    }
}
