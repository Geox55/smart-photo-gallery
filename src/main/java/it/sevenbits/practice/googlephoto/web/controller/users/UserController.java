package it.sevenbits.practice.googlephoto.web.controller.users;

import it.sevenbits.practice.googlephoto.core.model.user.User;
import it.sevenbits.practice.googlephoto.core.repository.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller to list users.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository usersRepository;

    /**
     * constructor
     *
     * @param userRepository - user repository
     */
    public UserController(final UserRepository userRepository) {
        this.usersRepository = userRepository;
    }
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping
    @ResponseBody
    @AuthRoleRequired("ADMIN")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(usersRepository.findAll());
    }

    /**
     * get user info
     *
     * @param username - name
     * @return user
     */
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping(value = "/{username}")
    @ResponseBody
    @AuthRoleRequired("ADMIN")
    public ResponseEntity<User> getUserInfo(@PathVariable("username") final String username) {
        return Optional
                .ofNullable(usersRepository.findByUserName(username))
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}


