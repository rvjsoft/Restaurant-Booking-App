package com.rvj.app.foodorder.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
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
import org.hibernate.annotations.NaturalId;

import com.rvj.app.foodorder.entity.converters.FoodTypeConverter;
import com.rvj.app.foodorder.entity.converters.StatusConverter;
import com.rvj.app.foodorder.entity.enums.FoodType;
import com.rvj.app.foodorder.entity.enums.Status;

import lombok.Data;

@Data
@Entity
@Table(name = "restaurant")
@PrimaryKeyJoinColumn(name = "id")
public class Restaurant extends User {

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	@Convert(converter = FoodTypeConverter.class)
	private FoodType type;

	@Embedded
	private AddressType address;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;

	@Column(name = "status")
	@Convert(converter = StatusConverter.class)
	private Status status;
	
	@Column(name = "base_tables")
	private Integer tableCount;
	
	@Column(name = "image_id")
	private String imageId;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Food> foods = new ArrayList<Food>();

//	@OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
//	private RestaurantTable table;

	@OneToMany(mappedBy = "restaurant", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<TableBooking> bookings = new ArrayList<TableBooking>();
	
	@OneToMany(mappedBy = "restaurant", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Tables> tables = new ArrayList<Tables>();
	
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

	public void addBooking(TableBooking booking) {
		this.bookings.add(booking);
		booking.setRestaurant(this);
	}
	
	public void removeBooking(TableBooking booking) {
		this.bookings.remove(booking);
		booking.setRestaurant(null);
	}
	
	public void addTables(Tables table) {
		this.tables.add(table);
		table.setRestaurant(this);
	}
	
	public void removeTables(Tables table) {
		this.tables.remove(table);
		table.setRestaurant(null);
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
