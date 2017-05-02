package com.bekmeh.fr.filter.node;

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
