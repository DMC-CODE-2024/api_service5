package com.gl.mdr.model.constants;

public enum SearchOperation {
	EQUALITY, EQUALITY_CASE_INSENSITIVE,NEGATION, GREATER_THAN, LESS_THAN, LIKE,IN,NOT_IN;

	public static final String[] SIMPLE_OPERATION_SET = { ":", "!", ">", "<", "~","=", "^"};

	public static SearchOperation getSimpleOperation(final char input)
	{
		switch (input) {
		case ':': return EQUALITY;
		case '!': return NEGATION;
		case '>': return GREATER_THAN;
		case '<': return LESS_THAN;
		case '~': return LIKE;
		case '=': return IN; // Assign a character for IN
		case '^': return NOT_IN; // Assign a character for NOT_IN (e.g., '^')
		default: return null;
		}
	}
}
