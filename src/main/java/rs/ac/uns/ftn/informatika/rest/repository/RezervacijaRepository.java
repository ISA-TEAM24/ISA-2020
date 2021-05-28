package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.rest.model.Rezervacija;

import java.util.List;

@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Long> {

    List<Rezervacija> findAll();
    Rezervacija findByID(Long ID);
    List<Rezervacija> findAllByApotekaID(Long ID);
    List<Rezervacija> findAllByPacijentID(Long ID);

}
