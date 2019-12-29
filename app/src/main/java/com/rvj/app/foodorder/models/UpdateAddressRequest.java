package com.rvj.app.foodorder.models;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UpdateAddressRequest extends BaseRequest {
	
	@NotEmpty(message = "UserName should Not be null/empty")
	@Size(min = 5, max = 20, message = "UserName length should be from 5 to 20 characters")
	private String userName;
	
	@Valid
	@NotNull(message = "address should not be empty")
	private AddressModel address;
	
	@NotNull(message = "id of address should not be null/empty")
	private Long addressId;
	
}
