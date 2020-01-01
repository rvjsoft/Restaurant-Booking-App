package com.rvj.app.foodorder.ops;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.Tables;
import com.rvj.app.foodorder.models.RestaurantTableRequest;
import com.rvj.app.foodorder.models.RestaurantTableResponse;
import com.rvj.app.foodorder.services.RestaurantService;
import com.rvj.app.foodorder.utils.AppConstants;

public class RestaurantTableOperation extends Operation<RestaurantTableRequest, RestaurantTableResponse> {

	@Autowired
	RestaurantService restaurantService;

	@Override
	protected boolean validate() {
		
		Restaurant restaurant = restaurantService.getRestaurant(request.getUserName());
		if ((request.getBaseCount() != null) && (request.getDate() != null || request.getTableCount() != null)) {
			this.getErrors().addError("conflictInRequest",
					"can't update both Restaurant's base table count and table count.");
		} else if(request.getBaseCount() != null) {
			request.setAction(AppConstants.RES_TABLE_ACTION);
		} else if(request.getDate() != null && request.getTableCount() != null) {
			request.setAction(AppConstants.DAY_TABLE_ACTION);
		}
		if (!Objects.nonNull(restaurant)) {
			this.getErrors().addError("username", "the restaurant does not exist");
		} else {
			if (request.getAction().equals(AppConstants.DAY_TABLE_ACTION)) {
				if (request.getDate().isAfter(LocalDate.now().plusDays(7))) {
					this.getErrors().addError("bookingdate", "you can change only for next 7 days");
				} else if(request.getDate().isBefore(LocalDate.now())) {
					this.getErrors().addError("bookingdate", "you can't change for past days");
				} else if (Objects.nonNull(restaurant.getTables())) {
					List<Tables> tables = restaurant.getTables();
					Optional<Tables> table = tables.stream()
							.filter(tableData -> tableData.getBookedOn().equals(request.getDate())).findFirst();
					if (table.isPresent() && table.get().getBookedTables() > request.getTableCount()) {
						this.getErrors().addError("tablecount",
								"in the mentioned date the tables are already booked above " + request.getTableCount());
					}
				}
			}
		}

		return this.getErrors().hasNoError();
	}

	@Override
	protected void process() {
		boolean status = false;
		status = restaurantService.changeTablesCount(request);
		if (!status) {
			this.getErrors().addError("operationError", "error changing restaurant table count");
		}
	}

}
