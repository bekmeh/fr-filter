package com.bekmeh.fr.filter;

import com.bekmeh.fr.filter.node.AndNode;
import com.bekmeh.fr.filter.node.EqualToNode;
import com.bekmeh.fr.filter.node.FilterNode;
import com.bekmeh.fr.filter.node.GreaterThanNode;
import com.bekmeh.fr.filter.node.IsPresentNode;
import com.bekmeh.fr.filter.node.LessThanNode;
import com.bekmeh.fr.filter.node.NotNode;
import com.bekmeh.fr.filter.node.OrNode;
import com.bekmeh.fr.filter.node.RegexNode;
import com.bekmeh.fr.resource.User;

public class Filter {

    private FilterNode<User> rootNode;

    public Filter() {

    }

    public Filter(final String key, final Comparator comparator, final Object comparate) {
        this.rootNode = createNewNode(key, comparator, comparate);
    }

    public Filter(final String key, final Comparator comparator, final Object comparate, final boolean desiredResult) {
        this.rootNode = new NotNode(createNewNode(key, comparator, comparate));
    }

    public Filter(final FilterNode<User> rootNode) {
        this.rootNode = rootNode;
    }

    public Filter and(final String key, final Comparator comparator, final Object comparate) {
        final FilterNode<User> newNode = createNewNode(key, comparator, comparate);

        if (this.rootNode instanceof OrNode) {
            this.rootNode.setRight(new AndNode(this.rootNode.getRight(), newNode));
        } else {
            this.rootNode = new AndNode(this.rootNode, newNode);
        }
        return this;
    }

    public Filter or(final String key, final Comparator comparator, final Object comparate) {
        final FilterNode<User> newNode = createNewNode(key, comparator, comparate);

        if (this.rootNode instanceof OrNode) {
            this.rootNode.setRight(new OrNode(this.rootNode.getRight(), newNode));
        } else {
            this.rootNode = new OrNode(this.rootNode, newNode);
        }
        return this;
    }

    /**
     * In this case, NOT is treated as AND NOT.
     * 
     * @param key
     * @param comparator
     * @param comparate
     * @return
     */
    public Filter not(final String key, final Comparator comparator, final Object comparate) {
        final NotNode newNode = new NotNode(createNewNode(key, comparator, comparate));

        if (this.rootNode == null) {
            this.rootNode = newNode;
        } else if (this.rootNode instanceof OrNode) {
            this.rootNode.setRight(new AndNode(this.rootNode.getRight(), newNode));
        } else {
            this.rootNode = new AndNode(this.rootNode, newNode);
        }
        return this;
    }

    public boolean matches(final User user) {
        return rootNode.evaluate(user);
    }

    private FilterNode<User> createNewNode(final String key, final Comparator comparator, final Object comparate) {
        switch (comparator) {
            case EQ:
                return new EqualToNode(key, comparate);
            case GT:
                return new GreaterThanNode(key, comparate);
            case LT:
                return new LessThanNode(key, comparate);
            case REGEX:
                return new RegexNode(key, comparate);
            case PRESENT:
                return new IsPresentNode(key);
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return this.rootNode.toString();
    }

}
