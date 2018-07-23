package com.roah.jdbc.defaults.annotations.common;

import java.lang.annotation.*;

/**
 * TODO: Add Description
 *
 * @author Roah
 * @since 02/28/2018
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    String name() default "fieldName";

    String setFuncName() default "setField";

    String getFuncName() default "";

    String note() default "";

    boolean defaultDBValue() default false;
}

