package com.rvj.app.foodorder.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvj.app.foodorder.config.AppOperationConfiguration;
import com.rvj.app.foodorder.models.AddFoodRequest;
import com.rvj.app.foodorder.models.AddFoodResponse;
import com.rvj.app.foodorder.models.DeleteFoodRequest;
import com.rvj.app.foodorder.models.DeleteFoodResponse;
import com.rvj.app.foodorder.models.FoodStatusRequest;
import com.rvj.app.foodorder.models.FoodStatusResponse;
import com.rvj.app.foodorder.models.RestaurantStatusReqeust;
import com.rvj.app.foodorder.models.RestaurantStatusResponse;
import com.rvj.app.foodorder.models.UpdateFoodRequest;
import com.rvj.app.foodorder.models.UpdateFoodResponse;
import com.rvj.app.foodorder.ops.AddFoodOperation;
import com.rvj.app.foodorder.ops.DeleteFoodOperation;
import com.rvj.app.foodorder.ops.FoodStatusOperation;
import com.rvj.app.foodorder.ops.RestaurantStatusOperation;
import com.rvj.app.foodorder.ops.UpdateFoodOperation;
import com.rvj.app.foodorder.utils.ValidationUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
	
	@Autowired
	private AppOperationConfiguration opsConfiguration;
	
	@PostMapping(path = "add/food", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AddFoodResponse> addFood(@Valid @RequestBody AddFoodRequest request, BindingResult bindingResult) {
		log.info("Started Processing Add food request, messageId=" + request.getMessageId());
		AddFoodResponse response = new AddFoodResponse();
		response.setMessageId(request.getMessageId());
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = ValidationUtils.getErrorMap(bindingResult);
			response.setErrors(errors);
			response.setMessage("Request processing failed, Enter the valide values");
			log.info("having constraint errors,stopped processing Add food Request, messageId=" + request.getMessageId());
			return new ResponseEntity<AddFoodResponse>(response, HttpStatus.BAD_REQUEST);
		}
		else {
			log.info("No constraint errors,started adding food");
			AddFoodOperation operation = opsConfiguration.getAddFoodOperation(request);
			response = operation.run();
			response.setMessageId(request.getMessageId());
			if(response.getErrors().isEmpty()) {
				response.setMessage("food Added successfully.");
				log.info("food added successfully");
				return new ResponseEntity<AddFoodResponse>(response, HttpStatus.OK);
			}
			else {
				response.setMessage("food addition failed");
				log.info("adding of food failed");
				return new ResponseEntity<AddFoodResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	@PostMapping(path = "update/food", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateFoodResponse> updateFood(@Valid @RequestBody UpdateFoodRequest request, BindingResult bindingResult) {
		log.info("Started Processing update food request, messageId=" + request.getMessageId());
		UpdateFoodResponse response = new UpdateFoodResponse();
		response.setMessageId(request.getMessageId());
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = ValidationUtils.getErrorMap(bindingResult);
			response.setErrors(errors);
			response.setMessage("Request processing failed, Enter the valide values");
			log.info("having constraint errors,stopped processing update food Request, messageId=" + request.getMessageId());
			return new ResponseEntity<UpdateFoodResponse>(response, HttpStatus.BAD_REQUEST);
		}
		else {
			log.info("No constraint errors,started updating food");
			UpdateFoodOperation operation = opsConfiguration.getUpdateFoodOperation(request);
			response = operation.run();
			response.setMessageId(request.getMessageId());
			if(response.getErrors().isEmpty()) {
				response.setMessage("food updated successfully.");
				log.info("food updated successfully");
				return new ResponseEntity<UpdateFoodResponse>(response, HttpStatus.OK);
			}
			else {
				response.setMessage("food updation failed");
				log.info("updating of food failed");
				return new ResponseEntity<UpdateFoodResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	@PostMapping(path = "delete/food", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteFoodResponse> deleteFood(@Valid @RequestBody DeleteFoodRequest request, BindingResult bindingResult) {
		log.info("Started Processing delete food request, messageId=" + request.getMessageId());
		DeleteFoodResponse response = new DeleteFoodResponse();
		response.setMessageId(request.getMessageId());
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = ValidationUtils.getErrorMap(bindingResult);
			response.setErrors(errors);
			response.setMessage("Request processing failed, Enter the valide values");
			log.info("having constraint errors,stopped processing delete food Request, messageId=" + request.getMessageId());
			return new ResponseEntity<DeleteFoodResponse>(response, HttpStatus.BAD_REQUEST);
		}
		else {
			log.info("No constraint errors,started deleting food");
			DeleteFoodOperation operation = opsConfiguration.getDeleteFoodOperation(request);
			response = operation.run();
			response.setMessageId(request.getMessageId());
			if(response.getErrors().isEmpty()) {
				response.setMessage("food deleted successfully.");
				log.info("food deleted successfully");
				return new ResponseEntity<DeleteFoodResponse>(response, HttpStatus.OK);
			}
			else {
				response.setMessage("food deletion failed");
				log.info("deleting of food failed");
				return new ResponseEntity<DeleteFoodResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	@PostMapping(path = "status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestaurantStatusResponse> changeStatus(@Valid @RequestBody RestaurantStatusReqeust request, BindingResult bindingResult) {
		log.info("Started Processing change restaurant status, messageId=" + request.getMessageId());
		RestaurantStatusResponse response = new RestaurantStatusResponse();
		response.setMessageId(request.getMessageId());
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = ValidationUtils.getErrorMap(bindingResult);
			response.setErrors(errors);
			response.setMessage("Request processing failed, Enter the valide values");
			log.info("having constraint errors,stopped processing change restaurant status Request, messageId=" + request.getMessageId());
			return new ResponseEntity<RestaurantStatusResponse>(response, HttpStatus.BAD_REQUEST);
		}
		else {
			log.info("No constraint errors,started changing status");
			RestaurantStatusOperation operation = opsConfiguration.getRestaurantStatusOperation(request);
			response = operation.run();
			response.setMessageId(request.getMessageId());
			if(response.getErrors().isEmpty()) {
				response.setMessage("status changed successfully.");
				log.info("status changed successfully");
				return new ResponseEntity<RestaurantStatusResponse>(response, HttpStatus.OK);
			}
			else {
				response.setMessage("change restaurant status failed");
				log.info("change restaurant status failed");
				return new ResponseEntity<RestaurantStatusResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	@PostMapping(path = "status/food", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FoodStatusResponse> changeFoodStatus(@Valid @RequestBody FoodStatusRequest request, BindingResult bindingResult) {
		log.info("Started Processing change food status, messageId=" + request.getMessageId());
		FoodStatusResponse response = new FoodStatusResponse();
		response.setMessageId(request.getMessageId());
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = ValidationUtils.getErrorMap(bindingResult);
			response.setErrors(errors);
			response.setMessage("Request processing failed, Enter the valide values");
			log.info("having constraint errors,stopped processing change food status Request, messageId=" + request.getMessageId());
			return new ResponseEntity<FoodStatusResponse>(response, HttpStatus.BAD_REQUEST);
		}
		else {
			log.info("No constraint errors,started changing status");
			FoodStatusOperation operation = opsConfiguration.getFoodStatusOperation(request);
			response = operation.run();
			response.setMessageId(request.getMessageId());
			if(response.getErrors().isEmpty()) {
				response.setMessage("status changed successfully.");
				log.info("status changed successfully");
				return new ResponseEntity<FoodStatusResponse>(response, HttpStatus.OK);
			}
			else {
				response.setMessage("change food status failed");
				log.info("change food status failed");
				return new ResponseEntity<FoodStatusResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}
	}
}
