package com.bekmeh.fr.filter;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.bekmeh.fr.resource.User;

public class FilterTest {

	private static final String HEIGHT_FIELD = "height";
	private static final String AGE_FIELD = "age";
	private static final String NAME_FIELD = "name";
	private User testUser1 = new User();
	private User testUser2 = new User();

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
		Filter filter = new Filter(AGE_FIELD, Filter.IS, UnaryCondition.PRESENT);
		assertTrue(filter.matches(testUser1));
		assertTrue(filter.matches(testUser2));
	}

	@Test
	public void testAgeIsGreaterThan5AndLessThan10() {
		Filter filter = new Filter(AGE_FIELD, Filter.IS, BinaryCondition.GREATER_THAN, 5)
							  .and(AGE_FIELD, Filter.IS, BinaryCondition.LESS_THAN, 10);
		assertTrue(filter.matches(testUser1));
		assertFalse(filter.matches(testUser2));
	}

	@Test
	public void testAgeGreaterThan15OrLessThan10() {
		Filter filter = new Filter(AGE_FIELD, Filter.IS, BinaryCondition.GREATER_THAN, 15)
							   .or(AGE_FIELD, Filter.IS, BinaryCondition.LESS_THAN, 10);
		assertTrue(filter.matches(testUser1));
		assertTrue(filter.matches(testUser2));
	}

	@Test
	public void testAgeIsNotLessThan10() {
		Filter filter = new Filter(AGE_FIELD, Filter.NOT, BinaryCondition.LESS_THAN, 10);
		assertFalse(filter.matches(testUser1));
		assertTrue(filter.matches(testUser2));
	}

	@Test
	public void testAgeIsGreaterThan5AndNotEqual10() {
		Filter filter = new Filter(AGE_FIELD, Filter.IS, BinaryCondition.GREATER_THAN, 5)
							  .and(AGE_FIELD, Filter.IS, BinaryCondition.EQUAL_TO, 10);
		assertFalse(filter.matches(testUser1));
		assertFalse(filter.matches(testUser2));
	}

	@Test
	public void testAgeIsLessThan15AndHeightIsLessThan5() {
		Filter filter = new Filter(AGE_FIELD, Filter.IS, BinaryCondition.LESS_THAN, 15)
							  .and(HEIGHT_FIELD, Filter.IS, BinaryCondition.LESS_THAN, 5);
		assertTrue(filter.matches(testUser1));
		assertFalse(filter.matches(testUser2));
	}

	@Test
	public void testNameMatchesRegex() {
		Filter filter = new Filter(NAME_FIELD, Filter.IS, BinaryCondition.REGEX_MATCH, "Joh?n Smithe?");
		assertTrue(filter.matches(testUser1));
		assertFalse(filter.matches(testUser2));
	}
	
	@Test
	public void testAgeIsGreaterThan10OrLessThan4OrHeightGreaterThan4() {
		Filter filter = new Filter(AGE_FIELD, Filter.IS, BinaryCondition.GREATER_THAN, 90)
							   .or(AGE_FIELD, Filter.IS, BinaryCondition.LESS_THAN, 4)
							   .or(HEIGHT_FIELD, Filter.IS, BinaryCondition.GREATER_THAN, 4);
		assertTrue(filter.matches(testUser1));
		assertTrue(filter.matches(testUser2));
	}
	@Test
	public void testFilterToString() {
		Filter filter = new Filter(AGE_FIELD, Filter.IS, BinaryCondition.GREATER_THAN, 90)
				.or(AGE_FIELD, Filter.IS, BinaryCondition.LESS_THAN, 4)
				.or(HEIGHT_FIELD, Filter.IS, BinaryCondition.GREATER_THAN, 4);
		String expectedFilterString = "age is greater than 90 "
				+ "or age is less than 4 "
				+ "or height is greater than 4";
		assertEquals(expectedFilterString, filter.toString());
	}

}
