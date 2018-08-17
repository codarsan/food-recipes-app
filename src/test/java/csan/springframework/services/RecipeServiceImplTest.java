package csan.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import csan.springframework.commands.RecipeCommand;
import csan.springframework.converters.RecipeCommandToRecipe;
import csan.springframework.converters.RecipeToRecipeCommand;
import csan.springframework.exceptions.NotFoundException;
import csan.springframework.model.Recipe;
import csan.springframework.repositories.RecipeRepository;

public class RecipeServiceImplTest {

	RecipeServiceImpl recipeService;
	@Mock
	RecipeRepository recipeRepository;
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;
	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository,recipeToRecipeCommand,recipeCommandToRecipe);
	}
	
	@SuppressWarnings("unused")
	@Test(expected = NotFoundException.class)
	public void testNotFoundId() {
		Optional<Recipe> emptyOptional = Optional.empty();
		
		when(recipeRepository.findById(Mockito.anyLong())).thenReturn(emptyOptional);
		
		Recipe recipe = recipeService.findById(1L);
	}

	@Test
	public void testfindById() {
		Recipe recipe = new Recipe();
		recipe.setId(3L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);
		
		Recipe recipeReturned = recipeService.findById(3L);
		assertNotNull("Null Recipe", recipeReturned);
		verify(recipeRepository,times(1)).findById(Mockito.anyLong());
		verify(recipeRepository,never()).findAll();
	}
	
	@Test
	public void testGetRecipes() {
		Recipe recipe = new Recipe();
		HashSet<Recipe> recipesData = new HashSet<Recipe>();
		recipesData.add(recipe);
		
		when(recipeService.getRecipes()).thenReturn(recipesData);
		
		Set<Recipe> recipes = recipeService.getRecipes();
		assertEquals(recipes.size(), 1);
		verify(recipeRepository,times(1)).findAll();
	}

	@Test
	public void testfindCommandById() {
		Recipe recipe = new Recipe();
		recipe.setId(3L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);
		
		RecipeCommand command = new RecipeCommand();
		command.setId(3L);
		
		when(recipeToRecipeCommand.convert(Mockito.any())).thenReturn(command);
		
		RecipeCommand commandById = recipeService.findCommandById(3L);
		assertNotNull("Null Recipe", commandById);
        verify(recipeRepository, times(1)).findById(Mockito.anyLong());
        verify(recipeRepository, never()).findAll();
	}
	
	@Test
	public void testDeleteById() throws Exception{
		Long idToDelete = Long.valueOf(1L);
		recipeService.deleteById(idToDelete);
		verify(recipeRepository,times(1)).deleteById(Mockito.anyLong());
	}
	
}
