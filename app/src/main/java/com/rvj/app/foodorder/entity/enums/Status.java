package com.rvj.app.foodorder.entity.enums;

public enum Status {
	AVAILABLE('A'),
	UNAVAILABLE('U');
	
	private final char code;

	Status(char code) {
		this.code = code;
	}

	public char getCode() {
		return this.code;
	}

	public static Status fromCode(char code) {
		if (code == 'A' || code == 'a') {
			return AVAILABLE;
		} else if (code == 'U' || code == 'u') {
			return UNAVAILABLE;
		} else throw new UnsupportedOperationException("the code " + code + " is not valid");
	}
}
