package com.bekmeh.fr.filter.node;

import com.bekmeh.fr.resource.User;

public class OrNode implements FilterNode<User> {
	
	private FilterNode<User> right;
	private FilterNode<User> left;

	@Override
	public boolean evaluate(User objectToEvaluate) {
		return this.right.evaluate(objectToEvaluate) || this.left.evaluate(objectToEvaluate);
	}

}
