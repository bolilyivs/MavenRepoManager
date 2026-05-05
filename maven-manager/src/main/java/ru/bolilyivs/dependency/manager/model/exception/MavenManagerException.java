package ru.bolilyivs.dependency.manager.model.exception;

public class MavenManagerException extends RuntimeException {


    public MavenManagerException(String message) {
        super(message);
    }

    public MavenManagerException(String message, Exception cause) {
        super(message, cause);
    }
}
