package com.rvj.app.foodorder.ops;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.rvj.app.foodorder.entity.Customer;
import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.Tables;
import com.rvj.app.foodorder.models.BookTableRequest;
import com.rvj.app.foodorder.models.BookTableResponse;
import com.rvj.app.foodorder.services.CustomerService;

public class BookTableOperation extends Operation<BookTableRequest, BookTableResponse> {

	@Autowired
	CustomerService customerService;

	@Override
	protected boolean validate() {
		Customer customer = customerService.getCustomer(request.getUserName());
		if (customer == null) {
			this.getErrors().addError("customer", "customer does not exist");
		}
		if (!customerService.isRestaurantExist(request.getResId())) {
			this.getErrors().addError("resId", "Restaurant does not exist");
		} else {
			if (request.getDate().isAfter(LocalDate.now().plusDays(7))) {
				this.getErrors().addError("bookingdate", "you can change only for next 7 days");
			} else if (request.getDate().isBefore(LocalDate.now())) {
				this.getErrors().addError("bookingdate", "you can't change for past days");
			}
			Restaurant restaurant = customerService.getRestaurant(request.getResId());
			if (Objects.nonNull(restaurant.getTables())) {
				List<Tables> tables = restaurant.getTables();
				Optional<Tables> tableData = tables.stream()
						.filter(data -> data.getBookedOn().equals(request.getDate()))
						.filter(data -> data.getPart().equals(request.getPart())).findFirst();
				if (tableData.isPresent()
						&& ((tableData.get().getBookedTables() + request.getCount()) > tableData.get().getTotal())) {
					this.getErrors().addError("count", request.getCount() + "seats are not available");
				}
			}
		}
		return this.getErrors().hasNoError();
	}

	@Override
	protected void process() {
		boolean status = false;
		status = customerService.bookTable(request);
		if(!status) {
			this.getErrors().addError("operationError", "error booking table");
		}
	}

}
