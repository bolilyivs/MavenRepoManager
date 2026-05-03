package ru.bolilyivs.server.data.exception;

import io.micronaut.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepoException extends RuntimeException {

    private final HttpStatus httpStatus;

    public RepoException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public RepoException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public RepoException() {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public static RepoException of(String message) {
        return new RepoException(message);
    }

    public static RepoException of(String id, String message) {
        return new RepoException("id: %s %s".formatted(id, message));
    }

    public static RepoException ofNotFound(String entity, String id) {
        return new RepoException("Не найден %s по id %s".formatted(entity, id), HttpStatus.NOT_FOUND);
    }
}
