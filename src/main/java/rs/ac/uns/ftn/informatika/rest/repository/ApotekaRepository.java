package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;

import java.util.List;

@Repository
public interface ApotekaRepository extends JpaRepository<Apoteka, Long> {

    List<Apoteka> findAll();

    Apoteka findByNaziv(String naziv);

    Apoteka findByZaposleni(Korisnik zaposleni);
}
