package pl.team.marking.projectjavaweb.service;

import org.springframework.stereotype.Service;
import pl.team.marking.projectjavaweb.entity.Category;
import pl.team.marking.projectjavaweb.repository.CategoryRepository;

import javax.servlet.http.HttpSession;
import java.util.List;

import static pl.team.marking.projectjavaweb.controller.CategoryController.CATEGORIES_TO_SAVE;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository aRepository) {
        repository = aRepository;
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Category> getAllWithSession(HttpSession aSession) {
        List<Category> resultCategories = getAll();
        List<Category> categoriesFromSession = (List<Category>) aSession.getAttribute(CATEGORIES_TO_SAVE);
        if(categoriesFromSession != null){
            resultCategories.addAll(categoriesFromSession);
        }
        return resultCategories;
    }

    @Override
    public void save(Category aCategory) {
        repository.save(aCategory);
    }
}
