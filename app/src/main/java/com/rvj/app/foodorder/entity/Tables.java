package com.rvj.app.foodorder.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "table_availability")
public class Tables {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "booking_date")
	private LocalDate bookedOn;
	
	@Column(name = "total")
	private Integer total;
	
	@Column(name = "booked")
	private Integer bookedTables;
	
	@ManyToOne
	@JoinColumn(name = "res_id")
	private Restaurant restaurant;
	
}
