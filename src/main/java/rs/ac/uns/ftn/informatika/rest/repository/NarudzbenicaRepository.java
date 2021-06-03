package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Narudzbenica;

import java.util.List;

@Repository
public interface NarudzbenicaRepository extends JpaRepository<Narudzbenica,Long> {

    List<Narudzbenica> findByApoteka(Apoteka a);
    Narudzbenica findByID(Long id);
}
