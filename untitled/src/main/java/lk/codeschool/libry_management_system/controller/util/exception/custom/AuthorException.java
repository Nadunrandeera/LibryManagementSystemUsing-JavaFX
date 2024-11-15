package lk.codeschool.libry_management_system.controller.util.exception.custom;

import lk.codeschool.libry_management_system.controller.util.exception.ServiceException;

public class AuthorException extends ServiceException {
    public AuthorException() {
    }

    public AuthorException(String message) {
        super(message);
    }

    public AuthorException(String message, Throwable cause) {
        super(message, cause);
    }
}
