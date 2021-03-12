package io.github.mateusz00.ComicReader.exception;

public class UsernameExistsException extends RuntimeException
{
    public UsernameExistsException(String message) {
        super(message);
    }
}
