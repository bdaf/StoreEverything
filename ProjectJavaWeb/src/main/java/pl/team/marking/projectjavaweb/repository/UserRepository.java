package pl.team.marking.projectjavaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.team.marking.projectjavaweb.entity.UserApp;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApp, Long> {
    Optional<UserApp> findUserByLogin(String aLogin);
}
