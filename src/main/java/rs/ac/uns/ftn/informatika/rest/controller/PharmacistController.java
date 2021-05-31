package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import rs.ac.uns.ftn.informatika.rest.dto.AllergiesDTO;
import rs.ac.uns.ftn.informatika.rest.dto.NewPharmacistDTO;
import rs.ac.uns.ftn.informatika.rest.dto.UserEditDTO;
import rs.ac.uns.ftn.informatika.rest.dto.UserRequest;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.repository.AuthorityRepository;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;
import rs.ac.uns.ftn.informatika.rest.service.CustomUserDetailsService;
import rs.ac.uns.ftn.informatika.rest.service.PharmacistService;

import java.io.Console;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/pharmacist", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacistController {

    @Autowired
    private PharmacistService pharmacistService;

    @Autowired
    private ApotekaService apotekaService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/whoami")
    @PreAuthorize("hasRole('PHARMACIST')")
    public Korisnik user(Principal user) {
        return this.pharmacistService.findByUsername(user.getName());
    }

    @PostMapping("/firstlogpwchange")
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<Korisnik> firstLogPwChange(Principal p) {
        System.out.println("***" + p.getName() + "***");
        String username = p.getName();
        Korisnik user = null;
        try {
            user = this.pharmacistService.changeFirstLoginState(username);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/editdata")
    @PreAuthorize("hasRole('PHARMACIST')")
    public Korisnik editUser(@RequestBody UserEditDTO user) {
        System.out.println(user);
        return this.pharmacistService.editPharmacist(user);
    }

    @RequestMapping(value = "/changepw", method = RequestMethod.POST)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<?> changePassword(@RequestBody AuthenticationController.PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }

    @PostMapping("/addpharmacist")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<Korisnik> addPharmacist(@RequestBody NewPharmacistDTO dto, Principal p, UriComponentsBuilder ucBuilder) throws Exception {
        System.out.println("USAOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        Korisnik existUser = this.pharmacistService.findByUsername(dto.getUsername());

        if (existUser != null) {
            throw new Exception("Username already exists");
        }

        Apoteka apoteka = apotekaService.getPharmacyByAdmin(p.getName());

        Korisnik user = this.pharmacistService.savePharmacist(dto, apoteka.getNaziv());
        apotekaService.hirePharmacist(user, apoteka.getNaziv());
        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
