package lk.codeschool.libry_management_system.controller.util.exception.custom;

import lk.codeschool.libry_management_system.controller.util.exception.ServiceException;

public class MemberException extends ServiceException {
    public MemberException() {
    }

    public MemberException(String message) {
        super(message);
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }
}
