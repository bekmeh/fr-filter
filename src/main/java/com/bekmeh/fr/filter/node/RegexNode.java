package com.bekmeh.fr.filter.node;

import com.bekmeh.fr.resource.User;

public class RegexNode implements FilterNode<User> {
	
	private String key;
	private Object value;
	
	public RegexNode(final String key, final Object value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public boolean evaluate(final User objectToEvaluate) {
		if (objectToEvaluate.containsKey(this.key)) {
			return objectToEvaluate.get(this.key).toString().equals(this.value.toString());
		}
		return false;
	}

}
