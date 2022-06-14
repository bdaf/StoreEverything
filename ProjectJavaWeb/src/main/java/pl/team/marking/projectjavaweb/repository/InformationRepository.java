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
    List<Information> findByUserOrderByTitle(UserApp user);

    List<Information> findByUserOrderByTitleDesc(UserApp user);

    List<Information> findByUserOrderByAddingDate(UserApp user);

    List<Information> findByUserOrderByAddingDateDesc(UserApp user);

    List<Information> findByUserOrderByCategory(UserApp user);

    List<Information> findByUserOrderByCategoryDesc(UserApp user);

    @Query(value = "SELECT i " +
            "FROM Information i " +
            "WHERE i.user = :user " +
            "AND i.remindDate <= CURRENT_DATE")
    List<Information> findRemindByUser(UserApp user);

    @Query(value = "SELECT COUNT(i)" +
            "FROM Information i " +
            "WHERE i.user = :user " +
            "AND i.remindDate <= CURRENT_DATE")
    Integer countRemindInformationByUser(UserApp user);

    @Query(value = "SELECT * " +
            "FROM information i " +
            "JOIN information_published_user ipu ON i.information_id = ipu.information_information_id " +
            "AND ipu.published_user_login = :login", nativeQuery = true)
    List<Information> findShareInformationForUser(String login);

    @Query(value = "SELECT * " +
            "FROM information i " +
            "JOIN information_published_user ipu ON i.information_id = ipu.information_information_id " +
            "AND ipu.published_user_login = :login AND ipu.information_information_id = :informationId",
            nativeQuery = true)
    Optional<Information> findShareInformationForUser(Long informationId, String login);

    Optional<Information> findByLinkUuid(String uuid);

    Optional<Information> findByInformationIdAndUser(Long informationId, UserApp user);
}
