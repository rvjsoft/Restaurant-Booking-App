package com.rvj.app.dataaccess;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rvj.app.foodorder.entity.Food;
import com.rvj.app.foodorder.entity.enums.Status;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>{

	@Query("select f.id from Food f where f.restaurant.id = ?1 and f.id in ?2 and f.status = ?3")
	public List<Long> getFoods(Long resId, Set<Long> foodID, Status status);
	
}
