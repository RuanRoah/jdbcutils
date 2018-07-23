package com.roah.jdbc.defaults.entity;

/**
 * TODO: Add Description
 *
 * @author Roah
 * @since 02/28/2018
 */
public class OutSQL {
    private String sql;

    public OutSQL() {
    }

    public OutSQL(String sql) {
        this.sql = sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return this.sql;
    }
}
