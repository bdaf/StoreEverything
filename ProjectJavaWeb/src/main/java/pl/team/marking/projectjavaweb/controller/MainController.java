package pl.team.marking.projectjavaweb.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.team.marking.projectjavaweb.entity.MyUserDetails;
import pl.team.marking.projectjavaweb.entity.UserApp;
import pl.team.marking.projectjavaweb.service.MyUserDetailsService;
import pl.team.marking.projectjavaweb.service.RemindService;

@AllArgsConstructor
@Controller
public class MainController {

    private final RemindService remindService;
    private final MyUserDetailsService myUserDetailsService;

    @GetMapping("/index")
    public String getMainPage() {
        return "index";
    }

    @GetMapping("/index_with_reminder")
    public String getMainPageOrReminderPage(@AuthenticationPrincipal MyUserDetails userDetails) {
        String login = userDetails.getUsername();
        UserApp user = myUserDetailsService.getUserByLogin(login);
        if(remindService.ifHasRemindInformation(user)){
            return "redirect:/informations/remind";
        }
        return "index";
    }

    @GetMapping("/logged_admin")
    public String getLoggedPage() {
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
