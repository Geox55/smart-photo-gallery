package it.sevenbits.practice.googlephoto.core.repository.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * password encounder
 */
public class BCryptPasswordEncoder  implements PasswordEncoder {
    private static final int TEN = 10;

    /**
     * matches
     * @param plainPassword the entered plain text password
     * @param hashedPassword the stored hashed password
     * @return boolean
     */
    public boolean matches(final String plainPassword, final String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    /**
     * hash
     * @param password - password
     * @return hash password
     */
    public String hash(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(TEN));
    }

}
