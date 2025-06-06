package edu.wsiiz.repairshop.auth.security;

import edu.wsiiz.repairshop.auth.domain.user.UserRole;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Secured {
    UserRole[] roles();
}
