package rs.ac.uns.ftn.informatika.rest.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.rest.dto.*;
import rs.ac.uns.ftn.informatika.rest.model.*;
import rs.ac.uns.ftn.informatika.rest.repository.*;


@Service
public class KorisnikService {

	@Autowired
	protected KorisnikRepository userRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private OcenaRepository ocenaRepository;

	@Autowired
	private LekRepository lekRepository;

	@Autowired
	private ApotekaRepository apotekaRepository;

	@Autowired
	private ZalbaRepository zalbaRepository;

	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;


	public Korisnik findByUsername(String username) throws UsernameNotFoundException {
		Korisnik k = userRepository.findByUsername(username);
		return k;
	}

	public Korisnik findById(Long id) throws AccessDeniedException {
		Korisnik k = userRepository.findById(id).orElseGet(null);
		return k;
	}

	public List<Korisnik> findAll() throws AccessDeniedException {
		List<Korisnik> result = userRepository.findAll();
		return result;
	}

	public Korisnik saveKorisnik(UserRequest userRequest) throws Exception {
		Korisnik k = new Korisnik();
		k.setUsername(userRequest.getUsername());
		// pre nego sto postavimo lozinku u atribut hesiramo je
		k.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		k.setIme(userRequest.getFirstname());
		k.setPrezime(userRequest.getLastname());
		k.setActivated(false);
		k.setAdresa(userRequest.getAddress());
		k.setDrzava(userRequest.getCountry());
		k.setGrad(userRequest.getCity());
		k.setLastPasswordResetDate(new Date());
		k.setTelefon(userRequest.getPhone());
		k.setEmail(userRequest.getEmail());
		List<Authority> auth = findByName("ROLE_USER");
		if (auth == null)
			throw new Exception("No role with such name");
		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
		k.setAuthorities(new HashSet<>(auth));
		
		k = this.userRepository.save(k);
		return k;
	}

	public Korisnik editKorisnik(UserEditDTO user) {
		Korisnik k = findByEmail(user.getEmail());
		k.setIme(user.getName());
		k.setPrezime(user.getLastName());
		k.setAdresa(user.getAddress());
		k.setTelefon(user.getPhoneNumber());
		k.setDrzava(user.getCountry());
		k.setGrad(user.getCity());

		if (user.getOldPassword() != null && !user.getOldPassword().equalsIgnoreCase("")) {
			k.setPassword(user.getNewPassword());
		}

		k = this.userRepository.save(k);
		return k;
	}

	public Korisnik verifyKorisnik(String username) {
		System.out.println("I am in verify korisnik");
		Korisnik k = findByUsername(username);
		k.setActivated(true);
		k = this.userRepository.save(k);
		return k;
	}

	public List<Authority> findAuthorityById(Long id) {
		Authority auth = this.authorityRepository.getOne(id);
		List<Authority> auths = new ArrayList<>();
		auths.add(auth);
		return auths;
	}

	public List<Authority> findByName(String name) {
		Authority auth = this.authorityRepository.findByName(name);
		List<Authority> auths = new ArrayList<>();
		auths.add(auth);
		return auths;
	}

