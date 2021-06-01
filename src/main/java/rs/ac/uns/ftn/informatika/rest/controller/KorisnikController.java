package rs.ac.uns.ftn.informatika.rest.controller;

import com.google.api.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.AllergiesDTO;
import rs.ac.uns.ftn.informatika.rest.dto.ApotekaGradeDTO;
import rs.ac.uns.ftn.informatika.rest.dto.UserEditDTO;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;
import rs.ac.uns.ftn.informatika.rest.service.KorisnikService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private ApotekaService apotekaService;

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('USER')")
    public Korisnik getById(@PathVariable Long id) {
        return korisnikService.findById(id);
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasRole('USER')")
    public Korisnik user(Principal user) {
        return this.korisnikService.findByUsername(user.getName());
    }

    @PostMapping("/user/allergy/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity addAllergy(Principal user, @RequestBody AllergiesDTO dto) {
        List<String> retList = korisnikService.addAllergyForUser(user.getName(), dto);
        return new ResponseEntity<>(retList, HttpStatus.CREATED);
    }

    @PostMapping("user/allergy/remove")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity removeAllergy(Principal user, @RequestBody AllergiesDTO dto) {
        if (korisnikService.removeAllergyForUser(user.getName(), dto.getAllergies().get(0)))
            return new ResponseEntity<>(null, HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/allergies")
    @PreAuthorize("hasRole('USER')")
    public AllergiesDTO getAllergies(Principal user) {
        return korisnikService.findAllergiesForUser(user.getName());
    }


    @GetMapping("/user/all")
    //@PreAuthorize("hasRole('USER')")
    public List<Korisnik> loadAll(HttpServletRequest request) {
        return this.korisnikService.findAll();
    }

    @PutMapping("/user/edit/me")
    @PreAuthorize("hasRole('USER')")
    public Korisnik editUser(@RequestBody UserEditDTO user) {
        System.out.println(user);
        return this.korisnikService.editKorisnik(user);
    }

    @GetMapping("/user/grading/pharmacies")
    @PreAuthorize("hasRole('USER')")
    public List<ApotekaGradeDTO> getInteractedPharmaciesForUser(Principal p) {

        return this.apotekaService.getInteractedPharmaciesForUser(p.getName());
    }




}
