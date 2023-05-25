package it.sevenbits.practice.googlephoto.core.repository.user;

import it.sevenbits.practice.googlephoto.core.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user repository
 */
public class UserRepository {
    private final JdbcOperations jdbcOperations;
    private static final String ROLE = "role";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    /**
     * constructor
     * @param jdbcOperations - operations
     */
    public UserRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    /**
     * find username
     * @param email - email
     * @return user
     */
    public User findByUserName(final String email) {
        Map<String, Object> rawUser;

        try {
            rawUser = jdbcOperations.queryForMap(
                    "SELECT email, password, name FROM users u" +
                            " WHERE u.enabled = true AND u.email = ?",
                    email
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }


        List<String> roles = new ArrayList<>();
        jdbcOperations.query(
                "SELECT email, role FROM user_roles" +
                        " WHERE email = ?",
                resultSet -> {
                    String role = resultSet.getString(this.ROLE);
                    roles.add(role);
                },
                email
        );
        String password = String.valueOf(rawUser.get(PASSWORD));
        String name = String.valueOf(rawUser.get(NAME));
        return new User(email, name, password, roles);
    }

    /**
     * find all
     *
     * @return list user
     */
    public List<User> findAll() {
        HashMap<String, User> users = new HashMap<>();

        for (Map<String, Object> row : jdbcOperations.queryForList(
                "SELECT a.email, a.role, u.name, u.password FROM user_roles a" +
                        " INNER JOIN users u ON a.email=u.email WHERE u.enabled=true")) {

            String emailUser = String.valueOf(row.get(EMAIL));
            String newRole = String.valueOf(row.get(ROLE));
            String nameUser = String.valueOf(row.get(NAME));
            String password = String.valueOf(row.get(PASSWORD));
            User user = users.computeIfAbsent(emailUser, name -> new User(emailUser, nameUser, password, new ArrayList<>()));
            List<String> roles = user.getRoles();
            roles.add(newRole);

        }

        return new ArrayList<>(users.values());
    }

    /**
     * add user
     * @param user - user
     * @return true
     */
    public boolean addUser(final User user) {
        jdbcOperations.update(
                "INSERT INTO users(email, name, password, enabled) VALUES ( ?, ?, ?, true)",
                user.getEmail(), user.getName(), user.getPassword());

        for (String i : user.getRoles()) {
            jdbcOperations.update(
                    "INSERT INTO user_roles(email, role) VALUES (?, ?)", user.getEmail(), i);
        }
        return true;
    }

}
