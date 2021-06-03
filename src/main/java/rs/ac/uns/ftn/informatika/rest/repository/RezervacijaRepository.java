package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.rest.model.Rezervacija;

import java.util.List;

@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Long> {

    List<Rezervacija> findAll();
    Rezervacija findByID(Long ID);
    List<Rezervacija> findAllByApotekaID(Long ID);
    List<Rezervacija> findAllByPacijentID(Long ID);
    void deleteRezervacijaByID(Long ID);

    @Query(value = "SELECT id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id\n" +
            "\tFROM public.rezervacija WHERE apoteka_id=?1 AND lek_id=?2 AND pacijent_id=?3", nativeQuery = true)
    List<Rezervacija> findRezervacijaByApotekaIDAndLekIDAndPacijentId(Long a, Long l, Long p);

    @Query(value = "SELECT id, datum_preuz, rok_za_preuzimanje, apoteka_id, lek_id, pacijent_id\n" +
            "\tFROM public.rezervacija WHERE apoteka_id=?1 AND lek_id=?2", nativeQuery = true)
    List<Rezervacija> findRezervacijaByApotekaIDAndLEKID(Long a, Long l);
}
