package pl.team.marking.projectjavaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.team.marking.projectjavaweb.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * " +
            "FROM category c " +
            "WHERE c.category_id <> :id",
            nativeQuery = true)
    List<Category> findByAllCategoryWithoutCategoryWithId(Long id);
}
