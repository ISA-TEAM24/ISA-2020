package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.DermatologDTO;
import rs.ac.uns.ftn.informatika.rest.dto.HireDermatologistDTO;
import rs.ac.uns.ftn.informatika.rest.dto.UserEditDTO;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.Period;
import rs.ac.uns.ftn.informatika.rest.model.RadnoInfo;
import rs.ac.uns.ftn.informatika.rest.repository.KorisnikRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PharmacyAdminService {

    @Autowired
    private KorisnikRepository userRepository;

    @Autowired
    private ApotekaService apotekaService;

    @Autowired
    private  DermatologistService dermatologistService;

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

    public List<DermatologDTO> createDeramtologDtos(List<Korisnik> dermatologists, Apoteka a) {
        List<DermatologDTO> dermatologDTOS = new ArrayList<>();
        for (Korisnik d : dermatologists) {
            DermatologDTO dermatologDTO = new DermatologDTO();
            dermatologDTO.setIme(d.getIme());
            dermatologDTO.setPrezime(d.getPrezime());
            dermatologDTO.setOcena(d.getOcena());
            dermatologDTO.setUsername(d.getUsername());
            dermatologDTO.setRadiUApotekama(dermatologistService.getMyPharmacies(d.getUsername()));
            if (dermatologDTO.getRadiUApotekama().contains(a)) {
                dermatologDTO.setRadiUMojoj(true);
            } else {
                dermatologDTO.setRadiUMojoj(false);
            }
            dermatologDTOS.add(dermatologDTO);
        }
        return  dermatologDTOS;
    }

    public void hireDermatologist(HireDermatologistDTO dto, Apoteka a) {
        System.out.println("USERNAME IZ DTO:");
        System.out.println(dto.getUsername());
        Korisnik k = dermatologistService.findByUsername(dto.getUsername());
        System.out.println("-------------------------------------------");
        System.out.println(k);
        System.out.println("-------------------------------------------");
        a.getZaposleni().add(k);

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

        k.getRadnoInfo().put(a.getNaziv(), ri);

        apotekaService.saveApoteka(a);
        userRepository.save(k);
    }
}
