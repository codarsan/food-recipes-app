package csan.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import csan.springframework.model.Recipe;
import csan.springframework.repositories.RecipeRepository;

public class ImageServiceImplTest {
	
	@Mock
	RecipeRepository recipeRepository;
	ImageService imageService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeRepository);
		
	}

	@Test
	public void testSaveImageFile() throws Exception{
		//given
		Long id = new Long(3L);
		Recipe recipe = new Recipe();
		recipe.setId(id);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		MockMultipartFile file = 
				new MockMultipartFile("imagefile", "test.txt", "text/plain", "Spring Framework Image".getBytes());
		when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);
		
		//when
		imageService.saveImageFile(recipe.getId(), file);
		
		Byte[] savedFile = recipeRepository.findById(id).get().getImage();
		
		byte[] byteSavedFile = new byte[savedFile.length];
		int j=0;
		for (Byte b : savedFile) {
			byteSavedFile[j++]= Byte.valueOf(b);
		}
			
		//then
		verify(recipeRepository, times(2)).findById(Mockito.anyLong());
		assertEquals(file.getBytes().length, byteSavedFile.length );
		
	}

}
