package com.rvj.app.foodorder.models;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AddAddressRequest extends BaseRequest {
	
	@Valid
	@NotNull(message = "address should not be empty")
	@Size(min = 1, max = 3, message = "address can be max of 3")
	private List<AddressModel> addresses;
	
}
