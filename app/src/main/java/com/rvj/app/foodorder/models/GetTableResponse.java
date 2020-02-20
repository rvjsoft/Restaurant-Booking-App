package com.rvj.app.foodorder.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GetTableResponse extends BaseResponse {
	List<TableModel> tableBookings = new ArrayList<TableModel>();
}
