package com.bekmeh.fr.filter.node;

import com.bekmeh.fr.resource.User;

public class IsPresentNode extends FilterNode<User> {

    private String key;

    public IsPresentNode(final String key) {
        this.key = key;
    }

    @Override
    public boolean evaluate(final User objectToEvaluate) {
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
