package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;

import java.util.List;

@Repository
public interface ApotekaRepository extends JpaRepository<Apoteka, Long> {

    List<Apoteka> findAll();
}
