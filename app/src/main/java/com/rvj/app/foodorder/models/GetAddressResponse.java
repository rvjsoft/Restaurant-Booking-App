package com.rvj.app.foodorder.models;

import java.util.List;

import lombok.Data;

@Data
public class GetAddressResponse extends BaseResponse{
	private List<AddressModel> addresses;
}
