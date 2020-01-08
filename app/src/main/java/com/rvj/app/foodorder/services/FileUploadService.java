package com.rvj.app.foodorder.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rvj.app.dataaccess.FoodRepository;
import com.rvj.app.dataaccess.ImageRespository;
import com.rvj.app.foodorder.config.ImageUploadProperties;
import com.rvj.app.foodorder.entity.Image;
import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.models.FileUploadRequest;

@Service
public class FileUploadService {
	
	@Autowired
	ImageUploadProperties uploadProperties;
	
	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
	FoodRepository foodRepository;
	
	@Autowired
	ImageRespository imageRespository;
	
	public String validate(FileUploadRequest request) {
		Restaurant restaurant = restaurantService.getRestaurant(request.getUserName());
		if(Objects.isNull(restaurant)) {
			return "restaurant doesn't exist";
		}
		if(request.getFile().isEmpty()) {
			return "file is invalid";
		}
		return null;
	}

	public String uploadImage(MultipartFile file, String baseName) {
		String fileName = null;
		try {
			byte[] bytes= file.getBytes();
			Base64.Encoder encoder = Base64.getEncoder();
			fileName = baseName + encoder.encodeToString(RandomUtils.nextBytes(6));
			fileName = fileName.replaceAll("/", "");
			Path path = Paths.get(uploadProperties.getPath() + fileName + ".jpg");
			Files.write(path, bytes, StandardOpenOption.CREATE);
			persistImage(fileName);
		} catch (Exception e) {
			return null;
		}
		return fileName;
	}
	
	@Transactional
	private void persistImage(String fileName) {
		try {
			imageRespository.save(new Image(fileName));
		} catch (Exception e) {
			throw e;
		}
	}

}