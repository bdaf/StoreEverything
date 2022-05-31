package pl.team.marking.projectjavaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/index")
    public String getMainPage() {
        System.out.println("WYWOLANIE");
        return "index";
    }

    @GetMapping("/logged_admin")
    public String getLoggedPage() {
        System.out.println("WYWOLANIE");
        return "logged_admin";
    }

    // Login form
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Login form with error
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

}
