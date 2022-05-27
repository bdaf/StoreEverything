package pl.team.marking.projectjavaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.team.marking.projectjavaweb.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
