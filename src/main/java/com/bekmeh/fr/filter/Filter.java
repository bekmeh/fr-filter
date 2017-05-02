package com.bekmeh.fr.filter;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Filter {
	
	public static final boolean IS = false;
	public static final boolean NOT = true;
	
	private List<FieldCondition> fieldConditions = new LinkedList<>();
	
	public Filter(final String stringRepresentation) {
		// Not yet implemented;
	}
	
	public Filter(final String fieldName, final boolean negation, final BinaryCondition condition, final Object comparisonValue) {
		this.fieldConditions.add(new FieldCondition(BooleanOperator.AND, fieldName, negation, condition, comparisonValue));
	}
	
	public Filter(final String fieldName, final boolean negation, final UnaryCondition condition) {
		this.fieldConditions.add(new FieldCondition(BooleanOperator.AND, fieldName, negation, condition));
	}

	public Filter and(final String fieldName, final boolean negation, final BinaryCondition condition, final Object comparisonValue) {
		this.fieldConditions.add(new FieldCondition(BooleanOperator.AND, fieldName, negation, condition, comparisonValue));
		return this;
	}

	public Filter and(final String fieldName, final boolean negation, final UnaryCondition condition) {
		this.fieldConditions.add(new FieldCondition(BooleanOperator.AND, fieldName, negation, condition));
		return this;
	}

	public Filter or(final String fieldName, final boolean negation, final BinaryCondition condition, final Object comparisonValue) {
		this.fieldConditions.add(new FieldCondition(BooleanOperator.OR, fieldName, negation, condition, comparisonValue));
		return this;
	}

	public Filter or(final String fieldName, final boolean negation, final UnaryCondition condition) {
		this.fieldConditions.add(new FieldCondition(BooleanOperator.OR, fieldName, negation, condition));
		return this;
	}
	
	public boolean matches(final Map<String, String> filterTarget) {
		
		if(this.fieldConditions.isEmpty()) {
			return false;
		}
		
		boolean matches = true;
		
		for (FieldCondition fieldCondition : this.fieldConditions) {
			BooleanOperator operator = fieldCondition.getBooleanOperator();
			boolean isConditionTrue = isConditionTrue(filterTarget, fieldCondition);
			
			if (fieldCondition.isNegated()) {
				isConditionTrue = !isConditionTrue;
			}
			
			if (operator == BooleanOperator.AND) {
				matches = matches && isConditionTrue;
			} else if (operator == BooleanOperator.OR) {
				matches = matches || isConditionTrue;
			}
		}
		
		return matches;
	}
	
	
	private boolean isConditionTrue(final Map<String, String> filterTarget, final FieldCondition fieldCondition) {
		Condition condition = fieldCondition.getCondition();
		Object comparisonValue = fieldCondition.getComparisonValue();

		if (condition instanceof BinaryCondition) {
			if (!filterTarget.containsKey(fieldCondition.getFieldName())) {
				return false;
			}
			String fieldValue = filterTarget.get(fieldCondition.getFieldName());
			BigDecimal decimalFieldValue;
			BigDecimal decimalComparisonValue;

			switch ((BinaryCondition) condition) {
				case EQUAL_TO:
					return fieldValue.toString().equals(comparisonValue.toString());
				case GREATER_THAN:
					decimalFieldValue = new BigDecimal(fieldValue);
					decimalComparisonValue = new BigDecimal(comparisonValue.toString());
					return decimalFieldValue.compareTo(decimalComparisonValue) == 1;
				case LESS_THAN:
					decimalFieldValue = new BigDecimal(fieldValue);
					decimalComparisonValue = new BigDecimal(comparisonValue.toString());
					return decimalFieldValue.compareTo(decimalComparisonValue) == -1;
				case REGEX_MATCH:
					return fieldValue.matches((String) comparisonValue);
			}
		} else if (condition instanceof UnaryCondition) {
			switch ((UnaryCondition) condition) {
				case PRESENT:
					return filterTarget.containsKey(fieldCondition.getFieldName());
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean isFirstCondition = true;
		for (FieldCondition fieldCondition : this.fieldConditions) {
			if (!isFirstCondition) {
				sb.append(" ");
				sb.append(fieldCondition.getBooleanOperator());
				sb.append(" ");
			}
			sb.append(fieldCondition.getFieldName());
			sb.append(" ");
			sb.append(fieldCondition.isNegated() ? "is not" : "is");
			sb.append(" ");
			sb.append(fieldCondition.getCondition().toString());
			if (fieldCondition.getCondition() instanceof BinaryCondition) {
				sb.append(" ");
				sb.append(fieldCondition.getComparisonValue());
			}
			isFirstCondition = false;
		}
		
		return sb.toString().trim();
	}

}
