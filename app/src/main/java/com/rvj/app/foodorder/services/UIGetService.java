package com.rvj.app.foodorder.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.rvj.app.dataaccess.OrderRepository;
import com.rvj.app.foodorder.entity.Food;
import com.rvj.app.foodorder.entity.Order;
import com.rvj.app.foodorder.entity.OrderItem;
import com.rvj.app.foodorder.models.FoodModel;
import com.rvj.app.foodorder.models.GetOrderResponse;
import com.rvj.app.foodorder.models.OrderItemModel;
import com.rvj.app.foodorder.models.OrderModel;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UIGetService {

	@Autowired
	OrderRepository orderRepository;

	public boolean getOrders(Example<Order> orderExample, GetOrderResponse response) {
		List<Order> orders;
		try {
			orders = orderRepository.findAll(orderExample);
			response.setOrders(getOrderModel(orders));
		} catch (Exception e) {
			log.info("caught exception while processing request, Exception:" + e.getMessage());
			return false;
		}
		return true;
	}

	private List<OrderModel> getOrderModel(List<Order> orders) {
		List<OrderModel> orderModelList = new ArrayList<OrderModel>();
		for (Order order : orders) {
			OrderModel orderModel = new OrderModel();
			BeanUtils.copyProperties(order, orderModel);
			orderModel.setCustomerName(order.getCustomer().getFullName());
			orderModel.setRestaurantName(order.getRestaurant().getName());
			List<OrderItemModel> itemsModel = new ArrayList<OrderItemModel>();
			for (OrderItem item : order.getItems()) {
				OrderItemModel itemModel = new OrderItemModel();
				itemModel.setId(item.getId());
				itemModel.setFood(item.getFood().getName());
				itemModel.setQuantity(item.getQuantity());
				itemsModel.add(itemModel);
			}
			orderModel.setFoodItems(itemsModel);
			orderModelList.add(orderModel);
		}
		return orderModelList;
	}

}
