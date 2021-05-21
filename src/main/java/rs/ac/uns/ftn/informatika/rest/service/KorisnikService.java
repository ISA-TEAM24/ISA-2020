package rs.ac.uns.ftn.informatika.rest.service;

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

import rs.ac.uns.ftn.informatika.rest.dto.UserEditDTO;
import rs.ac.uns.ftn.informatika.rest.dto.UserRequest;
import rs.ac.uns.ftn.informatika.rest.model.Authority;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.repository.AuthorityRepository;
import rs.ac.uns.ftn.informatika.rest.repository.KorisnikRepository;


@Service
public class KorisnikService {

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


}
