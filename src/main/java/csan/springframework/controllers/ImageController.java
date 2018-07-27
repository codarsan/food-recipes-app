package csan.springframework.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import csan.springframework.commands.RecipeCommand;
import csan.springframework.services.ImageService;
import csan.springframework.services.RecipeService;

@Controller
public class ImageController {
	
	private final RecipeService recipeService;
	private final ImageService imageService;
	
	
	public ImageController(RecipeService recipeService, ImageService imageService) {
		this.recipeService = recipeService;
		this.imageService = imageService;
	}


	@GetMapping("recipe/{recipeId}/image")
	public String showUploadForm(@PathVariable String recipeId, Model model) {
		model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipeId)));
		return "recipe/imageuploadform";
	}

	@PostMapping("recipe/{recipeId}/image")
	public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
		imageService.saveImageFile(Long.valueOf(recipeId),file);
		return "redirect:/recipe/"+recipeId+"/show";
	}
	
	@GetMapping("recipe/{recipeId}/recipeimage")
	public void getImageFromDb (@PathVariable String recipeId, HttpServletResponse response) throws IOException{
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		
		if (recipeCommand.getImage()!=null) {
			byte[] byteArray = new byte[recipeCommand.getImage().length];
		
			int i = 0;
		
			for (Byte b : recipeCommand.getImage()) {
				byteArray[i++] = Byte.valueOf(b);
			}
		
			InputStream inputStream = new ByteArrayInputStream(byteArray);
		
			response.setContentType("image/jpeg");
			IOUtils.copy(inputStream, response.getOutputStream());
		}
	}
}
