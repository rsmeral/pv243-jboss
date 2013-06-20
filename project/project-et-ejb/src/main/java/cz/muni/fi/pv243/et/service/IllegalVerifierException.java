package cz.muni.fi.pv243.et.service;

public class IllegalVerifierException extends RuntimeException {
    public IllegalVerifierException() {
    }

    public IllegalVerifierException(String message) {
        super(message);
    }

    public IllegalVerifierException(String message, Throwable cause) {
        super(message, cause);
    }
}
