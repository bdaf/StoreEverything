package pl.team.marking.projectjavaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/index")
    public String getMainPage() {
        System.out.println("WYWOLANIE");
        return "index";
    }

    @RequestMapping("/logged")
    public String getLoggedPage() {
        System.out.println("WYWOLANIE");
        return "logged";
    }

    // Login form
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

}
