package pl.team.marking.projectjavaweb.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.team.marking.projectjavaweb.entity.UserApp;
import pl.team.marking.projectjavaweb.service.MyUserDetailsService;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final MyUserDetailsService userService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(MyUserDetailsService userService, PasswordEncoder aPasswordEncoder) {
        super();
        this.userService = userService;
        this.passwordEncoder = aPasswordEncoder;
    }

    @ModelAttribute("user")
    public UserApp userRegistrationApp() {
        return new UserApp();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@Valid @ModelAttribute("user") UserApp aUserApp, BindingResult result) {
        // return if form had errors
        if(result.hasErrors()) return "registration";

        // encoding password
        String encodedPassword = passwordEncoder.encode(aUserApp.getPassword());
        aUserApp.setPassword(encodedPassword);

        // saving to database
        userService.save(aUserApp);
        return "index";
    }
}
