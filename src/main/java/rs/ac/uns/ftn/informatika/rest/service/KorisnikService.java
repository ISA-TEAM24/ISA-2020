package rs.ac.uns.ftn.informatika.rest.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

	public Korisnik saveKorisnik(UserRequest userRequest) {
		Korisnik k = new Korisnik();
		k.setUsername(userRequest.getUsername());
		// pre nego sto postavimo lozinku u atribut hesiramo je
		k.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		k.setIme(userRequest.getFirstname());
		k.setPrezime(userRequest.getLastname());
		k.setActivated(true);
		
		List<Authority> auth = findByName("ROLE_USER");
		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
		k.setAuthorities(new HashSet<>(auth));
		
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

}
