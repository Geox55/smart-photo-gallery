package it.sevenbits.practice.googlephoto.core.repository.security;

/**
 * interface password
 */
public interface PasswordEncoder {

    /**
     * Checks the entered password matches with the hashed password
     * @param plainPassword the entered plain text password
     * @param hashedPassword the stored hashed password
     * @return true if the password matches with the hash
     */
    boolean matches(String plainPassword, String hashedPassword);

    /**
     * hash
     * @param password - password
     * @return hash password
     */
    String hash(String password);

}
