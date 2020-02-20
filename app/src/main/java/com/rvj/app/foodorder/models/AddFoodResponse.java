package com.rvj.app.foodorder.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AddFoodResponse extends BaseResponse{
	List<Long> ids = new ArrayList<Long>();
}
