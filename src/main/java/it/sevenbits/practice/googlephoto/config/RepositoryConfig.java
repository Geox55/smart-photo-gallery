package it.sevenbits.practice.googlephoto.config;

import it.sevenbits.practice.googlephoto.core.repository.security.BCryptPasswordEncoder;
import it.sevenbits.practice.googlephoto.core.repository.security.PasswordEncoder;
import it.sevenbits.practice.googlephoto.core.repository.user.UserRepository;
import it.sevenbits.practice.googlephoto.web.controller.users.JsonWebTokenService;
import it.sevenbits.practice.googlephoto.web.controller.users.JwtSettings;
import it.sevenbits.practice.googlephoto.web.controller.users.JwtTokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * Repository config
 */
@Configuration
public class RepositoryConfig {
    /**
     * repository
     * @param jdbcOperations - operations
     * @return user repository
     */
    @Bean
    public UserRepository userQuizzesRepository(
            @Qualifier("quizzesJdbcOperations")
            final JdbcOperations jdbcOperations
    ) {
        return new UserRepository(jdbcOperations);
    }

    /**
     * password encoder
     * @return password
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * jwt token service
     * @param settings - settings
     * @return token
     */
    @Bean
    public JwtTokenService jwtTokenService(final JwtSettings settings) {
        return new JsonWebTokenService(settings);
    }
}