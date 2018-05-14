package csan.springframework.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import csan.springframework.model.Category;
import csan.springframework.model.UnitOfMesure;
import csan.springframework.repositories.CategoryRepository;
import csan.springframework.repositories.UnitOfMesureRepository;

@Controller
public class IndexController {
	
	private CategoryRepository categoryRepository;
	private UnitOfMesureRepository unitOfMesureRepository;
	
	public IndexController(CategoryRepository categoryRepository, UnitOfMesureRepository unitOfMesureRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMesureRepository = unitOfMesureRepository;
	}

	@RequestMapping({"","/","index"})
	public String getIndexPage() {
		Optional<Category> categoryOptional = categoryRepository.findBycategoryName("Italian");
		Optional<UnitOfMesure> uomOptional = unitOfMesureRepository.findByuom("Ounce");	
		System.out.println("Cat id : "+ categoryOptional.get().getId());
		System.out.println("Uom id : "+ uomOptional.get().getId());
		return "index";
	}

}
