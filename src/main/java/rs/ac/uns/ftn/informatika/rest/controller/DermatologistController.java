package rs.ac.uns.ftn.informatika.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import rs.ac.uns.ftn.informatika.rest.dto.*;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;
import rs.ac.uns.ftn.informatika.rest.service.CustomUserDetailsService;
import rs.ac.uns.ftn.informatika.rest.service.DermatologistService;

import java.security.Principal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/dermatologist", produces = MediaType.APPLICATION_JSON_VALUE)
public class DermatologistController {

    @Autowired
    private DermatologistService dermatologistService;

    @Autowired
    private ApotekaService pharmacyService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/whoami")
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public Korisnik user(Principal user) {
        return this.dermatologistService.findByUsername(user.getName());
    }

    @PostMapping("/firstlogpwchange")
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<Korisnik> firstLogPwChange(Principal p) {
        System.out.println("***" + p.getName() + "***");
        String username = p.getName();
        Korisnik user = null;
        try {
            user = this.dermatologistService.changeFirstLoginState(username);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/editdata")
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public Korisnik editUser(@RequestBody UserEditDTO user) {
        System.out.println(user);
        return this.dermatologistService.editDermatologist(user);
    }

    @RequestMapping(value = "/changepw", method = RequestMethod.POST)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<?> changePassword(@RequestBody AuthenticationController.PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }

    // Endpoint za registraciju novog dermatologa
    @PostMapping("/signupDerm")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Korisnik> addDermatologist(@RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) throws Exception {

        Korisnik existUser = this.dermatologistService.findByUsername(userRequest.getUsername());

        if (existUser != null) {
            throw new Exception("Username already exists");
        }

        Korisnik user = this.dermatologistService.saveDermatologist(userRequest);
        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public List<Korisnik> getAllDermatologists() {
        return dermatologistService.getAll();
    }

    @GetMapping("/getmypharmacies/{username}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public List<Apoteka> getMyPharmacies(@PathVariable("username") String username) {
        return dermatologistService.getMyPharmacies(username);
    }

    @GetMapping("/getdermwithradnoinfo/{username}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public DermatologRadnoVremeDTO getDermatologistWithWorkTimes(@PathVariable("username") String username) {
        return dermatologistService.createDermatologistWTDTO(username);
    }

    @GetMapping("/getdermwithappointments/{username}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public DermatologRViTermDTO getDermWithAppointments(@PathVariable("username") String username, Principal p) {
        Apoteka a = pharmacyService.getPharmacyByAdmin(p.getName());
        return dermatologistService.createDermatologRViTermDTO(username, a);
    }
}
