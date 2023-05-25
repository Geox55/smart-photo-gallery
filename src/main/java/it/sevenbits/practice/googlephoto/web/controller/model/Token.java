package it.sevenbits.practice.googlephoto.web.controller.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Model to send token.
 */

public class Token {
    private final String accessToken;

    /**
     * constructor
     * @param token - token
     */
    @JsonCreator
    public Token(@JsonProperty("accessToken") final  String token) {
        this.accessToken = token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Token token = (Token) o;
        return Objects.equals(accessToken, token.accessToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken);
    }
}
