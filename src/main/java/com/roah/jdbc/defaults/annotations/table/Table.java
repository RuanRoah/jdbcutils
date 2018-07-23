package com.roah.jdbc.defaults.annotations.table;

import java.lang.annotation.*;

/**
 * TODO: Add Description
 *
 * @author Roah
 * @since 02/27/2018
 */
@Documented
//注解的作用域：运行期
@Retention (RetentionPolicy.RUNTIME)
//注解的作用位置,类的上面
@Target (ElementType.TYPE)

public @interface Table {
    String name() default "className";
}
