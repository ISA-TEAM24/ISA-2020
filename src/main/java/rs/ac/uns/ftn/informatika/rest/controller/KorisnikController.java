package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.UserEditDTO;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.service.KorisnikService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

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

}
