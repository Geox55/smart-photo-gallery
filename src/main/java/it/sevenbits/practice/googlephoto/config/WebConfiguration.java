package it.sevenbits.practice.googlephoto.config;

import it.sevenbits.practice.googlephoto.web.controller.security.UserCredentialsResolver;
import it.sevenbits.practice.googlephoto.web.controller.users.JwtAuthInterceptor;
import it.sevenbits.practice.googlephoto.web.controller.users.JwtTokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * class webconfiguration
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final JwtTokenService jwtTokenService;

    /**
     * constructor
     * @param jwtTokenService - token
     */
    public WebConfiguration(
            final JwtTokenService jwtTokenService
    ) {
        this.jwtTokenService = jwtTokenService;
    }


    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserCredentialsResolver());
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(
                new JwtAuthInterceptor(jwtTokenService)
        );
    }

}
