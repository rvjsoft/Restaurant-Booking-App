package com.rvj.app.foodorder.models;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AddAddressRequest extends BaseRequest {
	
	@NotEmpty(message = "UserName should Not be null/empty")
	@Size(min = 5, max = 20, message = "UserName length should be from 5 to 20 characters")
	private String userName;
	
	@Valid
	@NotNull(message = "address should not be empty")
	@Size(min = 1, max = 3, message = "address can be max of 3")
	private List<AddressModel> addresses;
	
}
