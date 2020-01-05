package com.rvj.app.foodorder.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Objects;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rvj.app.dataaccess.FoodRepository;
import com.rvj.app.foodorder.config.ImageUploadProperties;
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
	
	public String uploadImaage(FileUploadRequest request) {
		Restaurant restaurant = restaurantService.getRestaurant(request.getUserName());
		if(Objects.isNull(restaurant)) {
			return "user doesn't exist";
		}
		if(request.getFile().isEmpty()) {
			return "file is empty";
		}
		if(Objects.nonNull(request.getFoodId())) {
			String status = uploadRestaurantImage(request.getFile(), request.getUserName());
			return status;
		} else {
			String status = uploadRestaurantImage(request.getFile(), request.getUserName());
			return status;
		}
	}

	private String uploadRestaurantImage(MultipartFile file, String baseName) {
		try {
			byte[] bytes= file.getBytes();
			Base64.Encoder encoder = Base64.getEncoder();
			String fileName = baseName + encoder.encodeToString(RandomUtils.nextBytes(6));
			Path path = Paths.get(uploadProperties.getPath() + fileName + ".jpg");
			Files.write(path, bytes, StandardOpenOption.CREATE);
		} catch (Exception e) {
			return "upload failed";
		}
		return "upload success";
	}

}
