package ru.terra.game.server.storage.jpa.controller.exceptions;

public class PreexistingEntityException extends Exception {
    public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreexistingEntityException(String message) {
        super(message);
    }
}
