package pl.team.marking.projectjavaweb.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.team.marking.projectjavaweb.entity.Information;
import pl.team.marking.projectjavaweb.entity.MyUserDetails;
import pl.team.marking.projectjavaweb.entity.UserApp;
import pl.team.marking.projectjavaweb.service.MyUserDetailsService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final MyUserDetailsService myUserDetailsService;

    @GetMapping
    public String getAllUsers(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        List<UserApp> usersToShow = myUserDetailsService.getAllUsers();

        model.addAttribute("users", usersToShow);
        return "user/users";
    }
}

