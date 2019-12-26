package com.rvj.app.foodorder.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "res_table")
public class RestaurantTable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "total")
	private Integer total;
	
	@Column(name = "available")
	private Integer available;
	
	@OneToOne
	@JoinColumn(name = "id")
	private Restaurant restaurant;
	
	@OneToMany(mappedBy = "resTable", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<TableBooking> bookings = new ArrayList<TableBooking>();
	
	public void addBooking(TableBooking booking) {
		this.bookings.add(booking);
		booking.setResTable(this);
	}
	
	public void removeBooking(TableBooking booking) {
		this.bookings.remove(booking);
		booking.setResTable(null);
	}
	
}
