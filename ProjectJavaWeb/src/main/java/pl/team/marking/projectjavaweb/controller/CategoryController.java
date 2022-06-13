package pl.team.marking.projectjavaweb.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.team.marking.projectjavaweb.RestConnector;
import pl.team.marking.projectjavaweb.entity.Category;
import pl.team.marking.projectjavaweb.entity.MyUserDetails;
import pl.team.marking.projectjavaweb.entity.UserApp;
import pl.team.marking.projectjavaweb.repository.CategoryRepository;
import pl.team.marking.projectjavaweb.repository.UserRepository;
import pl.team.marking.projectjavaweb.service.MyUserDetailsService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    final CategoryRepository categoryRepository;
    final MyUserDetailsService myUserDetailsService;
    final RestConnector restConnector;

    public CategoryController(CategoryRepository aCategoryRepository, MyUserDetailsService aMyUserDetailsService,RestConnector restConnector) {
        this.categoryRepository = aCategoryRepository;
        this.myUserDetailsService = aMyUserDetailsService;
        this.restConnector = restConnector;
    }

    @GetMapping
    public String getCategories(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        String login = userDetails.getUsername();
        UserApp user = myUserDetailsService.getUserByLogin(login);
        model.addAttribute("currentUser", user);
        return "category/categories";
    }

    @GetMapping("/add")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        System.out.println("Hello");
        return "category/add";
    }

    @PostMapping("/add_post")
    public String addCategory(@ModelAttribute("category") Category category) throws JsonProcessingException {
        System.out.println("hello2");
        if(restConnector.checkCategory(category.getName()))
        categoryRepository.save(category);
        System.out.println("hello3");
        return "redirect:/categories";
    }
}
