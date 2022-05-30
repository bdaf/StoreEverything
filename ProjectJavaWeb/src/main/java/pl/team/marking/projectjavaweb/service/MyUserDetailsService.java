package pl.team.marking.projectjavaweb.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.team.marking.projectjavaweb.entity.UserApp;

public interface MyUserDetailsService extends UserDetailsService {
    void save(UserApp aUserApp);
}
