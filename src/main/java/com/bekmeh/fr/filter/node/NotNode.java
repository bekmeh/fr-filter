package com.bekmeh.fr.filter.node;

import com.bekmeh.fr.resource.User;

public class NotNode implements FilterNode<User> {
	
	private FilterNode<User> child;

	@Override
	public boolean evaluate(User objectToEvaluate) {
		return !this.child.evaluate(objectToEvaluate);
	}

}
