package it.sevenbits.practice.googlephoto.web.controller.security;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * class user credentials
 */
public class UserCredentialsImpl implements UserCredentials {
    @JsonProperty("name")
    private final String name;

    @JsonProperty("roles")
    private final Set<String> roles;

    /**
     * constructor
     * @param name - name
     * @param roles - roles
     */
    @JsonCreator
    public UserCredentialsImpl(final String name, final Collection<String> roles) {
        this.name = name;
        this.roles = Collections.unmodifiableSet(new LinkedHashSet<>(roles));
    }

    public String getName() {
        return name;
    }

    public Set<String> getRoles() {
        return roles;
    }

}
