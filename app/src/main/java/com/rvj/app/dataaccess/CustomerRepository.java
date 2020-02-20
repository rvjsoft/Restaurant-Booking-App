package com.rvj.app.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvj.app.foodorder.entity.Customer;
import com.rvj.app.foodorder.entity.Restaurant;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	public List<Customer> findByEmailOrPhone(String email, String phone);
	
	public Customer findByUserName(String userName);
	
}
