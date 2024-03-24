package com.abit8.jdbc.exceptions;

public class DaoException extends RuntimeException {
    public DaoException(Throwable e) {
        super(e);
    }
}
