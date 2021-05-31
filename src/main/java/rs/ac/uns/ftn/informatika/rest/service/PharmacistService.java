package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.NewPharmacistDTO;
import rs.ac.uns.ftn.informatika.rest.dto.UserEditDTO;
import rs.ac.uns.ftn.informatika.rest.model.Authority;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.Period;
import rs.ac.uns.ftn.informatika.rest.model.RadnoInfo;
import rs.ac.uns.ftn.informatika.rest.repository.AuthorityRepository;
import rs.ac.uns.ftn.informatika.rest.repository.KorisnikRepository;

import java.util.*;

@Service
public class PharmacistService {

    @Autowired
    private KorisnikRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Korisnik findByUsername(String username) throws UsernameNotFoundException {
        Korisnik k = userRepository.findByUsername(username);
        return k;
    }

    public Korisnik changeFirstLoginState(String username) {
        Korisnik k = findByUsername(username);
        k.setPrvoLogovanje(false);
        k = this.userRepository.save(k);
        return k;
    }

    public Korisnik editPharmacist(UserEditDTO user) {
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

    public Korisnik findByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    public Korisnik savePharmacist(NewPharmacistDTO dto, String pharmacyname) throws Exception {
        Korisnik k = new Korisnik();
        k.setUsername(dto.getUsername());
        // pre nego sto postavimo lozinku u atribut hesiramo je
        k.setPassword(passwordEncoder.encode(dto.getPassword()));
        k.setIme(dto.getIme());
        k.setPrezime(dto.getPrezime());
        k.setActivated(false);
        k.setAdresa(dto.getAdresa());
        k.setDrzava(dto.getDrzava());
        k.setGrad(dto.getGrad());
        k.setLastPasswordResetDate(new Date());
        k.setTelefon(dto.getTelefon());
        k.setEmail(dto.getEmail());
        List<Authority> auth = findByName("ROLE_PHARMACIST");
        if (auth == null)
            throw new Exception("No role with such name");
        k.setAuthorities(new HashSet<>(auth));

        Period p = new Period();
        p.setOdVreme(dto.getOdVreme());
        p.setDoVreme(dto.getDoVreme());
        p.setOdDatum(dto.getOdDatum());
        p.setDoDatum(dto.getDoDatum());
        ArrayList<Period> periods = new ArrayList<Period>();
        periods.add(p);

        RadnoInfo ri = new RadnoInfo();
        ri.setBusinessHours(periods);
        ri.setNeradniDani(dto.getNeradniDani());

        HashMap <String, RadnoInfo> map = new HashMap<>();
        map.put(pharmacyname,ri);

        k.setRadnoInfo(map);

        k = this.userRepository.save(k);
        return k;
    }

    private List<Authority> findByName(String name) {
        Authority auth = this.authorityRepository.findByName(name);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }
}
