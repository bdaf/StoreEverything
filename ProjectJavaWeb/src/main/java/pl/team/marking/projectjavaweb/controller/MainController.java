package pl.team.marking.projectjavaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/index")
    public String getMainPage() {
        System.out.println("WYWOLANIE");
        return "start";
    }

    @GetMapping("/logged")
    public String getLoggedPage() {
        System.out.println("WYWOLANIE");
        return "logged";
    }

}
