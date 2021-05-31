package rs.ac.uns.ftn.informatika.rest.controller;

import com.google.api.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.*;
import rs.ac.uns.ftn.informatika.rest.model.Rezervacija;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;
import rs.ac.uns.ftn.informatika.rest.service.RezervacijaService;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class RezervacijaController {

    @Autowired
    private RezervacijaService rezervacijaService;

    @Autowired
    private ApotekaService apotekaService;


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

        if (flag == false) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all")
    public List<RezervacijaWithFlagDTO> getReservationsForUser(Principal p) {

        List<RezervacijaWithFlagDTO> retList = rezervacijaService.findAllActiveReservationsForUser(p.getName());

        return retList;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity createReservation(@RequestBody RezervacijaDTO dto, Principal p) throws ParseException {

        boolean flag = rezervacijaService.createReservation(dto, p.getName());

        if (!flag) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/findmeds")
    public List<ApotekaWithMedicineDto> findMedicineByQuery(@RequestBody IdDTO dto, Principal p) {

        List<ApotekaWithMedicineDto> retList = apotekaService.findPharmaciesWithMedicine(dto, p.getName());

        return retList;
    }

}
