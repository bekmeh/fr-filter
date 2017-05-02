package com.bekmeh.fr.filter;

public class FieldCondition {

	private String fieldName;
	private boolean negated;
	private Condition condition;
	private Object comparisonValue;

	public Object getComparisonValue() {
		return comparisonValue;
	}

	private BooleanOperator booleanOperator;
	
	public FieldCondition(final BooleanOperator booleanOperator, final String fieldName, final boolean negation, final Condition condition, final Object comparisonValue) {
		this.fieldName = fieldName;
		this.booleanOperator = booleanOperator;
		this.negated = negation;
		this.condition = condition;
		this.comparisonValue = comparisonValue;
	}
	
	public FieldCondition(final BooleanOperator booleanOperator, final String fieldName, final boolean negation, final Condition condition) {
		this.fieldName = fieldName;
		this.negated = negation;
		this.booleanOperator = booleanOperator;
		this.condition = condition;
	}

	public String getFieldName() {
		return fieldName;
	}

	public boolean isNegated() {
		return negated;
	}

	public Condition getCondition() {
		return condition;
	}

	public BooleanOperator getBooleanOperator() {
		return booleanOperator;
	}

}
