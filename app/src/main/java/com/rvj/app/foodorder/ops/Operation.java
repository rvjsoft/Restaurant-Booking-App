package com.rvj.app.foodorder.ops;

import com.rvj.app.foodorder.models.BaseRequest;
import com.rvj.app.foodorder.models.BaseResponse;
import com.rvj.app.foodorder.utils.AppError;

import lombok.Data;

@Data
public abstract class Operation<REQ extends BaseRequest, RES extends BaseResponse> {
	
	protected REQ request;
	
	protected RES response;
	
	protected AppError errors = new AppError();
	
	protected void preprocess() {
		
	}
	
	protected void postprocess() {
//		if(!this.getErrors().hasNoError()) {
//			response
//			.getErrors()
//			.putAll(this.getErrors().getErrors());
//		}
	}
	
	public RES run() {
		preprocess();
		if(validate()) {
			process();
		}
		if(errors.hasNoError()) {
			return response;
		} else {
			response
			.getErrors()
			.putAll(errors.getErrors());
			return response;
		}
	}
	
	protected abstract boolean validate();
	
	protected abstract void process();

	
}
