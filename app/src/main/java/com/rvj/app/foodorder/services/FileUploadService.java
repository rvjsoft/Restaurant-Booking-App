package com.rvj.app.foodorder.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Objects;

import javax.servlet.http.HttpSession;
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
import com.rvj.app.foodorder.utils.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
	@Autowired
	HttpSession session;
	
	public String validate(FileUploadRequest request) {
		request.setUserName((String)session.getAttribute(AppConstants.APP_USER));
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
			Path path = Paths.get(uploadProperties.getPath() + fileName + AppConstants.IMAGE_EXTENSION);
			Files.write(path, bytes, StandardOpenOption.CREATE);
			persistImage(fileName);
		} catch (Exception e) {
			e.printStackTrace();
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

	public byte[] getImageBytes(String imageId) {
		Path path = Paths.get(uploadProperties.getPath() + imageId + AppConstants.IMAGE_EXTENSION);
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] bytes = null;
		try {
			bytes = encoder.encode(Files.readAllBytes(path));
		} catch (IOException e) {
			log.info("exception caught when encoding image data, Exception:" + e.getMessage());
		}
		return bytes;
	}

	public void updateRestaurantImage(FileUploadRequest request, String imageId) {
		restaurantService.updateImage(request.getUserName(), imageId);
	}

	public void updateFoodImage(FileUploadRequest request, String imageId) {
		restaurantService.updateFoodImage(request.getUserName(),request.getFoodId().toString(), imageId);		
	}

}
