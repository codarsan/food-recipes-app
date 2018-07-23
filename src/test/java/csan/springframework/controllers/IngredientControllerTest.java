package csan.springframework.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import csan.springframework.commands.IngredientCommand;
import csan.springframework.commands.RecipeCommand;
import csan.springframework.services.IngredientService;
import csan.springframework.services.RecipeService;
import csan.springframework.services.UomService;

public class IngredientControllerTest {
	
	IngredientController ingredientController;
	@Mock
	RecipeService recipeService;
	@Mock
	IngredientService ingredientService;
	@Mock
	UomService uomService;
	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientController = new IngredientController(recipeService,ingredientService,uomService);
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
	}

	@Test
	public void testViewIngredients() throws Exception{
		RecipeCommand command = new RecipeCommand();
		
		when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(command);
		
		mockMvc.perform(get("/recipe/1/ingredients"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/list"))
		.andExpect(model().attributeExists("recipe"));
		
		verify(recipeService,times(1)).findCommandById(Mockito.anyLong());
	}

	@Test
	public void testShowIngredient() throws Exception{
		IngredientCommand ingredientCommand = new IngredientCommand();
		
		when(ingredientService.findByRecipeIdAndIngredientId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(ingredientCommand);
		
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/show"))
		.andExpect(model().attributeExists("ingredient"));
		
		verify(ingredientService,times(1)).findByRecipeIdAndIngredientId(Mockito.anyLong(), Mockito.anyLong());
	}
	
	@Test
	public void testUpdateIngredient() throws Exception{
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();
		
		//when
		when(ingredientService.findByRecipeIdAndIngredientId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(ingredientCommand);
		when(uomService.listAllUom()).thenReturn(new HashSet<>());
		
		//then
		mockMvc.perform(get("/recipe/1/ingredient/2/update"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/ingredientform"))
		.andExpect(model().attributeExists("ingredient"))
		.andExpect(model().attributeExists("uomList"));
		
		verify(ingredientService,times(1)).findByRecipeIdAndIngredientId(Mockito.anyLong(), Mockito.anyLong());
		verify(uomService,times(1)).listAllUom();
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(2L);
		command.setRecipeId(3L);
		
		//when
		when(ingredientService.saveIngredientCommand(Mockito.any())).thenReturn(command);
		
		//then
		mockMvc.perform(post("/recipe/3/ingredient")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.param("id","")
		.param("description","some string"))
		
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/3/ingredient/2/show"));			
	}
	
	@Test
	public void testDeleteIngredient() throws Exception{
		
		mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/1/ingredients"));
		
		verify(ingredientService,times(1)).deleteById(Mockito.anyLong(), Mockito.anyLong());
		
		}
}
