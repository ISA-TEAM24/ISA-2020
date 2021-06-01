package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.rest.model.ERecept;

import java.util.List;

@Repository
public interface EReceptRepository extends JpaRepository<ERecept, Long> {

    List<ERecept> findAllByEmail(String email);
}
