package pl.team.marking.projectjavaweb.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.team.marking.projectjavaweb.entity.MyUserDetails;
import pl.team.marking.projectjavaweb.entity.UserApp;
import pl.team.marking.projectjavaweb.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pl.team.marking.projectjavaweb.entity.UserApp.*;

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

        userRepository.save(aUserApp);
    }

    @Override
    public UserApp getUserByLogin(String aLogin) {
        return userRepository.findUserByLogin(aLogin).get();
    }

    @Override
    public List<UserApp> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<String> getAllRoles() {
        return new ArrayList<>(List.of(LIMITED_USER, FULL_USER, ADMIN));
    }

    @Override
    public void updateUserRole(UserApp aUserToGetRoleFrom, String aUserLogin) {
        UserApp userFoundByLogin = userRepository.findUserByLogin(aUserLogin).get();
        userFoundByLogin.setRole(aUserToGetRoleFrom.getRole());
        userRepository.save(userFoundByLogin);
    }
}
