package csan.springframework.services;

import java.util.Set;
import csan.springframework.model.Recipe;

public interface RecipeService {
	Set<Recipe> getRecipes();
	Recipe findById(Long id);
}
