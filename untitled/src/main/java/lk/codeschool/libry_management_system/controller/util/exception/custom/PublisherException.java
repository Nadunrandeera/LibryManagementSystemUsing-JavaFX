package lk.codeschool.libry_management_system.controller.util.exception.custom;

import lk.codeschool.libry_management_system.controller.util.exception.ServiceException;

public class PublisherException extends ServiceException {
    public PublisherException() {
    }

    public PublisherException(String message) {
        super(message);
    }

    public PublisherException(String message, Throwable cause) {
        super(message, cause);
    }
}
