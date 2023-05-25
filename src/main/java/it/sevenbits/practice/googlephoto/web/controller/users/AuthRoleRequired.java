package it.sevenbits.practice.googlephoto.web.controller.users;

import java.lang.annotation.*;

/**
 * Annotation for check by one of roles existence
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthRoleRequired {

    /**
     * Returns a role which is required to access the method
     * @return the role name
     */
    String value();

}
