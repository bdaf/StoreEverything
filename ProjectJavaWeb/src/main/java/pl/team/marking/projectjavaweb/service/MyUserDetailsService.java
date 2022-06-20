package pl.team.marking.projectjavaweb.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.team.marking.projectjavaweb.entity.UserApp;

import java.util.List;

public interface MyUserDetailsService extends UserDetailsService {
    int save(UserApp aUserApp);

    UserApp getUserByLogin(String aLogin);
    List<UserApp> getAllUsers();

    List<String> getAllRoles();

    void updateUserRole(UserApp aUserToUpdateRole, String aUserLogin);
}
