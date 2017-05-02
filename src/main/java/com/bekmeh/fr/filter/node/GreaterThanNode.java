package com.bekmeh.fr.filter.node;

import java.math.BigDecimal;

import com.bekmeh.fr.resource.User;

public class GreaterThanNode implements FilterNode<User> {
	
	private String key;
	private Object value;
	
	public GreaterThanNode(final String key, final Object value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public boolean evaluate(final User objectToEvaluate) {
		final BigDecimal objectValue = new BigDecimal(objectToEvaluate.get(this.key).toString());
		final BigDecimal comparisonValue = new BigDecimal(this.value.toString());
		if (objectToEvaluate.containsKey(this.key)) {
			return objectValue.compareTo(comparisonValue) == 1;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("( \"");
		sb.append(this.key);
		sb.append("\" GREATER THAN ");
		sb.append(this.value);
		sb.append(" )");
		return sb.toString();
	}

}
