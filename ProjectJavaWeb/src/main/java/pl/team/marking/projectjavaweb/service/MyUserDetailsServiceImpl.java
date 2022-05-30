package pl.team.marking.projectjavaweb.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.team.marking.projectjavaweb.entity.MyUserDetails;
import pl.team.marking.projectjavaweb.entity.UserApp;
import pl.team.marking.projectjavaweb.repository.UserRepository;

import java.util.Locale;
import java.util.Optional;

@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    final UserRepository userRepository;

    public MyUserDetailsServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String aUsername) throws UsernameNotFoundException {
        Optional<UserApp> user = userRepository.findUserByLogin(aUsername);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + aUsername));
        return user.map(MyUserDetails::new).get();
    }

    @Override
    public void save(UserApp aUserApp) {
        // Make login lowercase
        aUserApp.setLogin(aUserApp.getLogin().toLowerCase(Locale.ROOT));
    }
}
