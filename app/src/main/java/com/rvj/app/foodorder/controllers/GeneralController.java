package com.rvj.app.foodorder.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvj.app.foodorder.config.AppOperationConfiguration;
import com.rvj.app.foodorder.models.GetRestaurantResponse;
import com.rvj.app.foodorder.models.GetRestaurantsRequest;
import com.rvj.app.foodorder.ops.GetRestaurantsOperation;
import com.rvj.app.foodorder.services.FileUploadService;
import com.rvj.app.foodorder.utils.AppConstants;
import com.rvj.app.foodorder.utils.ValidationUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/gen")
public class GeneralController {

	@Autowired
	private AppOperationConfiguration opsConfiguration;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@GetMapping(path = "get/restaurant", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetRestaurantResponse> getRestaurant(@Valid @RequestBody GetRestaurantsRequest request, BindingResult bindingResult) {
		log.info("Started Processing get restaurants request, messageId=" + request.getMessageId());
		GetRestaurantResponse response = new GetRestaurantResponse();
		response.setMessageId(request.getMessageId());
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = ValidationUtils.getErrorMap(bindingResult);
			response.setErrors(errors);
			response.setMessage("Request processing failed, Enter the valide values");
			log.info("having constraint errors,stopped processing get restaurants Request, messageId=" + request.getMessageId());
			return new ResponseEntity<GetRestaurantResponse>(response, HttpStatus.BAD_REQUEST);
		}
		else {
			log.info("No constraint errors,started get tabel booking request");
			request.setAction(AppConstants.RES_SINGLE);
			GetRestaurantsOperation operation = opsConfiguration.getGetRestaurantsOperation(request);
			response = operation.run();
			response.setMessageId(request.getMessageId());
			if(response.getErrors().isEmpty()) {
				response.setMessage("get restaurants successfully.");
				log.info("get restaurants successfully");
				return new ResponseEntity<GetRestaurantResponse>(response, HttpStatus.OK);
			}
			else {
				response.setMessage("get restaurants failed");
				log.info("get restaurants failed");
				return new ResponseEntity<GetRestaurantResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	@GetMapping(path = "get/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImage(@PathVariable String imageId){
		byte[] imagebytes = fileUploadService.getImageBytes(imageId);
		return imagebytes;
	}
}
