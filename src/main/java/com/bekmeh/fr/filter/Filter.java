package com.bekmeh.fr.filter;

import com.bekmeh.fr.filter.node.FilterNode;
import com.bekmeh.fr.resource.User;

public class Filter {
	
	private FilterNode<User> rootNode;
	
	public Filter(final FilterNode<User> rootNode) {
		this.rootNode = rootNode;
	}
	
	public boolean matches(final User user) {
		return rootNode.evaluate(user);
	}
	
	@Override
	public String toString() {
		return this.rootNode.toString();
	}
	
}
