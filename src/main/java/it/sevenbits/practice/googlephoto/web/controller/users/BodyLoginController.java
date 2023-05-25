package it.sevenbits.practice.googlephoto.web.controller.users;

import it.sevenbits.practice.googlephoto.core.model.user.User;
import it.sevenbits.practice.googlephoto.web.controller.model.Login;
import it.sevenbits.practice.googlephoto.web.controller.model.Token;
import it.sevenbits.practice.googlephoto.web.service.login.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * class for login controller
 */
@RestController
@RequestMapping("/signin")
public class BodyLoginController {
    private final LoginService loginService;
    private final JwtTokenService tokenService;

    /**
     * constructor
     * @param loginService - login service
     * @param tokenService - token service
     */
    public BodyLoginController(final LoginService loginService, final JwtTokenService tokenService) {
        this.loginService = loginService;
        this.tokenService = tokenService;
    }

    /**
     * create token
     * @param login - login
     * @return token
     */
    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping
    @ResponseBody
    public ResponseEntity<Token> create(@RequestBody final Login login) {
        User user = loginService.login(login);
        String token = tokenService.createToken(user);
        return ResponseEntity.ok().body(new Token(token));
    }
}
