package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.informatika.rest.model.Poseta;

import java.util.List;

@Repository
public interface PosetaRepository extends JpaRepository<Poseta, Long> {

    List<Poseta> findPosetaByZaposleniID(Long ID);

    List<Poseta> findPosetaByPacijentID(Long ID);

    void deletePosetaByID(Long ID);

    Poseta findPosetaByID(Long ID);

}
