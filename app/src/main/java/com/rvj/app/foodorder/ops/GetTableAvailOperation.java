package com.rvj.app.foodorder.ops;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.enums.UserLevel;
import com.rvj.app.foodorder.models.TableAvailRequest;
import com.rvj.app.foodorder.models.TableAvailResponse;
import com.rvj.app.foodorder.services.CustomerService;
import com.rvj.app.foodorder.services.RestaurantService;
import com.rvj.app.foodorder.utils.AppConstants;

public class GetTableAvailOperation extends Operation<TableAvailRequest, TableAvailResponse>{

	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	HttpSession session;
	
	@Override
	protected boolean validate() {
//		UserLevel level = (UserLevel) session.getAttribute(AppConstants.USR_LEVEL);
		UserLevel level = request.getUserLevel();
		if (level.equals(UserLevel.CUSTOMER)) {
			if(Objects.isNull(request.getResId())) {
				this.getErrors().addError("resId", "resId should not be null");
			} else {
				Restaurant res = customerService.getRestaurant(request.getResId());
				if(Objects.isNull(res)) {
					this.getErrors().addError("resId", "restaurant doesn't exist");
				} else {
					request.setUserName(res.getUserName());
				}
			}
		} else {
//			request.setUserName((String) session.getAttribute(AppConstants.APP_USER));
		}
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
