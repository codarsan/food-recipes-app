package csan.springframework.repositories;

import org.springframework.data.repository.CrudRepository;

import csan.springframework.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{

}
