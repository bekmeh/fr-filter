package com.bekmeh.fr.filter.node;

import com.bekmeh.fr.resource.User;

public class EqualToNode implements FilterNode<User> {
	
	private String key;
	private Object value;
	
	public EqualToNode(final String key, final Object value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public boolean evaluate(final User objectToEvaluate) {
		if (objectToEvaluate.containsKey(this.key)) {
			return objectToEvaluate.get(this.key).toString().matches(this.value.toString());
		}
		return false;
	}

}
