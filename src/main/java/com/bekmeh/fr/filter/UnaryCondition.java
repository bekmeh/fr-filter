package com.bekmeh.fr.filter;

public enum UnaryCondition implements Condition {
	PRESENT("present");
	
	private String stringRepresentation;
	
	private UnaryCondition(final String stringRepresentation) {
		this.stringRepresentation = stringRepresentation;
	}
	
	@Override
	public String toString() {
		return this.stringRepresentation;
	}
}
