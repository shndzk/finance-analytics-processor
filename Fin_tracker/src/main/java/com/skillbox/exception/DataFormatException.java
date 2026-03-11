package com.skillbox.exception;

public class DataFormatException extends DataAccessException {

    public DataFormatException(String message) {
        super("Некорректный формат данных: " + message);
    }

    public DataFormatException(String message, Throwable cause) {
        super("Некорректный формат данных: " + message, cause);
    }
}
