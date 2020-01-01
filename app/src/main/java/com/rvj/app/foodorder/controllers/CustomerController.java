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
import com.rvj.app.foodorder.models.AddAddressRequest;
import com.rvj.app.foodorder.models.AddAddressResponse;
import com.rvj.app.foodorder.models.DeleteAddressRequest;
import com.rvj.app.foodorder.models.DeleteAddressResponse;
import com.rvj.app.foodorder.models.UpdateAddressRequest;
import com.rvj.app.foodorder.models.UpdateAddressResponse;
import com.rvj.app.foodorder.ops.AddressOperation;
import com.rvj.app.foodorder.ops.DeleteAddressOperation;
import com.rvj.app.foodorder.ops.UpdateAddressOperaion;
import com.rvj.app.foodorder.utils.ValidationUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private AppOperationConfiguration opsConfiguration;
	
	@PostMapping(path = "add/address", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AddAddressResponse> addAddress(@Valid @RequestBody AddAddressRequest request, BindingResult bindingResult) {
		log.info("Started Processing Add address request, messageId=" + request.getMessageId());
		AddAddressResponse response = new AddAddressResponse();
		response.setMessageId(request.getMessageId());
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = ValidationUtils.getErrorMap(bindingResult);
			response.setErrors(errors);
			response.setMessage("Request processing failed, Enter the valide values");
			log.info("having constraint errors,stopped processing Add address Request, messageId=" + request.getMessageId());
			return new ResponseEntity<AddAddressResponse>(response, HttpStatus.BAD_REQUEST);
		}
		else {
			log.info("No constraint errors,started adding address");
			AddressOperation operation = opsConfiguration.getCustomerOperation(request);
			response = operation.run();
			response.setMessageId(request.getMessageId());
			if(response.getErrors().isEmpty()) {
				response.setMessage("Address Added successfully.");
				log.info("address added successfully");
				return new ResponseEntity<AddAddressResponse>(response, HttpStatus.OK);
			}
			else {
				response.setMessage("address addition failed");
				log.info("adding of address failed");
				return new ResponseEntity<AddAddressResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	@PostMapping(path = "update/address", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UpdateAddressResponse> updateAddress(@Valid @RequestBody UpdateAddressRequest request, BindingResult bindingResult) {
		log.info("Started Processing update address request, messageId=" + request.getMessageId());
		UpdateAddressResponse response = new UpdateAddressResponse();
		response.setMessageId(request.getMessageId());
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = ValidationUtils.getErrorMap(bindingResult);
			response.setErrors(errors);
			response.setMessage("Request processing failed, Enter the valide values");
			log.info("having constraint errors,stopped processing update address Request, messageId=" + request.getMessageId());
			return new ResponseEntity<UpdateAddressResponse>(response, HttpStatus.BAD_REQUEST);
		}
		else {
			log.info("No constraint errors,started updating address");
			UpdateAddressOperaion operation = opsConfiguration.getUpdateAddressOperation(request);
			response = operation.run();
			response.setMessageId(request.getMessageId());
			if(response.getErrors().isEmpty()) {
				response.setMessage("Address updated successfully.");
				log.info("address updated successfully");
				return new ResponseEntity<UpdateAddressResponse>(response, HttpStatus.OK);
			}
			else {
				response.setMessage("address update failed");
				log.info("updating of address failed");
				return new ResponseEntity<UpdateAddressResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	@PostMapping(path = "delete/address", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteAddressResponse> deleteAddress(@Valid @RequestBody DeleteAddressRequest request, BindingResult bindingResult) {
		log.info("Started Processing delete address request, messageId=" + request.getMessageId());
		DeleteAddressResponse response = new DeleteAddressResponse();
		response.setMessageId(request.getMessageId());
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = ValidationUtils.getErrorMap(bindingResult);
			response.setErrors(errors);
			response.setMessage("Request processing failed, Enter the valide values");
			log.info("having constraint errors,stopped processing delete address Request, messageId=" + request.getMessageId());
			return new ResponseEntity<DeleteAddressResponse>(response, HttpStatus.BAD_REQUEST);
		}
		else {
			log.info("No constraint errors,started DeleteAddressResponse address");
			DeleteAddressOperation operation = opsConfiguration.getDeleteAddressOperation(request);
			response = operation.run();
			response.setMessageId(request.getMessageId());
			if(response.getErrors().isEmpty()) {
				response.setMessage("Address deleted successfully.");
				log.info("address deleted successfully");
				return new ResponseEntity<DeleteAddressResponse>(response, HttpStatus.OK);
			}
			else {
				response.setMessage("address delete failed");
				log.info("deletion of address failed");
				return new ResponseEntity<DeleteAddressResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}
	}
}