package csan.springframework.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import csan.springframework.commands.RecipeCommand;
import csan.springframework.model.Recipe;
import csan.springframework.services.RecipeService;

public class RecipeControllerTest {
	
	@Mock
	RecipeService recipeService;
	
	RecipeController recipeController;
	
	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeController = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
	}

	@Test
	public void GetRecipeTest() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		when(recipeService.findById(Mockito.anyLong())).thenReturn(recipe);
		
		mockMvc.perform(get("/recipe/1/show"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/show"))
		.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void newRecipeTest() throws Exception {
		mockMvc.perform(get("/recipe/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/recipeform"))
		.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void saveOrUpdateTest() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);
		
		when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(command);
		
		mockMvc.perform(post("/recipe")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.param("id","")
		.param("description","some string"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/2/show"));			
	}

	@Test
	public void getUpdateTest() throws Exception {
		
		mockMvc.perform(get("/recipe/1/update"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/recipeform"));
	}
}
