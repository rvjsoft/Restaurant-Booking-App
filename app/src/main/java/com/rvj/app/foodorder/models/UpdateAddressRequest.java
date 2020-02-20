package com.rvj.app.foodorder.models;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateAddressRequest extends BaseRequest {
	
	@Valid
	@NotNull(message = "address should not be empty")
	private AddressModel address;
	
	@NotNull(message = "id of address should not be null/empty")
	private Long addressId;
	
}
