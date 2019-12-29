package com.rvj.app.foodorder.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rvj.app.foodorder.entity.converters.FoodTypeConverter;
import com.rvj.app.foodorder.entity.converters.StatusConverter;
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
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "type")
	@Convert(converter = FoodTypeConverter.class)
	private FoodType type;
	
	@Column(name = "status")
	@Convert(converter = StatusConverter.class)
	private Status status;
	
	@Column(name = "category")
	@Enumerated(EnumType.STRING)
	private FoodCategory category;
	
	@ManyToOne
	@JoinColumn(name = "res_id")
	private Restaurant restaurant;
}
