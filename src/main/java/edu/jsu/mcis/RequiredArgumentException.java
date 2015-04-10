package edu.jsu.mcis;

public class RequiredArgumentException extends RuntimeException {
    public RequiredArgumentException (String message) {
        super (message);
    }
}