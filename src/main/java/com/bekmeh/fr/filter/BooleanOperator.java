package com.bekmeh.fr.filter;

public enum BooleanOperator {
	AND("and"), 
	OR("or");

	private String stringRepresentation;

	private BooleanOperator(final String stringRepresentation) {
		this.stringRepresentation = stringRepresentation;
	}

	@Override
	public String toString() {
		return stringRepresentation;
	}
}
