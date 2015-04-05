package edu.jsu.mcis;

public class FileErrorException extends RuntimeException {
    public FileErrorException (String message) {
        super (message);
    }
}