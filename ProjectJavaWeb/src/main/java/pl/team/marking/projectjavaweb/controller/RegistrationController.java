package pl.team.marking.projectjavaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.team.marking.projectjavaweb.entity.UserApp;
import pl.team.marking.projectjavaweb.service.MyUserDetailsService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final MyUserDetailsService userService;

    public RegistrationController(MyUserDetailsService userService) {
        super();
        this.userService = userService;
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
    public String registerUserAccount(@ModelAttribute("user") UserApp aUserApp) {
        userService.save(aUserApp);
        return "index";
//        return "redirect:/registration?success";
    }
}
