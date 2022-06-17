package pl.team.marking.projectjavaweb.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import pl.team.marking.projectjavaweb.entity.Category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static pl.team.marking.projectjavaweb.controller.CategoryController.CATEGORIES_TO_SAVE;

@Service
public class CustomLogoutHandler implements LogoutHandler {


    private final CategoryService categoryService;

    public CustomLogoutHandler(CategoryService aCategoryService) {
        categoryService = aCategoryService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        HttpSession session = request.getSession();
        List<Category> categoriesToSave = (List<Category>) session.getAttribute(CATEGORIES_TO_SAVE);
        if (categoriesToSave != null) {
            for (Category category : categoriesToSave) {
                categoryService.save(category);
            }
        }
    }
}
