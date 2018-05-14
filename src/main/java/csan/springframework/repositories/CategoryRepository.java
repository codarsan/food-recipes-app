package csan.springframework.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import csan.springframework.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{
	
	Optional<Category> findBycategoryName (String categoryName);

}
