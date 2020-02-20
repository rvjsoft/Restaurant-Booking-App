package com.rvj.app.foodorder.ops;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.rvj.app.foodorder.models.BaseRequest;
import com.rvj.app.foodorder.models.GetAddressResponse;
import com.rvj.app.foodorder.services.CustomerService;
import com.rvj.app.foodorder.utils.AppConstants;

public class GetAddressOperation extends Operation<BaseRequest, GetAddressResponse>{

	@Autowired
	HttpSession session;
	
	@Autowired
	CustomerService customerService;
	
	@Override
	protected boolean validate() {
		request.setUserName((String) session.getAttribute(AppConstants.APP_USER));
		return true;
	}

	@Override
	protected void process() {
		boolean status = false;
		status = customerService.getAddresses(request, response);
		if(!status) {
			this.getErrors().addError("operationError", "error deleting addresses");
		}
	}

}
