package com.bekmeh.fr.filter.node;

public interface FilterNode<T> {

	public boolean evaluate(final T objectToEvaluate);

}
