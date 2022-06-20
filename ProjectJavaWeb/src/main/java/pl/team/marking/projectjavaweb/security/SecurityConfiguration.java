package pl.team.marking.projectjavaweb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;
import pl.team.marking.projectjavaweb.service.CustomLogoutHandler;
import pl.team.marking.projectjavaweb.service.RemindService;

import static pl.team.marking.projectjavaweb.entity.UserApp.*;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final RemindService remindService;
    private final LogoutHandler logoutHandler;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public SecurityConfiguration(UserDetailsService userDetailsService, RemindService aRemindService, CustomLogoutHandler aLogoutHandler) {
        super();
        this.userDetailsService = userDetailsService;
        this.remindService = aRemindService;
        logoutHandler = aLogoutHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers( "/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                // For All
                .antMatchers("/registration", "/login-error", "/logout", "/login", "/index", "/informations/share/link/**", "/css/**", "/js/**", "/").permitAll()
                // For Admin
                .antMatchers("/users**").hasAuthority(ADMIN)
                // For any full users and admin
                .antMatchers("/categories").hasAnyAuthority(FULL_USER, ADMIN)
                // For any users
                .antMatchers("/categories").hasAnyAuthority(LIMITED_USER, FULL_USER, ADMIN)
                .anyRequest().authenticated()

                // Details about logging
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")

                .defaultSuccessUrl("/index_with_reminder?just_logged", true)
                .and()
                .logout()
                .addLogoutHandler(logoutHandler)
                .invalidateHttpSession(true).clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
