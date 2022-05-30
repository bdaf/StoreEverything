package pl.team.marking.projectjavaweb.entity;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class MyUserDetails implements UserDetails {
    private String login;
    private String name;
    private String surname;
    private String password;
    private Integer age;
    private Boolean isActive;
    private List<GrantedAuthority> authorities;

    public MyUserDetails(UserApp aUserApp) {
        this.login = aUserApp.getLogin();
        this.name = aUserApp.getName();
        this.surname = aUserApp.getSurname();
        this.password = aUserApp.getPassword();
        this.age = aUserApp.getAge();
        this.authorities = List.of(new SimpleGrantedAuthority(aUserApp.getRole()));
        this.isActive = aUserApp.getIsActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
