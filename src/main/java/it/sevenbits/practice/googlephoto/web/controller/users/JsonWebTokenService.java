package it.sevenbits.practice.googlephoto.web.controller.users;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.sevenbits.practice.googlephoto.core.model.user.User;
import it.sevenbits.practice.googlephoto.web.controller.security.UserCredentials;
import it.sevenbits.practice.googlephoto.web.controller.security.UserCredentialsImpl;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Service to generate and parse JWT tokens.
 */
public class JsonWebTokenService implements JwtTokenService {

    private final JwtSettings settings;
    private static final String ROLES = "roles";

    /**
     * constructor
     * @param settings - settngs
     */
    public JsonWebTokenService(final JwtSettings settings) {
        this.settings = settings;
    }

    @Override
    public String createToken(final User user) {

        Instant now = Instant.now();

        Claims claims = Jwts.claims()
                .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(Date.from(now))
                .setSubject(user.getName())
                .setExpiration(Date.from(now.plus(settings.getTokenExpiredIn())));
        claims.put(ROLES, user.getRoles());
        claims.put("name", user.getName());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();
    }

    @Override
    @SuppressWarnings("unchecked")
    public UserCredentials parseToken(final String token) {

        Jws<Claims> claims = Jwts.parser().setSigningKey(settings.getTokenSigningKey()).parseClaimsJws(token);

        String subject = claims.getBody().getSubject();
        List<String> roles = claims.getBody().get(ROLES, List.class);
        return new UserCredentialsImpl(subject, Collections.unmodifiableList(roles));
    }

}
