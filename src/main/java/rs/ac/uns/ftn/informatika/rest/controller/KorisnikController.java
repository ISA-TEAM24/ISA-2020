package rs.ac.uns.ftn.informatika.rest.controller;

import com.google.api.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.*;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;
import rs.ac.uns.ftn.informatika.rest.service.KorisnikService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

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
    @PreAuthorize("hasAnyRole('PHARMACIST', 'DERMATOLOGIST', 'USER', 'PH_ADMIN')")
    public Korisnik user(Principal user) {
        return this.korisnikService.findByUsername(user.getName());
    }

    @GetMapping("/user/subs")
    @PreAuthorize("hasRole('USER')")
    public Map<String, Boolean> getSubsForUser(Principal p) {
        return korisnikService.findByUsername(p.getName()).getLoyaltyInfo().getPratiPromocije();
    }

    @PutMapping("user/subs/update")
    public ResponseEntity updateSubsForUser(@RequestBody SubCheckDTO dto, Principal p) {

        korisnikService.updateSubsForUser(dto, p.getName());
        return new ResponseEntity<>(null, HttpStatus.OK);
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

    @GetMapping("/user/grading/meds")
    @PreAuthorize("hasRole('USER')")
    public List<MedicineDTO> getInteractedMedicineForUser(Principal p) {

        return this.apotekaService.getInteractedMedicineForUser(p.getName());
    }

    @GetMapping("/user/grading/derms")
    @PreAuthorize("hasRole('USER')")
    public List<ZaposleniDTO> getInteractedDermatologistsForUser(Principal p) {

        return this.apotekaService.getInteractedDermatologistsForUser(p.getName());
    }

    @GetMapping("/user/grading/farms")
    @PreAuthorize("hasRole('USER')")
    public List<ZaposleniDTO> getInteractedPharmacistsForUser(Principal p) {

        return this.apotekaService.getInteractedPharmacistsForUser(p.getName());
    }

    @PostMapping("/user/grading/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getInteractedPharmacistsForUser(@RequestBody OcenaDTO dto, Principal p) {

        if (dto.getOcena() > 5)
            dto.setOcena(5);
        if (dto.getOcena() < 0)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        korisnikService.leaveGrade(dto, p.getName());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }




}
