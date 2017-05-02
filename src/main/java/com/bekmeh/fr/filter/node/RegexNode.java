package com.bekmeh.fr.filter.node;

import com.bekmeh.fr.resource.User;

public class RegexNode extends FilterNode<User> {

    private String key;
    private Object value;

    public RegexNode(final String key, final Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean evaluate(final User objectToEvaluate) {
        if (objectToEvaluate.containsKey(this.key)) {
            return objectToEvaluate.get(this.key).toString().matches(this.value.toString());
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("( \"");
        sb.append(this.key);
        sb.append("\" MATCHES REGEX \"");
        sb.append(this.value);
        sb.append("\" )");
        return sb.toString();
    }

}
