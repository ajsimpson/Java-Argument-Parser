package edu.jsu.mcis;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException (String message) {
        super (message);
    }
}