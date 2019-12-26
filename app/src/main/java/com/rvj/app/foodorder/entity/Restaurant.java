package com.rvj.app.foodorder.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.rvj.app.foodorder.entity.enums.FoodType;
import com.rvj.app.foodorder.entity.enums.Status;

import lombok.Data;

@Data
@Entity
@Table(name = "restaurant")
@PrimaryKeyJoinColumn(name = "id")
public class Restaurant extends User {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private FoodType type;

	@Embedded
	private AddressType address;

	@Column(name = "status")
	private Status status;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Food> foods = new ArrayList<Food>();

	@OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
	private RestaurantTable table;

	@OneToMany(mappedBy = "restaurant", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Order> orders = new ArrayList<Order>();
	
	public void addFood(Food food) {
		this.foods.add(food);
		food.setRestaurant(this);
	}

	public void removeFood(Food food) {
		this.foods.remove(food);
		food.setRestaurant(null);
	}

	public void addTable(RestaurantTable table) {
		this.table = table;
		table.setRestaurant(this);
	}

	public void removeTable() {
		if (this.table != null) {
			table.setRestaurant(null);
			this.table = null;
		}
	}
	
	public void addOrder(Order order) {
		this.orders.add(order);
		order.setRestaurant(this);
	}

	public void removeOrder(Order order) {
		this.orders.remove(order);
		order.setRestaurant(null);
	}

}
