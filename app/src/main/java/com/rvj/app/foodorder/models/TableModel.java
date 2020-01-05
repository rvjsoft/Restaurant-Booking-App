package com.rvj.app.foodorder.models;

import java.time.LocalDate;

import com.rvj.app.foodorder.entity.enums.PartOfDay;

import lombok.Data;

@Data
public class TableModel {
	
	private Long bookingId;

	private Integer count;

	private PartOfDay partOfDay;

	private LocalDate bookingDate;

	private String restaurant;

	private String customer;
}
