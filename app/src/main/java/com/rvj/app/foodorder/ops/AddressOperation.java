package com.rvj.app.foodorder.ops;

import org.springframework.beans.factory.annotation.Autowired;

import com.rvj.app.foodorder.entity.Customer;
import com.rvj.app.foodorder.models.AddAddressRequest;
import com.rvj.app.foodorder.models.AddAddressResponse;
import com.rvj.app.foodorder.services.CustomerService;

public class AddressOperation extends Operation<AddAddressRequest, AddAddressResponse> {

	@Autowired
	CustomerService customerService;
	
	@Override
	protected boolean validate() {
		
		Customer customer = customerService.getCustomer(request.getUserName());
		if(customer == null) {
			this.getErrors().addError("customer", "customer does not exist");
		} else {
			int size = customer.getAddresses().size() + this.request.getAddresses().size();
			if(size > 3) {
				this.getErrors().addError("addresses", "max address per use is 3 only");
			}
		}
		return this.getErrors().hasNoError();
	}

	@Override
	protected void process() {
		boolean status = false;
		status = customerService.addAddresses(request, response);
		if(!status) {
			this.getErrors().addError("operationError", "error adding addresses");
		}
	}

}
