package com.kostka.efhomework.exception;

public class NoPermissionException extends RuntimeException {
    public NoPermissionException(final String message) {
        super(message);
    }

    public NoPermissionException(final Throwable cause) {
        super("The permission is not granted.", cause);
    }
}
