package com.bekmeh.fr.filter.node;

import java.math.BigInteger;

import com.bekmeh.fr.resource.User;

public class LessThanNode implements FilterNode<User> {
	
	private String key;
	private Object value;
	
	public LessThanNode(final String key, final Object value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public boolean evaluate(final User objectToEvaluate) {
		final BigInteger objectValue = new BigInteger(objectToEvaluate.get(this.key).toString());
		final BigInteger comparisonValue = new BigInteger(this.value.toString());
		if (objectToEvaluate.containsKey(this.key)) {
			return objectValue.compareTo(comparisonValue) == -1;
		}
		return false;
	}

}
