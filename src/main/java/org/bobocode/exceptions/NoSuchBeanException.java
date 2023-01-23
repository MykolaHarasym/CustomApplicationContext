package org.bobocode.exceptions;

public class NoSuchBeanException extends RuntimeException {

    public NoSuchBeanException() {
    }

    public NoSuchBeanException(String message) {
        super(message);
    }
}
