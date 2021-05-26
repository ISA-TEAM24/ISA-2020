package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.UserEditDTO;
import rs.ac.uns.ftn.informatika.rest.dto.UserRequest;
import rs.ac.uns.ftn.informatika.rest.model.Authority;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.repository.AuthorityRepository;
import rs.ac.uns.ftn.informatika.rest.repository.KorisnikRepository;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class DermatologistService {

    @Autowired
    private KorisnikRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Korisnik saveDermatologist(UserRequest dermRequest) throws Exception {
        Korisnik k = new Korisnik();
        k.setUsername(dermRequest.getUsername());
        // pre nego sto postavimo lozinku u atribut hesiramo je
        k.setPassword(passwordEncoder.encode(dermRequest.getPassword()));
        k.setIme(dermRequest.getFirstname());
        k.setPrezime(dermRequest.getLastname());
        k.setActivated(false);
        k.setAdresa(dermRequest.getAddress());
        k.setDrzava(dermRequest.getCountry());
        k.setGrad(dermRequest.getCity());
        k.setLastPasswordResetDate(new Date());
        k.setTelefon(dermRequest.getPhone());
        k.setEmail(dermRequest.getEmail());
        List<Authority> auth = findByName("ROLE_DERMATOLOGIST");
        if (auth == null)
            throw new Exception("No role with such name");
        // registrujem samo dermatologa
        k.setAuthorities(new HashSet<>(auth));

        k = this.userRepository.save(k);
        return k;
    }

    public Korisnik changeFirstLoginState(String username) {
        Korisnik k = findByUsername(username);
        k.setPrvoLogovanje(false);
        k = this.userRepository.save(k);
        return k;
    }

    public Korisnik editDermatologist(UserEditDTO user) {
        Korisnik k = findByEmail(user.getEmail());
        k.setIme(user.getName());
        k.setPrezime(user.getLastName());
        k.setAdresa(user.getAddress());
        k.setTelefon(user.getPhoneNumber());
        k.setDrzava(user.getCountry());
        k.setGrad(user.getCity());

        k = this.userRepository.save(k);
        return k;
    }

    public List<Authority> findByName(String name) {
        Authority auth = this.authorityRepository.findByName(name);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }

    public Korisnik findByUsername(String username) throws UsernameNotFoundException {
        Korisnik k = userRepository.findByUsername(username);
        return k;
    }

    public Korisnik findByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }
}
