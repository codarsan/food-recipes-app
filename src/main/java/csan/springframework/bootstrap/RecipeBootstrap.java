package csan.springframework.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import csan.springframework.model.Category;
import csan.springframework.model.Difficulty;
import csan.springframework.model.Ingredient;
import csan.springframework.model.Notes;
import csan.springframework.model.Recipe;
import csan.springframework.model.UnitOfMesure;
import csan.springframework.repositories.CategoryRepository;
import csan.springframework.repositories.RecipeRepository;
import csan.springframework.repositories.UnitOfMesureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent>{
	
	private final RecipeRepository recipeRepository;
	private final CategoryRepository categoryRepository;
	private final UnitOfMesureRepository unitOfMesureRepository;
	
	public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
			UnitOfMesureRepository unitOfMesureRepository) {
		this.recipeRepository = recipeRepository;
		this.categoryRepository = categoryRepository;
		this.unitOfMesureRepository = unitOfMesureRepository;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// TODO Auto-generated method stub
		recipeRepository.saveAll(getRecipes());
		log.debug("bootstrap data");
	}

	private List<Recipe> getRecipes(){
		
		List<Recipe> recipes = new ArrayList<>(2);
		
		// **************************************************************************************
		
		Optional<UnitOfMesure> teaSpoonUomOptional = unitOfMesureRepository.findByUom("Teaspoon");
		
		if (!teaSpoonUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found !");	
		}
		
		Optional<UnitOfMesure> tableSpoonUomOptional = unitOfMesureRepository.findByUom("Tablespoon");
		
		if (!tableSpoonUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found !");	
		}
		
		Optional<UnitOfMesure> cupUomOptional = unitOfMesureRepository.findByUom("Cup");
		
		if (!cupUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found !");	
		}
		
		Optional<UnitOfMesure> eachUomOptional = unitOfMesureRepository.findByUom("Each");
		
		if (!eachUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found !");	
		}
		
		Optional<UnitOfMesure> pinchUomOptional = unitOfMesureRepository.findByUom("Pinch");
		
		if (!pinchUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found !");	
		}
		
		Optional<UnitOfMesure> pintUomOptional = unitOfMesureRepository.findByUom("Pint");
		
		if (!pintUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found !");	
		}
		
		Optional<UnitOfMesure> dashUomOptional = unitOfMesureRepository.findByUom("Dash");
		
		if (!dashUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found !");	
		}
		
		// get() UnitOfMesure objects from optional
		UnitOfMesure tableSpoonUom = tableSpoonUomOptional.get();
		UnitOfMesure teaSpoonUom = teaSpoonUomOptional.get();
		UnitOfMesure cupUom = cupUomOptional.get();
		UnitOfMesure eachUom = eachUomOptional.get();
		@SuppressWarnings("unused")
		UnitOfMesure pinchUom = pinchUomOptional.get();
		@SuppressWarnings("unused")
		UnitOfMesure pintUom = pintUomOptional.get();
		UnitOfMesure dashUom = dashUomOptional.get();
		
		// **************************************************************************************
		
		Optional<Category> americanCategoryOptional = categoryRepository.findByCategoryName("American");
		
		if (!americanCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected Category not found !");
		}
		
		Optional<Category> mexicanCategoryOptional = categoryRepository.findByCategoryName("Mexican");
		
		if (!mexicanCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected Category not found !");
		}
		
		Optional<Category> italianCategoryOptional = categoryRepository.findByCategoryName("Italian");
		
		if (!italianCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected Category not found !");
		}
		
		Optional<Category> chineseCategoryOptional = categoryRepository.findByCategoryName("Chinese");
		
		if (!chineseCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected Category not found !");
		}
		
		// get() categories
		Category americanCategory = americanCategoryOptional.get();
		Category mexicanCategory = mexicanCategoryOptional.get();
		@SuppressWarnings("unused")
		Category italianCategory = italianCategoryOptional.get();
		@SuppressWarnings("unused")
		Category chineseCategory = chineseCategoryOptional.get();
		
		// Guacamole Recipe
		Recipe guacRecipe = new Recipe();
		guacRecipe.setDescription("Perfect Guacamole");
		guacRecipe.setPrepTime(10);
		guacRecipe.setCookTime(0);
		guacRecipe.setDifficulty(Difficulty.EASY);
		guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl."+"\n"+
				"2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)"+"\n"+
				"3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\r\n" + 
				"\r\n" + 
				"Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\r\n" + 
				"\r\n" + 
				"Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste."+"\n"+
				"4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\r\n" + 
				"\r\n" + 
				"Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");
		Notes guacNotes = new Notes();
		guacNotes.setNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\r\n" + 
				"\r\n" + 
				"Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries (see our Strawberry Guacamole).\r\n" + 
				"\r\n" + 
				"The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\r\n" + 
				"\r\n" + 
				"To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\r\n" + 
				"\r\n" + 
				"For a deviled egg version with guacamole, try our Guacamole Deviled Eggs!");
		
		guacRecipe.setNotes(guacNotes);
		guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
		
		guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
		guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal("0.5"), teaSpoonUom));
		guacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(1), tableSpoonUom));
		guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal("0.25"), cupUom));
		guacRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom));
		guacRecipe.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), tableSpoonUom));
		guacRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(1), dashUom));
		guacRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal("0.5"), eachUom));
		
		guacRecipe.getCategories().add(americanCategory);
		guacRecipe.getCategories().add(mexicanCategory);
		
		recipes.add(guacRecipe);
		
		// Taco recipe
				Recipe tacoRecipe = new Recipe();
				tacoRecipe.setDescription("Spicy Grilled Chicken Tacos");
				tacoRecipe.setPrepTime(20);
				tacoRecipe.setCookTime(15);
				tacoRecipe.setServings(4);
				tacoRecipe.setDifficulty(Difficulty.MODERATE);
				tacoRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\r\n" + 
						"\r\n" + 
						"2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\r\n" + 
						"\r\n" + 
						"Set aside to marinate while the grill heats and you prepare the rest of the toppings.\r\n" + 
						"\r\n" + 
						"Spicy Grilled Chicken Tacos\r\n" + 
						"\r\n" + 
						"3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\r\n" + 
						"\r\n" + 
						"4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\r\n" + 
						"\r\n" + 
						"Wrap warmed tortillas in a tea towel to keep them warm until serving.\r\n" + 
						"\r\n" + 
						"5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\r\n");
				Notes tacoNotes = new Notes();
				tacoNotes.setNotes("Everyone can grab a warm tortilla from the pile and make their own tacos just they way they like them.\r\n" + 
						"\r\n" + 
						"You could also easily double or even triple this recipe for a larger party. A taco and a cold beer on a warm day? Now that’s living!");
				
				tacoRecipe.setNotes(tacoNotes);
				tacoRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
				
				tacoRecipe.addIngredient(new Ingredient("ancho chili powder", new BigDecimal(2), tableSpoonUom));
				tacoRecipe.addIngredient(new Ingredient("dried oregano", new BigDecimal(1), teaSpoonUom));
				tacoRecipe.addIngredient(new Ingredient("dried cumin", new BigDecimal(1), teaSpoonUom));
				tacoRecipe.addIngredient(new Ingredient("sugar", new BigDecimal(1), teaSpoonUom));
				tacoRecipe.addIngredient(new Ingredient("salt", new BigDecimal("0.5"), teaSpoonUom));
				tacoRecipe.addIngredient(new Ingredient("finely grated orange zest", new BigDecimal(1), tableSpoonUom));
				tacoRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom));
				tacoRecipe.addIngredient(new Ingredient("olive oil", new BigDecimal("2"), tableSpoonUom));
				tacoRecipe.addIngredient(new Ingredient("skinless, boneless chicken thighs (1 1/4 pounds)", new BigDecimal("4"), eachUom));
				
				tacoRecipe.getCategories().add(americanCategory);
				tacoRecipe.getCategories().add(mexicanCategory);
				
				recipes.add(tacoRecipe);
		// ******* test recipe !!!!
				
				Recipe tacoRecipe2 = new Recipe();
				tacoRecipe2.setDescription("Spicy Grilled Chicken Tacos 2");
				tacoRecipe2.setPrepTime(20);
				tacoRecipe2.setCookTime(15);
				tacoRecipe2.setServings(4);
				tacoRecipe2.setDifficulty(Difficulty.MODERATE);
				tacoRecipe2.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\r\n" + 
						"\r\n" + 
						"2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\r\n" + 
						"\r\n" + 
						"Set aside to marinate while the grill heats and you prepare the rest of the toppings.\r\n" + 
						"\r\n" + 
						"Spicy Grilled Chicken Tacos\r\n" + 
						"\r\n" + 
						"3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\r\n" + 
						"\r\n" + 
						"4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\r\n" + 
						"\r\n" + 
						"Wrap warmed tortillas in a tea towel to keep them warm until serving.\r\n" + 
						"\r\n" + 
						"5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\r\n");
				Notes tacoNotes2 = new Notes();
				tacoNotes2.setNotes("Everyone can grab a warm tortilla from the pile and make their own tacos just they way they like them.\r\n" + 
						"\r\n" + 
						"You could also easily double or even triple this recipe for a larger party. A taco and a cold beer on a warm day? Now that’s living!");
				tacoNotes2.setRecipe(tacoRecipe2);
				tacoRecipe2.setNotes(tacoNotes2);
				tacoRecipe2.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
				
				tacoRecipe2.getIngredients().add(new Ingredient("ancho chili powder", new BigDecimal(2), tableSpoonUom, tacoRecipe2));
				tacoRecipe2.getIngredients().add(new Ingredient("dried oregano", new BigDecimal(1), teaSpoonUom, tacoRecipe2));
				tacoRecipe2.getIngredients().add(new Ingredient("dried cumin", new BigDecimal(1), teaSpoonUom, tacoRecipe2));
				tacoRecipe2.getIngredients().add(new Ingredient("sugar", new BigDecimal(1), teaSpoonUom, tacoRecipe2));
				tacoRecipe2.getIngredients().add(new Ingredient("salt", new BigDecimal("0.5"), teaSpoonUom, tacoRecipe2));
				tacoRecipe2.getIngredients().add(new Ingredient("finely grated orange zest", new BigDecimal(1), tableSpoonUom, tacoRecipe2));
				tacoRecipe2.getIngredients().add(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom, tacoRecipe2));
				tacoRecipe2.getIngredients().add(new Ingredient("olive oil", new BigDecimal("2"), tableSpoonUom, tacoRecipe2));
				tacoRecipe2.getIngredients().add(new Ingredient("skinless, boneless chicken thighs (1 1/4 pounds)", new BigDecimal("4"), eachUom, tacoRecipe2));
				
				tacoRecipe2.getCategories().add(americanCategory);
				tacoRecipe2.getCategories().add(italianCategory);
				
				recipes.add(tacoRecipe2);				
		return recipes;
	}

}
