package csan.springframework.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import csan.springframework.model.Recipe;
import csan.springframework.services.RecipeService;

public class IndexControllerTest {
	
	IndexController indexController;
	@Mock
	RecipeService recipeService;
	@Mock
	Model model;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		indexController = new IndexController(recipeService);
	}
	
	@Test
	public void testMockMvc () throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("index"));
	}
	
	@Test
	public void testGetIndexPage() throws Exception {
		
		//given
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(new Recipe());
		Recipe recipe = new Recipe();
		recipe.setId(Long.valueOf("2"));
		recipes.add(recipe);
		
		@SuppressWarnings("unchecked")
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
		
		//when
		when(recipeService.getRecipes()).thenReturn(recipes);
		
		//then
		String viewName = indexController.getIndexPage(model);
		assertEquals("index", viewName);
		verify(recipeService,times(1)).getRecipes();
		verify(model,times(1)).addAttribute(Mockito.eq("recipes"),argumentCaptor.capture());
		Set<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(2, setInController.size());
	}

}
