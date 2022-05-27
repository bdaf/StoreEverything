package pl.team.marking.projectjavaweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.team.marking.projectjavaweb.entity.MyUserDetails;
import pl.team.marking.projectjavaweb.entity.UserApp;
import pl.team.marking.projectjavaweb.repository.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String aUsername) throws UsernameNotFoundException {
        Optional<UserApp> user = userRepository.findUserByLogin(aUsername);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + aUsername));
        return user.map(MyUserDetails::new).get();
    }
}
