package com.rvj.app.foodorder.ops;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.Tables;
import com.rvj.app.foodorder.entity.enums.PartOfDay;
import com.rvj.app.foodorder.models.RestaurantTableRequest;
import com.rvj.app.foodorder.models.RestaurantTableResponse;
import com.rvj.app.foodorder.services.RestaurantService;
import com.rvj.app.foodorder.utils.AppConstants;

import lombok.Data;

public class RestaurantTableOperation extends Operation<RestaurantTableRequest, RestaurantTableResponse> {

	@Autowired
	RestaurantService restaurantService;

	@Override
	protected boolean validate() {

		Restaurant restaurant = restaurantService.getRestaurant(request.getUserName());
		if ((request.getBaseCount() != null) && (request.getDate() != null || request.getTableCount() != null)) {
			this.getErrors().addError("conflictInRequest",
					"can't update both Restaurant's base table count and table count.");
			return false;
		} else if (request.getBaseCount() != null) {
			request.setAction(AppConstants.RES_TABLE_ACTION);
		} else if (request.getDate() != null && request.getTableCount() != null) {
			request.setAction(AppConstants.DAY_TABLE_ACTION);
		}
		if (!Objects.nonNull(restaurant)) {
			this.getErrors().addError("username", "the restaurant does not exist");
		} else {
			if (request.getAction().equals(AppConstants.DAY_TABLE_ACTION)) {
				if (request.getDate().isAfter(LocalDate.now().plusDays(7))) {
					this.getErrors().addError("bookingdate", "you can change only for next 7 days");
				} else if (request.getDate().isBefore(LocalDate.now())) {
					this.getErrors().addError("bookingdate", "you can't change for past days");
				} else if (Objects.nonNull(restaurant.getTables())) {
					List<Tables> tables = restaurant.getTables();
					List<Tables> table = tables.stream()
							.filter(tableData -> tableData.getBookedOn().equals(request.getDate()))
							.collect(Collectors.toList());
					if (!CollectionUtils.isEmpty(table)) {
						if (request.getPart() == null && getMax(table) > request.getTableCount()) {
							this.getErrors().addError("tablecount",
									"in the mentioned date the tables are already booked above "
											+ request.getTableCount());
						} else if (getMaxOfPart(table, request.getPart()) > request.getTableCount()) {
							this.getErrors().addError("tablecount",
									"in the mentioned date and part of the tables are already booked above "
											+ request.getTableCount());
						}
					}
				}
			}
		}

		return this.getErrors().hasNoError();
	}

	private Integer getMaxOfPart(List<Tables> table, PartOfDay part) {
		Optional<Tables> res = table.stream().filter(data -> data.getPart().equals(part)).findFirst();
		if (res.isPresent())
			return res.get().getBookedTables();
		return null;
	}

	private Integer getMax(List<Tables> table) {
		Optional<Integer> res = table.stream().map(data -> data.getBookedTables()).max(Integer::compare);
		return res.get();
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
