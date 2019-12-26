package com.rvj.app.foodorder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rvj.app.foodorder.entity.enums.FoodCategory;
import com.rvj.app.foodorder.entity.enums.FoodType;
import com.rvj.app.foodorder.entity.enums.Status;

import lombok.Data;

@Data
@Entity
@Table(name = "food")
public class Food {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "type")
	private FoodType type;
	
	@Column(name = "status")
	private Status status;
	
	@Column(name = "category")
	private FoodCategory category;
	
	@ManyToOne
	@JoinColumn(name = "res_id")
	private Restaurant restaurant;
}
