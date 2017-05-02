package com.bekmeh.fr.filter;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.bekmeh.fr.filter.node.AndNode;
import com.bekmeh.fr.filter.node.EqualToNode;
import com.bekmeh.fr.filter.node.GreaterThanNode;
import com.bekmeh.fr.filter.node.IsPresentNode;
import com.bekmeh.fr.filter.node.LessThanNode;
import com.bekmeh.fr.filter.node.NotNode;
import com.bekmeh.fr.filter.node.OrNode;
import com.bekmeh.fr.filter.node.RegexNode;
import com.bekmeh.fr.resource.Resource;

/**
 * Unit tests for {@link Filter}.
 * 
 * @author bekmeh
 *
 */
public class FilterTest {

    private static final String HEIGHT_FIELD = "height";
    private static final String AGE_FIELD    = "age";
    private static final String NAME_FIELD   = "name";
    private static final String NAME_REGEX   = "Joh?n Smithe?";
    private Resource                testUser1    = new Resource();
    private Resource                testUser2    = new Resource();

    @Before
    public void setUp() {
        testUser1.put(NAME_FIELD, "John Smith");
        testUser1.put(AGE_FIELD, "7");
        testUser1.put(HEIGHT_FIELD, "4.6");

        testUser2.put(NAME_FIELD, "Maggie Simpson");
        testUser2.put(AGE_FIELD, "70");
        testUser2.put(HEIGHT_FIELD, "4.6");
    }

    @Test
    public void testAgeIsPresent() {
        Filter filter = new Filter(new IsPresentNode(AGE_FIELD));
        assertTrue(filter.matches(testUser1));
        assertTrue(filter.matches(testUser2));

        Filter filterV2 = new Filter(AGE_FIELD, Comparator.PRESENT, null);
        assertTrue(filterV2.matches(testUser1));
        assertTrue(filterV2.matches(testUser2));
    }

    @Test
    public void testAgeIsGreaterThan5AndLessThan10() {
        Filter filter = new Filter(
                                   new AndNode(
                                               new GreaterThanNode(AGE_FIELD, 5), 
                                               new LessThanNode(AGE_FIELD, 10)));
        assertTrue(filter.matches(testUser1));
        assertFalse(filter.matches(testUser2));

        Filter filterV2 = new Filter(AGE_FIELD, Comparator.GT, 5).and(AGE_FIELD, Comparator.LT, 10);
        assertTrue(filterV2.matches(testUser1));
        assertFalse(filterV2.matches(testUser2));

    }

    @Test
    public void testAgeGreaterThan15OrLessThan10() {
        Filter filter = new Filter(
                                   new OrNode(
                                              new GreaterThanNode(AGE_FIELD, 15), 
                                              new LessThanNode(AGE_FIELD, 10)));
        assertTrue(filter.matches(testUser1));
        assertTrue(filter.matches(testUser2));

        Filter filterV2 = new Filter(AGE_FIELD, Comparator.GT, 15).or(AGE_FIELD, Comparator.LT, 10);
        assertTrue(filterV2.matches(testUser1));
        assertTrue(filterV2.matches(testUser2));
    }

    @Test
    public void testAgeIsNotLessThan10() {
        Filter filter = new Filter(
                                   new NotNode(
                                               new LessThanNode(AGE_FIELD, 10)));
        assertFalse(filter.matches(testUser1));
        assertTrue(filter.matches(testUser2));

        Filter filterV2 = new Filter().not(AGE_FIELD, Comparator.LT, 10);
        assertFalse(filterV2.matches(testUser1));
        assertTrue(filterV2.matches(testUser2));
    }

    @Test
    public void testAgeIsGreaterThan5AndNotEqual10() {
        Filter filter = new Filter(
                                   new AndNode(new GreaterThanNode(AGE_FIELD, 5),
                                               new NotNode(new EqualToNode(AGE_FIELD, 10))));
        assertTrue(filter.matches(testUser1));
        assertTrue(filter.matches(testUser2));

        Filter filterV2 = new Filter(AGE_FIELD, Comparator.GT, 5).not(AGE_FIELD, Comparator.EQ, 10);

        assertTrue(filterV2.matches(testUser1));
        assertTrue(filterV2.matches(testUser2));
    }

    @Test
    public void testAgeIsLessThan15AndHeightIsLessThan5() {
        Filter filter = new Filter(
                                   new AndNode(
                                               new LessThanNode(AGE_FIELD, 15), 
                                               new LessThanNode(HEIGHT_FIELD, 5)));
        assertTrue(filter.matches(testUser1));
        assertFalse(filter.matches(testUser2));

        Filter filterV2 = new Filter(AGE_FIELD, Comparator.LT, 15).and(HEIGHT_FIELD, Comparator.LT, 5);
        assertTrue(filterV2.matches(testUser1));
        assertFalse(filterV2.matches(testUser2));
    }

    @Test
    public void testNameMatchesRegex() {
        Filter filter = new Filter(new RegexNode(NAME_FIELD, NAME_REGEX));
        assertTrue(filter.matches(testUser1));
        assertFalse(filter.matches(testUser2));

        Filter filterV2 = new Filter(NAME_FIELD, Comparator.REGEX, NAME_REGEX);
        assertTrue(filterV2.matches(testUser1));
        assertFalse(filterV2.matches(testUser2));
    }

    @Test
    public void testAgeIsGreaterThan10OrLessThan4OrHeightGreaterThan4() {
        Filter filter = new Filter(
                                   new OrNode(
                                              new OrNode(
                                                         new GreaterThanNode(AGE_FIELD, 10),
                                                         new LessThanNode(AGE_FIELD, 4)),
                                              new GreaterThanNode(HEIGHT_FIELD, 4)));
        assertTrue(filter.matches(testUser1));
        assertTrue(filter.matches(testUser2));

        Filter filterV2 = new Filter(AGE_FIELD, Comparator.GT, 100).or(AGE_FIELD, Comparator.LT, 4)
                                                                   .or(HEIGHT_FIELD, Comparator.GT, 4);
        assertTrue(filterV2.matches(testUser1));
        assertTrue(filterV2.matches(testUser2));
    }

    @Test
    public void testFilterToString() {
        Filter filter = new Filter(
                                   new OrNode(
                                              new GreaterThanNode(AGE_FIELD, 10),
                                              new OrNode(
                                                         new LessThanNode(AGE_FIELD, 4),
                                                         new GreaterThanNode(HEIGHT_FIELD, 4))));
        String expectedFilterString = "( ( \"age\" GREATER THAN 10 ) OR " 
                                      + "( ( \"age\" LESS THAN 4 ) OR "
                                      + "( \"height\" GREATER THAN 4 ) ) )";
        assertEquals(expectedFilterString, filter.toString());

        Filter filterV2 = new Filter(AGE_FIELD, Comparator.GT, 10).or(AGE_FIELD, Comparator.LT, 4)
                                                                  .or(HEIGHT_FIELD, Comparator.GT, 4);

        assertEquals(expectedFilterString, filterV2.toString());
    }

}
