package com.bekmeh.fr.filter.node;

import com.bekmeh.fr.resource.Resource;

/**
 * {@link FilterNode} which, when evaluated, determines whether the resource
 * value is equal to the comparate value supplied in the constructor.
 * 
 * @author bekmeh
 *
 */
public class EqualToNode extends FilterNode<Resource> {

    private String key;
    private Object value;

    public EqualToNode(final String key, final Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean evaluate(final Resource objectToEvaluate) {
        if (objectToEvaluate.containsKey(this.key)) {
            return objectToEvaluate.get(this.key)
                                   .toString()
                                   .equals(this.value.toString());
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("( \"");
        sb.append(this.key);
        sb.append("\" EQUAL TO ");
        sb.append(this.value);
        sb.append(" )");
        return sb.toString();
    }

}
