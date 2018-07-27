package csan.springframework.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import csan.springframework.model.Recipe;
import csan.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
	
	RecipeRepository recipeRepository;

	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void saveImageFile(Long recipeId, MultipartFile file) {
		
		try {
			Recipe recipe = recipeRepository.findById(recipeId).get();
			Byte[] byteObjectFile = new Byte[file.getBytes().length];
			int i=0;
			
			for (byte b : file.getBytes()) {
				byteObjectFile[i++]=b;
			}
			
			recipe.setImage(byteObjectFile);
			recipeRepository.save(recipe);
			
		} catch (Exception e) {
			log.error("Error Occured ", e);
			e.printStackTrace();
		}
	}
}
