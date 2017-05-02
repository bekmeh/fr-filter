package com.bekmeh.fr.filter;

public enum BinaryCondition implements Condition {
	GREATER_THAN("greater than"), 
	LESS_THAN("less than"), 
	EQUAL_TO("equal to"), 
	REGEX_MATCH("matching");
	
	private String stringRepresentation;
	
	private BinaryCondition(final String stringRepresentation) {
		this.stringRepresentation = stringRepresentation;
	}

	@Override
	public String toString() {
		return stringRepresentation;
	}

}
