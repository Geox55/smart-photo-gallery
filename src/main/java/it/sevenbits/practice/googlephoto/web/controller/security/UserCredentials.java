package it.sevenbits.practice.googlephoto.web.controller.security;

import java.util.Set;

/**
 * interface
 */

public interface UserCredentials {
    /**
     * get name
     * @return name
     */
    String getName();

    /**
     * get roles
     * @return list roles
     */
    Set<String> getRoles();
}
