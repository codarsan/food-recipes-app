package csan.springframework.services;

import java.util.Set;
import csan.springframework.commands.RecipeCommand;
import csan.springframework.model.Recipe;

public interface RecipeService {
	Set<Recipe> getRecipes();
	Recipe findById(Long id);
	void deleteById(Long id);
	RecipeCommand findCommandById(Long id);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
}
