package com.rvj.app.foodorder.entity.enums;

public enum UserLevel {
	CUSTOMER('C'),
	RESTAURANT('R');
	
	private final char code;

	UserLevel(char code) {
		this.code = code;
	}

	public char getCode() {
		return this.code;
	}

	public static UserLevel fromCode(char code) {
		if (code == 'C' || code == 'c') {
			return CUSTOMER;
		} else if (code == 'R' || code == 'r') {
			return RESTAURANT;
		} else throw new UnsupportedOperationException("the code " + code + " is not valid");
	}
}
