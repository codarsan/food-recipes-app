package csan.springframework.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import csan.springframework.commands.RecipeCommand;
import csan.springframework.services.ImageService;
import csan.springframework.services.RecipeService;

public class ImageControllerTest {

	ImageController imageController;
	@Mock
	RecipeService recipeService;
	@Mock
	ImageService imageService;
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageController = new ImageController(recipeService, imageService);
		mockMvc = MockMvcBuilders.standaloneSetup(imageController)
				.setControllerAdvice(new ExceptionHandlerController())
				.build();
	}
	
	@Test
	public void badInputHandleTest() throws Exception{
		mockMvc.perform(get("/recipe/dsf/image"))
		.andExpect(status().isBadRequest())
		.andExpect(view().name("400error"));
	}

	@Test
	public void testShowUploadForm() throws Exception {
		//given
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);
		when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(command);
		
		//when
		mockMvc.perform(get("/recipe/1/image"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/imageuploadform"))
		.andExpect(model().attributeExists("recipe"));
		
		//then
		verify(recipeService,times(1)).findCommandById(Mockito.anyLong());
	}

	@Test
	public void testHandleImagePost() throws Exception {
		//given
		MockMultipartFile multipartFile = 
				new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring Framework".getBytes());
		
		//when
		mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/recipe/1/show"));
		
		//then
		verify(imageService,times(1)).saveImageFile(Mockito.anyLong(), Mockito.any());
		
	}
	
	@Test
	public void testGetImageFromDb()throws Exception {
		//given
		RecipeCommand  recipeCommand = new RecipeCommand();
		recipeCommand.setId(3L);
		
		String file = "fake image file";
		Byte[] boxedFile = new Byte[file.getBytes().length];
		
		int i = 0;
		
		for (byte b : file.getBytes()) {
			boxedFile[i++] = b;
		}
		recipeCommand.setImage(boxedFile);
		
		when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(recipeCommand);
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		//when
		imageController.getImageFromDb("3", response);
		byte[] responseByte = response.getContentAsByteArray();
		
		//then
		assertEquals(file.getBytes().length, responseByte.length);
	}

}
