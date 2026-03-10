package com.skillbox.exception;

public class FileNotFoundException extends DataAccessException {

    public FileNotFoundException(String filename) {
        super("Файл не найден: " + filename);
    }

    public FileNotFoundException(String filename, Throwable cause) {
        super("Файл не найден: " + filename, cause);
    }
}
