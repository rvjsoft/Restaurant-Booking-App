package com.rvj.app.foodorder.controllers;

import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rvj.app.foodorder.config.AppOperationConfiguration;
import com.rvj.app.foodorder.entity.enums.FoodType;
import com.rvj.app.foodorder.models.GetRestaurantResponse;
import com.rvj.app.foodorder.models.GetRestaurantsRequest;
import com.rvj.app.foodorder.models.TableAvailRequest;
import com.rvj.app.foodorder.models.TableAvailResponse;
import com.rvj.app.foodorder.ops.GetRestaurantsOperation;
import com.rvj.app.foodorder.ops.GetTableAvailOperation;
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
	
	@GetMapping(path = "get/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetRestaurantResponse> getRestaurant(@Nullable @RequestParam("resId") Long resId,
			@Nullable @RequestParam("resName") String resName, @Nullable @RequestParam("type") FoodType type) {
		GetRestaurantsRequest request = new GetRestaurantsRequest();
		GetRestaurantResponse response = new GetRestaurantResponse();
		request.setResId(resId);
		request.setResName(resName);
		request.setType(type);
		response.setMessageId(request.getMessageId());
		log.info("No constraint errors,started get tabel booking request");
		request.setAction(AppConstants.RES_SINGLE);
		GetRestaurantsOperation operation = opsConfiguration.getGetRestaurantsOperation(request);
		response = operation.run();
		response.setMessageId(request.getMessageId());
		if (response.getErrors().isEmpty()) {
			response.setMessage("get restaurants successfully.");
			log.info("get restaurants successfully");
			return new ResponseEntity<GetRestaurantResponse>(response, HttpStatus.OK);
		} else {
			response.setMessage("get restaurants failed");
			log.info("get restaurants failed");
			return new ResponseEntity<GetRestaurantResponse>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "get/tablesAvail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TableAvailResponse> getTablesAvailability(@RequestParam("messageId") String messageId, @Nullable @RequestParam("resId") Long resId) {
		TableAvailRequest request = new TableAvailRequest();
		request.setMessageId(messageId);
		request.setResId(resId);
		log.info("Started Processing get tabel availability request, messageId=" + request.getMessageId());
		TableAvailResponse response = new TableAvailResponse();
		response.setMessageId(request.getMessageId());
		log.info("No constraint errors,started get tabel availability request");
		GetTableAvailOperation operation = opsConfiguration.getGetTableAvailOperation(request);
		response = operation.run();
		response.setMessageId(request.getMessageId());
		if (response.getErrors().isEmpty()) {
			response.setMessage("get tabel availability successfully.");
			log.info("get tabel availability successfully");
			return new ResponseEntity<TableAvailResponse>(response, HttpStatus.OK);
		} else {
			response.setMessage("get tabel availability failed");
			log.info("get tabel availability failed");
			return new ResponseEntity<TableAvailResponse>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "get/image/{imageId}", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getImage(@PathVariable String imageId){
		log.info("getting image");
		if(StringUtils.isBlank(imageId))
			return null;
		byte[] imagebytes = fileUploadService.getImageBytes(imageId);
		if(Objects.isNull(imagebytes))
			return null;
		return "data:image/jpg;base64,"+ (new String(imagebytes));
	}
}
