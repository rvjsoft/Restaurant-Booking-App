package com.rvj.app.dataaccess;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvj.app.foodorder.entity.Restaurant;
import com.rvj.app.foodorder.entity.Tables;

@Repository
public interface TableAvailRepository extends JpaRepository<Tables, Long>{
	
	public List<Tables> findByRestaurantAndBookedOnGreaterThan(Restaurant restaurant, LocalDate date);
}
