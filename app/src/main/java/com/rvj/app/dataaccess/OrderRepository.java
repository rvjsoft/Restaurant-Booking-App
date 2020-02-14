package com.rvj.app.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvj.app.foodorder.entity.Order;
import com.rvj.app.foodorder.entity.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	public Integer countByStatusIn(List<OrderStatus> status);
}
