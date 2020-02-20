package com.rvj.app.foodorder.models;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.rvj.app.foodorder.entity.enums.PartOfDay;

import lombok.Data;

@Data
public class TableAvailResponse extends BaseResponse{

	private Map<LocalDate, Map<PartOfDay, TableAvailModel>> availability = new HashMap<LocalDate, Map<PartOfDay,TableAvailModel>>();
	private Integer baseCount;
}
