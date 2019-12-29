package com.rvj.app.foodorder.ops;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rvj.app.foodorder.entity.Address;
import com.rvj.app.foodorder.entity.Customer;
import com.rvj.app.foodorder.models.UpdateAddressRequest;
import com.rvj.app.foodorder.models.UpdateAddressResponse;
import com.rvj.app.foodorder.services.CustomerService;

@Component
public class UpdateAddressOperaion extends Operation<UpdateAddressRequest, UpdateAddressResponse> {

	@Autowired
	CustomerService customerService;
	
	@Override
	protected boolean validate() {
		
		Customer customer = customerService.getCustomer(request.getUserName());
		if(customer == null) {
			this.getErrors().addError("customer", "customer does not exist");
		} else {
			List<Address> addresses = customer.getAddresses().stream().filter(address -> address.getId().equals(request.getAddressId())).collect(Collectors.toList());
			if(CollectionUtils.isEmpty(addresses)) {
				this.getErrors().addError("addressId", "address does not exist");
			}
		}
		return this.getErrors().hasNoError();
	}

	@Override
	protected void process() {
		boolean status = false;
		status = customerService.updateAddresses(request);
		if(!status) {
			this.getErrors().addError("operationError", "error updating addresses");
		}
	}

}
