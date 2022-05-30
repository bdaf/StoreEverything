package pl.team.marking.projectjavaweb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.team.marking.projectjavaweb.entity.Category;
import pl.team.marking.projectjavaweb.repository.CategoryRepository;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "category/categories";
    }

    @GetMapping("/add")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        System.out.println("Hello");
        return "category/add";
    }

    @PostMapping("/add_post")
    public String addCategory(@ModelAttribute("category") Category category) {
        System.out.println("hello2");
        categoryRepository.save(category);
        System.out.println("hello3");
        return "redirect:/categories";
    }
}
