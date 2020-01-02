package com.rvj.app.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvj.app.foodorder.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
}
