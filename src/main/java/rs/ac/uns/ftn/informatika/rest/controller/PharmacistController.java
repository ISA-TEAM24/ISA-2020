package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.UserEditDTO;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.service.CustomUserDetailsService;
import rs.ac.uns.ftn.informatika.rest.service.PharmacistService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/pharmacist", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacistController {

    @Autowired
    private PharmacistService pharmacistService;

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
}
