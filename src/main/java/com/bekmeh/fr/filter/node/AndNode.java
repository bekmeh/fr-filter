package com.bekmeh.fr.filter.node;

import com.bekmeh.fr.resource.Resource;

/**
 * {@link FilterNode} which, when evaluated, determines the result of the left
 * branch && result of the right branch.
 * 
 * @author bekmeh
 *
 */
public class AndNode extends FilterNode<Resource> {

    public AndNode(final FilterNode<Resource> left, final FilterNode<Resource> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate(final Resource objectToEvaluate) {
        return this.right.evaluate(objectToEvaluate) && this.left.evaluate(objectToEvaluate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("( ");
        sb.append(this.left.toString());
        sb.append(" AND ");
        sb.append(this.right.toString());
        sb.append(" )");
        return sb.toString();
    }

}
