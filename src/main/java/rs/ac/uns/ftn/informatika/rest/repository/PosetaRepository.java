package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.rest.model.Poseta;

import java.util.List;

@Repository
public interface PosetaRepository extends JpaRepository<Poseta, Long> {

    List<Poseta> findPosetaByZaposleniID(Long ID);
}
