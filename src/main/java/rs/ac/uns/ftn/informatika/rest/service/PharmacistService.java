package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.DermDTO;
import rs.ac.uns.ftn.informatika.rest.dto.NewPharmacistDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PharmacistDTO;
import rs.ac.uns.ftn.informatika.rest.dto.UserEditDTO;
import rs.ac.uns.ftn.informatika.rest.model.*;
import rs.ac.uns.ftn.informatika.rest.repository.ApotekaRepository;
import rs.ac.uns.ftn.informatika.rest.repository.AuthorityRepository;
import rs.ac.uns.ftn.informatika.rest.repository.KorisnikRepository;

import java.util.*;

@Service
public class PharmacistService {

    @Autowired
    private KorisnikRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private ApotekaRepository pharmacyRepository;

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
        k.setActivated(true);
        List<Authority> auth = findByName("ROLE_PHARMACIST");
        if (auth == null)
            throw new Exception("No role with such name");
        k.setAuthorities(new HashSet<>(auth));

        Period p = new Period();
        p.setOdVreme(dto.getOdVreme());
        p.setDoVreme(dto.getDoVreme());
        p.setOdDatum(dto.getOdDatum());
        p.setDoDatum(dto.getDoDatum());
        List<Period> periods = new ArrayList<>();
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

    public List<Korisnik> getAll() throws AccessDeniedException {
        ArrayList<Korisnik> pharmacists = new ArrayList<>();
        List<Korisnik> allUsers = userRepository.findAll();

        for (Korisnik k : allUsers) {
            boolean isPharmacists = false;
            for (GrantedAuthority auth : k.getAuthorities()) {
                if (!auth.getAuthority().equalsIgnoreCase("ROLE_PHARMACIST"))
                    continue;
                isPharmacists = true;
            }
            if (isPharmacists) pharmacists.add(k);
        }

        return pharmacists;
    }

    public List<PharmacistDTO> createPharmDtos(List<Korisnik> pharmacists) {
        List<PharmacistDTO> pharmacistDTOS = new ArrayList<>();
        for (Korisnik p : pharmacists) {
            PharmacistDTO pharmacistDTO = new PharmacistDTO();
            pharmacistDTO.setIme(p.getIme());
            pharmacistDTO.setPrezime(p.getPrezime());
            pharmacistDTO.setOcena(p.getOcena());
            pharmacistDTO.setUsername(p.getUsername());
            pharmacistDTO.setApoteka(getMyPharmacy(p.getUsername()));
            pharmacistDTOS.add(pharmacistDTO);
        }
        return pharmacistDTOS;
    }

    private String getMyPharmacy(String username) {
        List<Apoteka> pharmacies = pharmacyRepository.findAll();
        for (Apoteka a : pharmacies) {
            for (Korisnik k : a.getZaposleni()) {
                if (username.equalsIgnoreCase(k.getUsername())) {
                    return a.getNaziv();
                }
            }
        }
        return "";
    }
}
