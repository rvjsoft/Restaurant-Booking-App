package com.rvj.app.foodorder.ops;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.rvj.app.foodorder.models.TableAvailRequest;
import com.rvj.app.foodorder.models.TableAvailResponse;
import com.rvj.app.foodorder.services.RestaurantService;
import com.rvj.app.foodorder.utils.AppConstants;

public class GetTableAvailOperation extends Operation<TableAvailRequest, TableAvailResponse>{

	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
	HttpSession session;
	
	@Override
	protected boolean validate() {
		request.setUserName((String) session.getAttribute(AppConstants.APP_USER));
		return true;
	}

	@Override
	protected void process() {
		boolean status = false;
		status = restaurantService.getTableAvailability(request, response);
		if(!status) {
			this.getErrors().addError("response", "error while getting table availability");
		}
	}

}
