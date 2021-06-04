package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.*;
import rs.ac.uns.ftn.informatika.rest.model.*;
import rs.ac.uns.ftn.informatika.rest.repository.ApotekaRepository;
import rs.ac.uns.ftn.informatika.rest.repository.AuthorityRepository;
import rs.ac.uns.ftn.informatika.rest.repository.KorisnikRepository;

import javax.validation.constraints.Null;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DermatologistService {

    @Autowired
    private KorisnikRepository userRepository;

    @Autowired
    private ApotekaRepository pharmacyRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PosetaService posetaService;

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

    public List<Korisnik> getAll() throws AccessDeniedException {
        ArrayList<Korisnik> dermatologists = new ArrayList<>();
        List<Korisnik> allUsers = userRepository.findAll();

        for (Korisnik k : allUsers) {
            boolean isDermatologist = false;
            for (GrantedAuthority auth : k.getAuthorities()) {
                if (!auth.getAuthority().equalsIgnoreCase("ROLE_DERMATOLOGIST"))
                    continue;
                isDermatologist = true;
            }
            if (isDermatologist) dermatologists.add(k);
        }

        return dermatologists;
    }

    public List<Apoteka> getMyPharmacies(String username) {
        List<Apoteka> myPharmacies = new ArrayList<>();
        List<Apoteka> pharmacies = pharmacyRepository.findAll();
        for (Apoteka a : pharmacies) {
            for (Korisnik k : a.getZaposleni()) {
                if (username.equalsIgnoreCase(k.getUsername())) {
                    myPharmacies.add(a);
                }
            }
        }
        return myPharmacies;
    }

    public DermatologRadnoVremeDTO createDermatologistWTDTO(String username) {
        System.out.println("USERNAME NA POCETKU:");
        System.out.println(username);
        Korisnik k = findByUsername(username);
        DermatologRadnoVremeDTO dermatolog = new DermatologRadnoVremeDTO();
        dermatolog.setIme(k.getIme());
        dermatolog.setPrezime(k.getPrezime());
        dermatolog.setUsername(username);
        Map<String, RadnoInfo> ri = k.getRadnoInfo();
        List<RadnoVremeDTO> radnoVremeDTOS = new ArrayList<>();

        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");

        for (Map.Entry<String, RadnoInfo> entry : ri.entrySet()) {
            String apoteka = entry.getKey();
            for (Period p : entry.getValue().getBusinessHours()) {
                RadnoVremeDTO rvDTO = new RadnoVremeDTO();
                rvDTO.setApoteka(apoteka);
                rvDTO.setDoDatum(dateFormat.format(p.getDoDatum()));
                rvDTO.setOdDatum(dateFormat.format(p.getOdDatum()));
                rvDTO.setOdVreme(p.getOdVreme());
                rvDTO.setDoVreme(p.getDoVreme());
                System.out.println(rvDTO.getDoDatum());
                System.out.println(rvDTO.getOdDatum());
                radnoVremeDTOS.add(rvDTO);
            }
        }
        dermatolog.setRadnaVremena(radnoVremeDTOS);

        return dermatolog;
    }

    public DermatologRViTermDTO createDermatologRViTermDTO(String username, Apoteka a) {
        DermatologRViTermDTO dto = new DermatologRViTermDTO();
        Korisnik user = findByUsername(username);
        dto.setIme(user.getIme());
        dto.setPrezime(user.getPrezime());
        dto.setUsername(user.getUsername());
        List<Poseta> posete = posetaService.findUpcomingVisitsForDermatologInPharmacy(user, a);
        List<PosetaDTO> poseteDTOS = new ArrayList<>();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");

        for (Poseta p : posete) {
            PosetaDTO posetaDTO = new PosetaDTO();
            posetaDTO.setApoteka(p.getApoteka().getNaziv());
            posetaDTO.setDatum(dateFormat.format(p.getDatum()));
            posetaDTO.setTrajanje(p.getTrajanje());
            posetaDTO.setVreme(p.getVreme().toString());
            posetaDTO.setApoteka(a.getNaziv());
            posetaDTO.setZaposleni(username);

            poseteDTOS.add(posetaDTO);
        }
        dto.setZakazaniPregledi(poseteDTOS);

        List<WorkTimeDTO> workTimeDTOS = new ArrayList<>();
        Map<String, RadnoInfo> allRI = user.getRadnoInfo();

        for (Map.Entry<String, RadnoInfo> entry : allRI.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(a.getNaziv())) {
                RadnoInfo myRI = entry.getValue();
                for (Period p : myRI.getBusinessHours()) {
                    WorkTimeDTO workTimeDTO = new WorkTimeDTO();
                    workTimeDTO.setApoteka(a.getNaziv());
                    workTimeDTO.setOdDatum(dateFormat.format(p.getOdDatum()));
                    workTimeDTO.setDoDatum(dateFormat.format(p.getDoDatum()));
                    workTimeDTO.setOdVreme(p.getOdVreme());
                    workTimeDTO.setDoVreme(p.getDoVreme());
                    workTimeDTO.setNeradnidani(myRI.getNeradniDani());

                    workTimeDTOS.add(workTimeDTO);
                }
            }
        }
        dto.setRadnaVremena(workTimeDTOS);

        return dto;
    }

    public List<DermDTO> createDermDtos(List<Korisnik> dermatologists) {
        List<DermDTO> dermatologDTOS = new ArrayList<>();
        for (Korisnik d : dermatologists) {
            DermDTO dermatologDTO = new DermDTO();
            dermatologDTO.setIme(d.getIme());
            dermatologDTO.setPrezime(d.getPrezime());
            dermatologDTO.setOcena(d.getOcena());
            dermatologDTO.setUsername(d.getUsername());
            dermatologDTO.setRadiUApotekama(getMyPharmacies(d.getUsername()));
            dermatologDTOS.add(dermatologDTO);
        }
        return  dermatologDTOS;
    }
}
