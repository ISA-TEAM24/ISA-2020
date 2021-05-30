package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.IdDTO;
import rs.ac.uns.ftn.informatika.rest.dto.RezervacijaInfoDTO;
import rs.ac.uns.ftn.informatika.rest.dto.RezervacijaWithFlagDTO;
import rs.ac.uns.ftn.informatika.rest.model.Rezervacija;
import rs.ac.uns.ftn.informatika.rest.service.RezervacijaService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class RezervacijaController {

    @Autowired
    private RezervacijaService rezervacijaService;


    @PreAuthorize("hasRole('PHARMACIST')")
    @GetMapping("/find/{id}")
    public ResponseEntity<RezervacijaInfoDTO> getReservation(Principal pharmacist, @PathVariable String id) {
        Rezervacija r = rezervacijaService.findActiveRezervacijaByID(Long.parseLong(id), pharmacist.getName());

        if(r == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        RezervacijaInfoDTO dto = new RezervacijaInfoDTO(r.getPacijent().getIme() + " " + r.getPacijent().getPrezime(), r.getLek().getNaziv(), r.getID().toString());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/issue")
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<Rezervacija> issueReservation(@RequestBody String idReservation) {

        Rezervacija r = rezervacijaService.issueReservation(idReservation);

        if(r == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cancel")
    public ResponseEntity<Rezervacija> cancelReservation(@RequestBody IdDTO dto) {

        boolean flag = rezervacijaService.cancelReservation(Long.parseLong(dto.getId()));

        return null;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all")
    public List<RezervacijaWithFlagDTO> getReservationsForUser(Principal p) {

        List<RezervacijaWithFlagDTO> retList = rezervacijaService.findAllActiveReservationsForUser(p.getName());

        return retList;
    }

}
