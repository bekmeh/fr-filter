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
import com.bekmeh.fr.resource.Resource;

/**
 * A filter can be used on a Resource, i.e. a Map&lt;String, String&gt;. A new
 * Filter can be created in two ways:
 * <ul>
 * <li>Using only the constructor, and nesting {@link FilterNode}s.</li>
 * <p>
 * <code>Filter filter = new Filter(
                                   new AndNode(
                                               new GreaterThanNode(AGE_FIELD, 5), 
                                               new LessThanNode(AGE_FIELD, 10)));</code>
 * </p>
 * <li>Using the constructor and chaining and(), or() and not() methods.</li>
 * <p>
 * <code>Filter filter = new Filter(AGE_FIELD, Comparator.GT, 5).and(AGE_FIELD, Comparator.LT, 10);</code>
 * </p>
 * </ul>
 * 
 * To evaluate whether a resource matches the new filter, use the matches()
 * method.
 * 
 * 
 * @author bekmeh
 *
 */
public class Filter {

    private FilterNode<Resource> rootNode;

    /**
     * Empty constructor. Can be used to create a new Filter and subsequently
     * chaining a single not().
     */
    public Filter() {
        // No content required.
    }

    /**
     * Constructor to use when either including only one condition (e.g. x > y,
     * x == y), or to start a chain of and()/or()/not().
     * 
     * @param key
     *            The key of the resource.
     * @param comparator
     *            The {@link Comparator}, e.g. less than, greater than.
     * @param comparate
     *            The object to compare the resource value to.
     */
    public Filter(final String key, final Comparator comparator, final Object comparate) {
        this.rootNode = createNewNode(key, comparator, comparate);
    }

    /**
     * Constructor to use when creating a new filter using nested
     * {@link FilterNode}s.
     * 
     * @param rootNode
     */
    public Filter(final FilterNode<Resource> rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * Adds an AND node to the FilterNode tree. The location of this node
     * depends on what type the current root node is:
     * 
     * <ul>
     * <li>OR node: the new AND node will be placed as one of the current root's
     * children. The child it is replacing will become a new child of the new
     * AND node.</li>
     * <li>Not an OR node: the root will become an AND node with one child as
     * the previous root.</li>
     * </ul>
     * 
     * @param key
     *            The key of the resource.
     * @param comparator
     *            The {@link Comparator}, e.g. less than, greater than.
     * @param comparate
     *            The object to compare the resource value to.
     * @return The altered Filter object
     */
    public Filter and(final String key, final Comparator comparator, final Object comparate) {
        final FilterNode<Resource> newNode = createNewNode(key, comparator, comparate);

        if (this.rootNode instanceof OrNode) {
            this.rootNode.setRight(new AndNode(this.rootNode.getRight(), newNode));
        } else {
            this.rootNode = new AndNode(this.rootNode, newNode);
        }
        return this;
    }

    /**
     * Adds an OR node to the FilterNode tree. The location of this node depends
     * on what type the current root node is (see and()).
     * 
     * @param key
     *            The key of the resource.
     * @param comparator
     *            The {@link Comparator}, e.g. less than, greater than.
     * @param comparate
     *            The object to compare the resource value to.
     * @return The altered Filter object
     */
    public Filter or(final String key, final Comparator comparator, final Object comparate) {
        final FilterNode<Resource> newNode = createNewNode(key, comparator, comparate);

        if (this.rootNode instanceof OrNode) {
            this.rootNode.setRight(new OrNode(this.rootNode.getRight(), newNode));
        } else {
            this.rootNode = new OrNode(this.rootNode, newNode);
        }
        return this;
    }

    /**
     * In this case, NOT is treated as AND NOT unless there is no root node.
     * 
     * @param key
     *            The key of the resource.
     * @param comparator
     *            The {@link Comparator}, e.g. less than, greater than.
     * @param comparate
     *            The object to compare the resource value to.
     * @return The altered Filter object
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

    /**
     * Evaluates the FilterNode tree; determines if the resource satisfies the
     * filter.
     * 
     * @param resource
     *            The resource to test the filter against.
     * @return true if the resource matches, false if it does not.
     */
    public boolean matches(final Resource resource) {
        return rootNode.evaluate(resource);
    }

    /**
     * Creates a new node using the resource key, comparator and comparate.
     * 
     * @param key
     *            The key of the resource.
     * @param comparator
     *            The {@link Comparator}, e.g. less than, greater than.
     * @param comparate
     *            The object to compare the resource value to.
     * @return A new FilterNode.
     */
    private FilterNode<Resource> createNewNode(final String key, final Comparator comparator, final Object comparate) {
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
