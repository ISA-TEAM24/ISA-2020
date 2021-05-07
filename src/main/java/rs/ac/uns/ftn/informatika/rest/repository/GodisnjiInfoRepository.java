package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.rest.model.GodisnjiInfo;

@Repository
public interface GodisnjiInfoRepository extends JpaRepository<GodisnjiInfo, Long> {
}
