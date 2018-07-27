package csan.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import csan.springframework.commands.IngredientCommand;
import csan.springframework.commands.UnitOfMesureCommand;
import csan.springframework.services.IngredientService;
import csan.springframework.services.RecipeService;
import csan.springframework.services.UomService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {
	
	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UomService uomService;
	
	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UomService uomService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.uomService = uomService;
	}

	@GetMapping("recipe/{id}/ingredients")
	public String viewIngredients(@PathVariable String id,Model model) {
		log.debug("controller view Ingredients");
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/ingredient/list";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
	public String showIngredient(@PathVariable String recipeId,@PathVariable String ingredientId,Model model) {
			log.debug("controller show Ingredient");
			model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		return "recipe/ingredient/show";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
	public String updateIngredient(@PathVariable String recipeId,@PathVariable String ingredientId,Model model) {
			log.debug("controller update ingredient");
			model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
			model.addAttribute("uomList", uomService.listAllUom());
		return "recipe/ingredient/ingredientform";
	}
	
	@PostMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		log.debug("saved recipe id :"+savedCommand.getRecipeId());
		log.debug("saved ingredient id :"+savedCommand.getId());
		return "redirect:/recipe/"+savedCommand.getRecipeId()+"/ingredient/"+savedCommand.getId()+"/show";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/new")
	public String newIngredient(@PathVariable String recipeId, Model model) {
		//RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(Long.valueOf(recipeId));
		
		model.addAttribute("ingredient", ingredientCommand);
		ingredientCommand.setUom(new UnitOfMesureCommand());
		model.addAttribute("uomList", uomService.listAllUom());
		return "recipe/ingredient/ingredientform";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		log.debug("Deleting ingredient id: "+ingredientId);
		ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		return "redirect:/recipe/"+recipeId+"/ingredients";
	}
}
