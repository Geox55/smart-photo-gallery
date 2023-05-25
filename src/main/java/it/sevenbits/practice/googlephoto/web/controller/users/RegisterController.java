package it.sevenbits.practice.googlephoto.web.controller.users;

import it.sevenbits.practice.googlephoto.web.controller.model.Login;
import it.sevenbits.practice.googlephoto.web.service.login.LoginFailedException;
import it.sevenbits.practice.googlephoto.web.service.login.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * controller register
 */
@RestController
@RequestMapping("/signup")
public class RegisterController {
    private final LoginService loginService;
    private static final  int STATUS_ERROR = 404;

    /**
     * constructor
     * @param loginService - login service
     */
    public RegisterController(final LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * register user
     * @param login - login
     * @return status
     */
    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody final Login login) {
        try {
            loginService.addUser(login);
        } catch (LoginFailedException e) {
            return ResponseEntity.status(STATUS_ERROR).build();
        }

        return ResponseEntity.ok().build();
    }
}
