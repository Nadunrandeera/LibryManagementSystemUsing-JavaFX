package lk.codeschool.libry_management_system.controller.util.exception.custom;

import lk.codeschool.libry_management_system.controller.util.exception.ServiceException;

public class BookException extends ServiceException {
    public BookException() {
    }

    public BookException(String message) {
        super(message);
    }

    public BookException(String message, Throwable cause) {
        super(message, cause);
    }
}
