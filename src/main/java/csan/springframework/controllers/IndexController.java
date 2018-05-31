package csan.springframework.controllers;

import java.util.Comparator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import csan.springframework.model.Recipe;
import csan.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class IndexController {
	
	private final RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	//public Comparator<Recipe> compareRecipe = new Comparator<Recipe>() {

	//	@Override
	//	public int compare(Recipe o1, Recipe o2) {
			// TODO Auto-generated method stub
	//		return o1.getId().compareTo(o2.getId());
	//	}
	//};
	
	public Comparator<Recipe> compareRecipe= Comparator.comparing(Recipe::getId);
	

	@RequestMapping({"","/","index"})
	public String getIndexPage(Model model) {
		log.debug("hi im in controller");
		model.addAttribute("recipes", recipeService.getRecipes());
		model.addAttribute("compareRecipe",compareRecipe);
		return "index";
	}

}
