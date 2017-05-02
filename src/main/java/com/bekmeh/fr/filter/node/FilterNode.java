package com.bekmeh.fr.filter.node;

/**
 * The abstract FilterNode, which contains only a left node, right node and
 * evaluate method.
 * 
 * @author meemp
 *
 * @param <T>
 *            The class of object the Filter will act upon.
 */
public abstract class FilterNode<T> {

    public FilterNode<T> left;
    public FilterNode<T> right;

    public abstract boolean evaluate(final T objectToEvaluate);

    public FilterNode<T> getLeft() {
        return left;
    }

    public void setLeft(FilterNode<T> left) {
        this.left = left;
    }

    public FilterNode<T> getRight() {
        return right;
    }

    public void setRight(FilterNode<T> right) {
        this.right = right;
    }
}
