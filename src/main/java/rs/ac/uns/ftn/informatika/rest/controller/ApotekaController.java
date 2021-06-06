package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.rest.dto.TerminDTO;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.Lek;
import rs.ac.uns.ftn.informatika.rest.model.Poseta;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;
import rs.ac.uns.ftn.informatika.rest.service.KorisnikService;

import java.util.List;

@RestController
@RequestMapping(value = "/apoteka", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApotekaController {

    @Autowired
    private ApotekaService pharmacyService;

    @GetMapping("/{id}")
    public Apoteka getPharmacyById(@PathVariable Long id) {
        return pharmacyService.findById(id);
    }

    @GetMapping("/finddermatologists/{id}")
    public List<Korisnik> findMyDermatologists(@PathVariable Long id) {
        Apoteka a = pharmacyService.findById(id);
        return pharmacyService.findDermatologists(a);
    }

    @GetMapping("/findpharmacists/{id}")
    public List<Korisnik> findMyPharmacists(@PathVariable Long id) {
        Apoteka a = pharmacyService.findById(id);
        return pharmacyService.findPharmacists(a);
    }

    @GetMapping("/findmedicines/{id}")
    public List<Lek> findMyMedicines(@PathVariable Long id) {
        Apoteka a = pharmacyService.findById(id);
        return pharmacyService.findMedicines(a);
    }

    @GetMapping("findavailableappointments/{id}")
    public List<TerminDTO> findAvailableAppointments(@PathVariable Long id) {
        Apoteka a = pharmacyService.findById(id);
        return pharmacyService.findAvailableAppointments(a);
    }

}
