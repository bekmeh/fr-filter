package com.bekmeh.fr.filter.node;

import java.math.BigInteger;

import com.bekmeh.fr.resource.User;

public class LessThanNode implements FilterNode<User> {
	
	private String key;
	private Object value;

	@Override
	public boolean evaluate(User objectToEvaluate) {
		final BigInteger objectValue = new BigInteger(objectToEvaluate.get(this.key).toString());
		final BigInteger comparisonValue = new BigInteger(this.value.toString());
		if (objectToEvaluate.containsKey(this.key)) {
			return objectValue.compareTo(comparisonValue) == -1;
		}
		return false;
	}

}
