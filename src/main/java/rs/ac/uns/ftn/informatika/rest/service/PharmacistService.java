package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.UserEditDTO;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.repository.KorisnikRepository;

@Service
public class PharmacistService {

    @Autowired
    private KorisnikRepository userRepository;

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

}
