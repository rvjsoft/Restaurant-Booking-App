package com.rvj.app.foodorder.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.rvj.app.foodorder.entity.Address;
import com.rvj.app.foodorder.entity.AddressType;

import lombok.Data;

@Data
public class AddressModel {
	
	private Long id;
	
	@NotEmpty(message = "Address1 should Not be null/empty")
	@Size(min = 5, max = 100, message = "Address1 length should not be more than 100 characters")
	private String address1;
	
	@Size(max = 100, message = "Address2 length should not be more than 100 characters")
	private String address2;
	
	@NotEmpty(message = "landmark should Not be null/empty")
	@Size(min = 3, max = 20, message = "landmark length should be from 3 to 20 characters")
	private String landmark;
	
	@NotEmpty(message = "city should Not be null/empty")
	@Size(min = 3, max = 20, message = "city length should be from 3 to 20 characters")
	private String city;
	
	@NotEmpty(message = "state should Not be null/empty")
	@Size(min = 3, max = 20, message = "state length should be from 3 to 20 characters")
	private String state;
	
	public boolean isSame(Address address) {
		AddressType addr = address.getAddress();
		if (address1.equals(addr.getAddress1()) && address2.equals(addr.getAddress2())
				&& landmark.equals(addr.getLandmark()) && city.equals(addr.getCity())
				&& state.equals(addr.getState())) {
			return true;
		}
		return false;
	}
}
