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
import pl.team.marking.projectjavaweb.service.CategoryService;
import pl.team.marking.projectjavaweb.service.MyUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    final CategoryService categoryService;
    final MyUserDetailsService myUserDetailsService;
    final RestConnector restConnector;
    public static final String CATEGORIES_TO_SAVE = "categories_to_save";

    public CategoryController(CategoryService aCategoryService, MyUserDetailsService aMyUserDetailsService,RestConnector restConnector) {
        this.categoryService = aCategoryService;
        this.myUserDetailsService = aMyUserDetailsService;
        this.restConnector = restConnector;
    }

    @GetMapping
    public String getCategories(@AuthenticationPrincipal MyUserDetails userDetails, Model model, HttpServletRequest request) {
        model.addAttribute("categories", categoryService.getAllWithSession(request.getSession()));
        String login = userDetails.getUsername();
        UserApp user = myUserDetailsService.getUserByLogin(login);
        model.addAttribute("currentUser", user);
        return "category/categories";
    }

    @GetMapping("/add")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        return "category/add";
    }

    @PostMapping("/add_post")
    public String addCategory(@ModelAttribute("category") Category category, HttpServletRequest request) throws JsonProcessingException {
        if(restConnector.checkCategory(category.getName())) {
            addCategoryToSession(category, request);
            return "redirect:/categories";
        }
        return "category/error";
    }

    private void addCategoryToSession(Category category, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Category> categoriesFromSession = (List<Category>) session.getAttribute(CATEGORIES_TO_SAVE);
        if(categoriesFromSession == null){
            categoriesFromSession = new ArrayList<>();
        }
        categoriesFromSession.add(category);
        session.setAttribute(CATEGORIES_TO_SAVE, categoriesFromSession);
    }
}
