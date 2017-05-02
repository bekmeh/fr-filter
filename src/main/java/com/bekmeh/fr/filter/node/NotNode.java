package com.bekmeh.fr.filter.node;

import com.bekmeh.fr.resource.Resource;

/**
 * {@link FilterNode} which, when evaluated, determines the inverse of the
 * result of the child branch.
 * 
 * @author bekmeh
 *
 */
public class NotNode extends FilterNode<Resource> {

    private FilterNode<Resource> child;

    public NotNode(final FilterNode<Resource> child) {
        this.child = child;
    }

    @Override
    public boolean evaluate(final Resource objectToEvaluate) {
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
