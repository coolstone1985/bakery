package com.ashwini.bakery;

/**
 * The type Unknown product exception.
 */
public class UnknownProductException extends Exception {
    /**
     * Instantiates a new Unknown product exception.
     *
     * @param code the product code
     */
    public UnknownProductException(String code) {
        super("Unknown product code: " + code);
    }
}