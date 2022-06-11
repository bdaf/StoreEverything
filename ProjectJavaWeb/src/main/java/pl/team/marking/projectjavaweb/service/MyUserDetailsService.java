package pl.team.marking.projectjavaweb.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.team.marking.projectjavaweb.entity.UserApp;

import java.util.List;

public interface MyUserDetailsService extends UserDetailsService {
    void save(UserApp aUserApp);

    UserApp getUserByLogin(String aLogin);
    List<UserApp> getAllUsers();

}
