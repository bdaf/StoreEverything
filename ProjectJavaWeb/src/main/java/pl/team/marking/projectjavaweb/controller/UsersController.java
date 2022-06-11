package pl.team.marking.projectjavaweb.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.team.marking.projectjavaweb.entity.UserApp;
import pl.team.marking.projectjavaweb.service.MyUserDetailsService;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final MyUserDetailsService myUserDetailsService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserApp> usersToShow = myUserDetailsService.getAllUsers();

        model.addAttribute("users", usersToShow);
        return "user/users";
    }

    @GetMapping("/{userLogin}")
    public String getUserById(@PathVariable String userLogin, Model model) {
        UserApp userToShow = myUserDetailsService.getUserByLogin(userLogin);
        List<String> rolesToShowWithoutParticularUserRole = myUserDetailsService.getAllRoles();

        rolesToShowWithoutParticularUserRole.remove(userToShow.getRole());

        model.addAttribute("user", userToShow);
        model.addAttribute("roles", rolesToShowWithoutParticularUserRole);
        return "user/user_edit_role";
    }

    @PostMapping("{userLogin}/update/role")
    public String updateRoleInUser(@ModelAttribute("users") UserApp userToUpdateRole, @PathVariable String userLogin) {
        myUserDetailsService.updateUserRole(userToUpdateRole, userLogin);
        return "redirect:/users?role_changed";
    }
}

