package csan.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import csan.springframework.commands.CategoryCommand;
import csan.springframework.model.Category;
import lombok.Synchronized;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

	@Synchronized
	@Nullable
	@Override
	public Category convert(CategoryCommand source) {
		if (source == null) {
		return null;
		}
		final Category category = new Category();
		category.setId(source.getId());
		category.setCategoryName(source.getCategoryName());
		return category;
	}
	
}
