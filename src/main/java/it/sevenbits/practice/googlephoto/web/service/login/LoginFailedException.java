package it.sevenbits.practice.googlephoto.web.service.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * login Exception
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class LoginFailedException extends RuntimeException {
    /**
     * constructor
     * @param message - massage
     * @param cause - cause
     */

    public LoginFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor
     * @param message - massage
     */
    public LoginFailedException(final String message) {
        super(message);
    }
}