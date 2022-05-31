package pl.team.marking.projectjavaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.team.marking.projectjavaweb.entity.Information;
import pl.team.marking.projectjavaweb.entity.UserApp;

import java.util.List;
import java.util.Optional;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long> {
    List<Information> findByUser(UserApp user);

    Optional<Information> findByLinkUuid(String uuid);

    Optional<Information> findByInformationIdAndUser(Long informationId, UserApp user);


//    Optional<Information> findByInformationIdAndShareUser(Long informationId, UserApp user);
}
