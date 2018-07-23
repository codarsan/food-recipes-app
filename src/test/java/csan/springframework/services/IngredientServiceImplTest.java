package csan.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import csan.springframework.commands.IngredientCommand;
import csan.springframework.converters.IngredientCommandToIngredient;
import csan.springframework.converters.IngredientToIngredientCommand;
import csan.springframework.converters.UnitOfMesureCommandToUnitOfMesure;
import csan.springframework.converters.UnitOfMesureToUnitOfMesureCommand;
import csan.springframework.model.Ingredient;
import csan.springframework.model.Recipe;
import csan.springframework.repositories.RecipeRepository;
import csan.springframework.repositories.UnitOfMesureRepository;

public class IngredientServiceImplTest {
	
	@Mock
	RecipeRepository recipeRepository;
	@Mock
	UnitOfMesureRepository unitOfMesureRepository;
	IngredientService ingredientService;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMesureToUnitOfMesureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMesureCommandToUnitOfMesure());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(recipeRepository,unitOfMesureRepository, ingredientToIngredientCommand,ingredientCommandToIngredient);
	}

	@Test
	public void testFindByRecipeIdAndIngredientId() {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);

		Ingredient ingredient1 = new Ingredient();
		Ingredient ingredient2 = new Ingredient();
		Ingredient ingredient3 = new Ingredient();
		ingredient1.setId(1L);
		ingredient2.setId(2L);
		ingredient3.setId(3L);
		
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);
		
		//when
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L,3L);
		
		//then
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(Mockito.anyLong());
	}

	@Test
	public void testSaveIngredientCommand() throws Exception{
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(2L);
		command.setRecipeId(3L);
		
		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(2L);
		
		when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(Mockito.any())).thenReturn(savedRecipe);
		
		//when
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		//then
		assertEquals(Long.valueOf(2L), savedCommand.getId());
		verify(recipeRepository,times(1)).findById(Mockito.anyLong());
		verify(recipeRepository,times(1)).save(Mockito.any(Recipe.class));
		
	}
	
	@Test
	public void testdeleteIngredient() throws Exception{
		//given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(2L);
		Recipe recipe = new Recipe();
		recipe.setId(3L);
		recipe.addIngredient(ingredient);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);
		
		//when
		ingredientService.deleteById(Long.valueOf(3L), Long.valueOf(2L));
		
		//then
		assertEquals(0, recipeOptional.get().getIngredients().stream()
				.filter(ingre->ingre.getId().equals(Long.valueOf(2L))).count());
		
	}
}
