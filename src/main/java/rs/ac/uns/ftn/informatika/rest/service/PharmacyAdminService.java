package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.UserEditDTO;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.repository.KorisnikRepository;

@Service
public class PharmacyAdminService {

    @Autowired
    private KorisnikRepository userRepository;

    public Korisnik findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public Korisnik changeFirstLoginState(String username) {
        Korisnik korisnik = findByUsername(username);
        korisnik.setPrvoLogovanje(false);
        korisnik = this.userRepository.save(korisnik);
        return korisnik;
    }

    public Korisnik editPharmacyAdmin(UserEditDTO user) {
        Korisnik korisnik = findByEmail(user.getEmail());
        korisnik.setIme(user.getName());
        korisnik.setPrezime(user.getLastName());
        korisnik.setAdresa(user.getAddress());
        korisnik.setTelefon(user.getPhoneNumber());
        korisnik.setDrzava(user.getCountry());
        korisnik.setGrad(user.getCity());

        korisnik = this.userRepository.save(korisnik);
        return korisnik;
    }

    public Korisnik findByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }
}
