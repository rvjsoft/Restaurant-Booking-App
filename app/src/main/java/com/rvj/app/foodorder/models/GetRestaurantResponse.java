package com.rvj.app.foodorder.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GetRestaurantResponse extends BaseResponse {
	List<RestaurantModel> restaurants = new ArrayList<RestaurantModel>();
}
