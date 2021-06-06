package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.rest.model.Authority;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;

import java.util.List;
import java.util.Set;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {


    Set<Authority> findAuthoritiesByID(Long ID);

    Korisnik findOneByEmail(String email);

    Korisnik findByUsername(String username);

    List<Korisnik> findAll();

    Korisnik findByID(Long ID);

}
