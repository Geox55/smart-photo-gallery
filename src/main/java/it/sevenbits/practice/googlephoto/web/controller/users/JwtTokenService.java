package it.sevenbits.practice.googlephoto.web.controller.users;

import it.sevenbits.practice.googlephoto.core.model.user.User;
import it.sevenbits.practice.googlephoto.web.controller.security.UserCredentials;

/**
 * jwt token interface
 */
public interface JwtTokenService {
    /**
     * Parses the token
     * @param token the token string to parse
     * @return authenticated data
     */
    UserCredentials parseToken(String token);

    /**
     * Creates new Token for user.
     * @param user contains User to be represented as token
     * @return signed token
     */
    String createToken(User user);

}
