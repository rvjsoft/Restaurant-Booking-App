package com.rvj.app.dataaccess;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvj.app.foodorder.entity.Tables;
import com.rvj.app.foodorder.entity.enums.PartOfDay;

@Repository
public interface TableAvailRepository extends JpaRepository<Tables, Long>{
	
	public Tables findByRestaurantAndBookedOnAndPart(String restaurant, LocalDate bookedOn, PartOfDay part);
	
}
