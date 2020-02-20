package com.rvj.app.foodorder.entity.enums;

public enum FoodType {
	VEG('V'),
	NON_VEG('N');
	
	private final char code;
	
	FoodType(char code) {
		this.code = code;
	}
	
	public char getCode() {
		return this.code;
	}
	
	public static FoodType fromCode(char code) {
		if (code == 'V' || code == 'v') {
			return VEG;
		} else if (code == 'N' || code == 'n') {
			return NON_VEG;
		} else throw new UnsupportedOperationException("the code " + code + " is not valid");
	}
}
