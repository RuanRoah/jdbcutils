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
public @interface ProcedureName {
    String delete();
    String insert();
    String update();
    String updateByID();
    String load();
    String locaByPage();
}