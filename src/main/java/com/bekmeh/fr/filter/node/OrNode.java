package com.bekmeh.fr.filter.node;

import com.bekmeh.fr.resource.User;

public class OrNode extends FilterNode<User> {

    public OrNode(final FilterNode<User> left, final FilterNode<User> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate(final User objectToEvaluate) {
        return this.right.evaluate(objectToEvaluate) || this.left.evaluate(objectToEvaluate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("( ");
        sb.append(this.left.toString());
        sb.append(" OR ");
        sb.append(this.right.toString());
        sb.append(" )");
        return sb.toString();
    }

}
