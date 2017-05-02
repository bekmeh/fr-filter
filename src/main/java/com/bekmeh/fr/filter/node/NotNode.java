package com.bekmeh.fr.filter.node;

import com.bekmeh.fr.resource.User;

public class NotNode implements FilterNode<User> {
	
	private FilterNode<User> child;
	
	public NotNode(final FilterNode<User> child) {
		this.child = child;
	}

	@Override
	public boolean evaluate(final User objectToEvaluate) {
		return !this.child.evaluate(objectToEvaluate);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("( ");
		sb.append(" NOT ");
		sb.append(this.child.toString());
		sb.append(" )");
		return sb.toString();
	}

}
