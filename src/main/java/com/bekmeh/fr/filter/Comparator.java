package com.bekmeh.fr.filter;

/**
 * Enumeration for operations that either compare two values (e.g. x > y, x ==
 * y), or determine if a value is present.
 * 
 * @author bekmeh
 *
 */
public enum Comparator {
    GT,
    LT,
    EQ,
    REGEX,
    PRESENT;
}
