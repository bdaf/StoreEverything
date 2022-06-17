package pl.team.marking.projectjavaweb.service;

import pl.team.marking.projectjavaweb.entity.Category;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    List<Category> getAllWithSession(HttpSession aSession);
}
