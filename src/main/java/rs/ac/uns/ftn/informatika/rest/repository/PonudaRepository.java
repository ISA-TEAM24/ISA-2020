package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.rest.model.Narudzbenica;
import rs.ac.uns.ftn.informatika.rest.model.Ponuda;

import java.util.List;

@Repository
public interface PonudaRepository extends JpaRepository<Ponuda, Long> {

    List<Ponuda> findByNarudzbenica(Narudzbenica n);
}