	public Korisnik findByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}

	public AllergiesDTO findAllergiesForUser(String username) {
		Korisnik k = findByUsername(username);
		return new AllergiesDTO(new ArrayList<String>(k.getAlergije()));
	}

	public List<String> addAllergyForUser(String username, AllergiesDTO dto) {
		Korisnik k = findByUsername(username);
		k.getAlergije().addAll(dto.getAllergies());
		userRepository.save(k);
		return new ArrayList<String>(k.getAlergije());
	}

	public boolean removeAllergyForUser(String username, String allergy) {
		Korisnik k = findByUsername(username);

		for(String s : k.getAlergije()) {
			if (s.equalsIgnoreCase(allergy)) {
				k.getAlergije().remove(s);
				userRepository.save(k);
				return true;
			}
		}
		return false;
	}

    public void leaveGrade(OcenaDTO dto, String username) {

		Korisnik k = findByUsername(username);

		Ocena o = new Ocena();
		o.setNote(dto.getNote());
		o.setOd(k);
		o.setRecipientID(dto.getRecipientID());
		o.setOcena(dto.getOcena());
		Ocena.VrstaPrimaoca vp;
		if (dto.getVrstaPrimaoca().equalsIgnoreCase("apoteka")) {
			o.setVrstaPrimaoca(Ocena.VrstaPrimaoca.APOTEKA);
			vp = Ocena.VrstaPrimaoca.APOTEKA;
		}
		else if (dto.getVrstaPrimaoca().equalsIgnoreCase("lek")) {
			o.setVrstaPrimaoca(Ocena.VrstaPrimaoca.LEK);
			vp = Ocena.VrstaPrimaoca.LEK;
		}
		else {
			o.setVrstaPrimaoca(Ocena.VrstaPrimaoca.OSOBA);
			vp = Ocena.VrstaPrimaoca.OSOBA;
		}
		if (checkIfGradeAlreadyExists(o, k)) {

			updateRecipientGrade(vp, o);
			return;
		}

		o = ocenaRepository.save(o);

		updateRecipientGrade(vp,o);

    }

    private void updateRecipientGrade(Ocena.VrstaPrimaoca vp, Ocena o) {
		List<Ocena> ocene = ocenaRepository.findAll();

		if(vp == Ocena.VrstaPrimaoca.APOTEKA) {
			Apoteka a = apotekaRepository.findByID(o.getRecipientID());
			leaveGradeForPharmacy(a, ocene);
		}
		else if(vp == Ocena.VrstaPrimaoca.LEK) {
			Lek l = lekRepository.findLekByID(o.getRecipientID());
			leaveGradeForMedicine(l, ocene);
		}
		else if(vp == Ocena.VrstaPrimaoca.OSOBA) {
			Korisnik korisnik = userRepository.findByID(o.getRecipientID());
			leaveGradeForEmployee(korisnik, ocene);

		}
	}

	private boolean checkIfGradeAlreadyExists(Ocena ocena, Korisnik k) {

		List<Ocena> ocene = ocenaRepository.findAll();
		for(Ocena g : ocene) {
			if(g.getRecipientID() == ocena.getRecipientID() && g.getVrstaPrimaoca() == ocena.getVrstaPrimaoca() &&
			   g.getOd().getID() == ocena.getOd().getID()) {
				//ako postoji vec ova ocena treba je promeniti i to je to
				g.setNote(ocena.getNote());
				g.setOcena(ocena.getOcena());
				ocenaRepository.save(g);
				return true;
			}
		}

		return false;

	}

	private void leaveGradeForPharmacy(Apoteka a, List<Ocena> ocene) {
		int counter = 1;
		float sum = 5;
		for (Ocena grade : ocene) {
			if(a.getID() == grade.getRecipientID()) {
				sum += grade.getOcena();
				counter = counter + 1;
			}
		}
		// +5 +1 da bi se pocetna ocena 5 uzela u obzir
		a.setOcena(sum / counter);
		apotekaRepository.save(a);
		System.out.println("SUM // Counter");
		System.out.println(sum + " // " + counter);
	}

	private void leaveGradeForMedicine(Lek l, List<Ocena> ocene) {

		int counter = 1;
		float sum = 5;
		for (Ocena grade : ocene) {
			if(l.getID() == grade.getRecipientID()) {
				sum += grade.getOcena();
				counter = counter + 1;
			}


		}

		l.setOcena(sum / counter);
		lekRepository.save(l);
		System.out.println("SUM // Counter");
		System.out.println(sum + " // " + counter);
	}

	private void leaveGradeForEmployee(Korisnik k, List<Ocena> ocene) {

		int counter = 1;
		float sum = 5;
		for (Ocena grade : ocene) {
			if(k.getID() == grade.getRecipientID()) {
				sum += grade.getOcena();
				counter = counter + 1;
			}
		}

		k.setOcena(sum / counter);
		userRepository.save(k);
		System.out.println("SUM // Counter");
		System.out.println(sum + " // " + counter);
	}

	public void updateSubsForUser(SubCheckDTO dto, String username) {
		Korisnik k = findByUsername(username);
		k.getLoyaltyInfo().getPratiPromocije().put(dto.getNaziv(), dto.isPrati());
		userRepository.save(k);
  	}

	public void addGodisnjiInfo(TimeOffZahtev timeOffZahtev) {
		Korisnik k = timeOffZahtev.getPodnosilac();
		GodisnjiInfo godisnjiInfo = new GodisnjiInfo();
		godisnjiInfo.setNaGodisnjem(true);
		godisnjiInfo.setOdDatuma(timeOffZahtev.getOdDatuma());
		godisnjiInfo.setDoDatuma(timeOffZahtev.getDoDatuma());
		k.setGodisnjiInfo(godisnjiInfo);
		userRepository.save(k);
	}

	public void leaveComplaint(ZalbaDTO dto, String username) {

		Zalba z = new Zalba();
		z.setDatum(new Date());
		z.setIdPrimaoca(dto.getIdPrimaoca());
		z.setVrstaPrimaoca(dto.getVrstaPrimaoca());
		z.setOd(findByUsername(username));
		z.setText(dto.getText());

		zalbaRepository.save(z);
	}

	public boolean resetPenaltiesIfNeeded(String username) {

		Korisnik k = findByUsername(username);
		if (k.getLoyaltyInfo().getMonthOfLastReset() == LocalDate.now().getMonthValue()) {
			// no reset needed already reset for this month
			return false;
		}
		k.getLoyaltyInfo().setMonthOfLastReset(LocalDate.now().getMonthValue());
		k.getLoyaltyInfo().setPenali(0);
		userRepository.save(k);
		return true;
	}
}
