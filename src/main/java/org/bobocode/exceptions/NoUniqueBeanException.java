package org.bobocode.exceptions;

public class NoUniqueBeanException extends RuntimeException {

    public NoUniqueBeanException() {
    }

    public NoUniqueBeanException(String message) {
        super(message);
    }
}
