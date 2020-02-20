package com.rvj.app.foodorder.models;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TableAvailModel {

	private LocalDate bookedOn;
	
	private Integer total;
	
	private Integer bookedTables;
	
}
