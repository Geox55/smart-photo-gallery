package it.sevenbits.practice.googlephoto.core.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * class User
 */

public class User {
    @JsonProperty("playerId")
    private final String playerId;
    @JsonIgnore
    private final String email;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("roles")
    private final List<String> roles;

    @JsonIgnore
    private final String password;

    /**
     * Constructor
     *
     * @param playerId - player id
     * @param email    - email
     * @param username - name
     * @param password - password
     * @param roles    - roles
     */

    public User(final String playerId, final String email, final String username, final String password, final List<String> roles) {
        this.playerId = playerId;
        this.email = email;
        this.name = username;
        this.roles = roles;
        this.password = password;
    }

    /**
     * constructor
     *
     * @param playerId - player id
     * @param email    - email
     * @param password - password
     * @param roles    - roles
     */

    public User(final String playerId, final String email, final String password, final List<String> roles) {
        this.playerId = playerId;
        this.email = email;
        name = null;
        this.roles = roles;
        this.password = password;
    }

    /**
     * constructor
     * @param email - email
     * @param roles - roles
     */
    public User(final String email, final List<String> roles) {
        playerId = null;
        this.email = email;
        this.roles = roles;
        name = null;
        password = null;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(playerId, user.playerId) && Objects.equals(email, user.email) &&
                Objects.equals(name, user.name) && Objects.equals(roles, user.roles) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, email, name, roles, password);
    }
}
