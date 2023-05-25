package it.sevenbits.practice.googlephoto.web.service.login;

import it.sevenbits.practice.googlephoto.core.model.user.User;
import it.sevenbits.practice.googlephoto.core.repository.security.PasswordEncoder;
import it.sevenbits.practice.googlephoto.core.repository.user.UserRepository;
import it.sevenbits.practice.googlephoto.web.controller.model.Login;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Login service
 */
@Service
public class LoginService implements ILoginService {
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    /**
     * constructor
     * @param users - repository
     * @param passwordEncoder password encoder
     */
    public LoginService(
           final UserRepository users,
            final PasswordEncoder passwordEncoder
    ) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(final Login login) {
        User user = users.findByUserName(login.getEmail());
        if (user == null) {
            throw new LoginFailedException(
                    "User '" + login.getEmail() + "' not found"
            );
        }

        if (
                !passwordEncoder.matches(login.getPassword(), user.getPassword())
        ) {
            throw new LoginFailedException("Wrong password");
        }
        return new User(user.getPlayerId(), user.getEmail(), user.getName(), user.getPassword(), user.getRoles());
    }

    @Override
    public boolean addUser(final Login login) {
        List<String> roleList = new ArrayList<>();
        roleList.add("USER");
        User user = new User(UUID.randomUUID().toString(), login.getEmail(), login.getName(),
                passwordEncoder.hash(login.getPassword()), roleList);
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new LoginFailedException("Login or password null");
        }

        if (users.findByUserName(user.getEmail()) != null) {
            throw new LoginFailedException("User ' " + user.getEmail() + "' already registered");
        }

        return users.addUser(user);
    }

}
