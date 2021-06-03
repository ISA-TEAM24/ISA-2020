package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Upit;

import java.util.List;

@Repository
public interface UpitRepository extends JpaRepository<Upit, Long> {

    List<Upit> findByApotekaAndUspesan(Apoteka apoteka, boolean uspesan);
}
