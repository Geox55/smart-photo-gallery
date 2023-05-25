package it.sevenbits.practice.googlephoto.web.controller.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model to receive username and password.
 */

public class Login {
    private final String email;
    private final String name;
    private final String password;

    /**
     * Login
     * @param login - email
     * @param name - name
     * @param password - password
     */
    @JsonCreator
    public Login(
            @JsonProperty("email") final String login,
            @JsonProperty("name") final String name,
            @JsonProperty("password") final String password
    ) {
        this.email = login;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
