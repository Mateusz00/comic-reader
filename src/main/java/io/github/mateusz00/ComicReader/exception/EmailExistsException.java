package io.github.mateusz00.ComicReader.exception;

public class EmailExistsException extends RuntimeException
{
    public EmailExistsException(String message) {
        super(message);
    }
}