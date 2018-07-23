package com.roah.jdbc.defaults.annotations.common;

import java.lang.annotation.*;

/**
 * TODO: Add Description
 *
 * @author Roah
 * @since 02/27/2018
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {
    boolean updatable() default false;
    boolean insertable() default false;
}
