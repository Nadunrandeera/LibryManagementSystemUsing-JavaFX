package lk.codeschool.libry_management_system.controller.util.exception.custom;

import lk.codeschool.libry_management_system.controller.util.exception.ServiceException;

public class CategoryException extends ServiceException {
    public CategoryException() {
    }

    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
