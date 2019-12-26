package com.rvj.app.foodorder.entity.enums;

public enum PartOfDay {
	BREAKFAST('B'),
	LUNCH('L'),
	DINNER('D');
	
	private final char code;

	PartOfDay(char code) {
		this.code = code;
	}

	public char getCode() {
		return this.code;
	}

	public static PartOfDay fromCode(char code) {
		if (code == 'B' || code == 'b') {
			return BREAKFAST;
		} else if (code == 'L' || code == 'l') {
			return LUNCH;
		} else if (code == 'D' || code == 'd') {
			return DINNER;
		} else throw new UnsupportedOperationException("the code " + code + " is not valid");
	}
}
