package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.rest.model.Lek;

import java.util.List;

@Repository
public interface LekRepository extends JpaRepository<Lek, Long> {

    List<Lek> findAll();
    Lek findLekByID(Long ID);
}
