package com.rvj.app.foodorder.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rvj.app.foodorder.entity.converters.PartOfDayConverter;
import com.rvj.app.foodorder.entity.enums.PartOfDay;

import lombok.Data;

@Data
@Entity
@Table(name = "table_bookings")
public class TableBooking {
	@Id
	@Column(name = "booking_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookingId;
	
	@Column(name = "count")
	private Integer count;
	
	@Column(name = "time")
	@Convert(converter = PartOfDayConverter.class)
	private PartOfDay partOfDay;
	
	@Column(name = "booking_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookingDate;
	
	@ManyToOne
	@JoinColumn(name = "tables_id")
	private RestaurantTable resTable;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Customer customer;
	
}
