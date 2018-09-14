package csan.springframework.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import csan.springframework.model.Category;
import csan.springframework.model.UnitOfMesure;
import csan.springframework.repositories.CategoryRepository;
import csan.springframework.repositories.UnitOfMesureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class BootStrapMySQL implements ApplicationListener<ContextRefreshedEvent>{

	private final CategoryRepository categoryRepository;
	private final UnitOfMesureRepository unitOfMesureRepository;
	
	public BootStrapMySQL(CategoryRepository categoryRepository, UnitOfMesureRepository unitOfMesureRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMesureRepository = unitOfMesureRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if (categoryRepository.count()==0) {
			log.debug("loading categories");
			loadCategories();
		}
		
		if (unitOfMesureRepository.count()==0) {
			log.debug("loading Units of Measure");
			loadUom();
		}
	}

	private void loadCategories() {
		
		Category cat1 = new Category();
		cat1.setCategoryName("American");
		categoryRepository.save(cat1);
		
		Category cat2 = new Category();
		cat2.setCategoryName("Mexican");
		categoryRepository.save(cat2);
		
		Category cat3 = new Category();
		cat3.setCategoryName("Italian");
		categoryRepository.save(cat3);
		
		Category cat4 = new Category();
		cat4.setCategoryName("Chinese");
		categoryRepository.save(cat4);
	}
	
	private void loadUom() {
		
		UnitOfMesure uom1 = new UnitOfMesure();
		uom1.setUom("Teaspoon");
		unitOfMesureRepository.save(uom1);

		UnitOfMesure uom2 = new UnitOfMesure();
		uom2.setUom("Tablespoon");
		unitOfMesureRepository.save(uom2);
		
		UnitOfMesure uom3 = new UnitOfMesure();
		uom3.setUom("Cup");
		unitOfMesureRepository.save(uom3);
		
		UnitOfMesure uom4 = new UnitOfMesure();
		uom4.setUom("Each");
		unitOfMesureRepository.save(uom4);
		
		UnitOfMesure uom5 = new UnitOfMesure();
		uom5.setUom("Pinch");
		unitOfMesureRepository.save(uom5);
		
		UnitOfMesure uom6 = new UnitOfMesure();
		uom6.setUom("Pint");
		unitOfMesureRepository.save(uom6);
		
		UnitOfMesure uom7 = new UnitOfMesure();
		uom7.setUom("Dash");
		unitOfMesureRepository.save(uom7);
	}
}
