package com.bekmeh.fr.filter.node;

import com.bekmeh.fr.resource.User;

public class OrNode implements FilterNode<User> {
	
	private FilterNode<User> right;
	private FilterNode<User> left;
	
	public OrNode(final FilterNode<User> left, final FilterNode<User> right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean evaluate(final User objectToEvaluate) {
		return this.right.evaluate(objectToEvaluate) || this.left.evaluate(objectToEvaluate);
	}

}
