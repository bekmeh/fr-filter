package com.bekmeh.fr.filter.node;

import com.bekmeh.fr.resource.Resource;

/**
 * {@link FilterNode} which, when evaluated, determines whether the resource
 * key/value is present.
 * 
 * @author bekmeh
 *
 */
public class IsPresentNode extends FilterNode<Resource> {

    private String key;

    public IsPresentNode(final String key) {
        this.key = key;
    }

    @Override
    public boolean evaluate(final Resource objectToEvaluate) {
        return objectToEvaluate.containsKey(this.key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("( \"");
        sb.append(this.key);
        sb.append("\" IS PRESENT");
        sb.append(" )");
        return sb.toString();
    }

}
