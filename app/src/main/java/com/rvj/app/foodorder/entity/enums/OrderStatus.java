package com.rvj.app.foodorder.entity.enums;

public enum OrderStatus {
	ACKNOWLEDGED('A'),
	PREPARING('P'),
	DELIVERED('D');
	
	private final char code;

	OrderStatus(char code) {
		this.code = code;
	}

	public char getCode() {
		return this.code;
	}

	public static OrderStatus fromCode(char code) {
		if (code == 'A' || code == 'a') {
			return ACKNOWLEDGED;
		} else if (code == 'P' || code == 'p') {
			return PREPARING;
		} else if (code == 'D' || code == 'd') {
			return DELIVERED;
		} else throw new UnsupportedOperationException("the code " + code + " is not valid");
	}
}
