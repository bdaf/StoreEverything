package pl.team.marking.projectjavaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.team.marking.projectjavaweb.entity.UserApp;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp, String> {
    Optional<UserApp> findUserByLogin(String aLogin);
}
