package csan.springframework.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import csan.springframework.commands.IngredientCommand;
import csan.springframework.converters.IngredientCommandToIngredient;
import csan.springframework.converters.IngredientToIngredientCommand;
import csan.springframework.model.Ingredient;
import csan.springframework.model.Recipe;
import csan.springframework.repositories.RecipeRepository;
import csan.springframework.repositories.UnitOfMesureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
	
	RecipeRepository recipeRepository;
	UnitOfMesureRepository unitOfMesureRepository;
	IngredientToIngredientCommand ingredientToIngredientCommand;
	IngredientCommandToIngredient ingredientCommandToIngredient;

	public IngredientServiceImpl(RecipeRepository recipeRepository,
			UnitOfMesureRepository unitOfMesureRepository, IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.recipeRepository = recipeRepository;
		this.unitOfMesureRepository = unitOfMesureRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			log.error("recipe with Id : "+ recipeId +" not found");
		}
		Recipe recipe = recipeOptional.get();
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
		if (!ingredientCommandOptional.isPresent()) {
			log.error("ingredient with Id : "+ ingredientId +" not found");
		}
		return ingredientCommandOptional.get();
	}

	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
		
		if (!recipeOptional.isPresent()) {
			log.error("recipe with Id : "+ command.getRecipeId() +" not found");
			return new IngredientCommand();
		}else {
			Recipe recipe = recipeOptional.get();
			
			Optional<Ingredient> ingredientOptional = recipe.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(command.getId()))
					.findFirst();
			
			if (ingredientOptional.isPresent()) {
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(command.getDescription());
				ingredientFound.setAmount(command.getAmount());
				ingredientFound.setUom(unitOfMesureRepository
						.findById(command.getUom().getId())
						.orElseThrow(()-> new RuntimeException("uom not found")));
			}else {
				//add new ingredient
				Ingredient ingredient = ingredientCommandToIngredient.convert(command);
				ingredient.setRecipe(recipe);
				recipe.addIngredient(ingredient);
			}
			
			Recipe savedRecipe = recipeRepository.save(recipe);
			
			Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(ingredient->ingredient.getId().equals(command.getId()))
					.findFirst();
			
			if (!savedIngredientOptional.isPresent()) {
				savedIngredientOptional = savedRecipe.getIngredients().stream()
						.filter(recipeIngredients->recipeIngredients.getDescription().equals(command.getDescription()))
						.filter(recipeIngredients->recipeIngredients.getAmount().equals(command.getAmount()))
						.filter(recipeIngredient->recipeIngredient.getUom().getId().equals(command.getUom().getId()))
						.findFirst();
			}
					
			return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
		}		
	}

	@Override
	public void deleteById(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			log.error("Recipe Not found !");
		}
		Ingredient ingredientToDelete = recipeOptional.get().getIngredients().stream()
				.filter(ingred->ingred.getId().equals(ingredientId))
				.findFirst().get();
		recipeOptional.get().getIngredients().remove(ingredientToDelete);
		ingredientToDelete.setRecipe(null);
		recipeRepository.save(recipeOptional.get());
	}

}
