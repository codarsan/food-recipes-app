package csan.springframework.commands;

import java.util.HashSet;
import java.util.Set;
import csan.springframework.model.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

	private Long id;
	private byte[] image;
	private NotesCommand notes;
	private Set<IngredientCommand> ingredients = new HashSet<>();
	private Set<CategoryCommand> categories = new HashSet<>();
	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;
	private String directions;
	private Difficulty difficulty;
}
