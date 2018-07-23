package com.roah.jdbc.defaults.entity;

/**
 * TODO: Add Description
 *
 * @author Roah
 * @since 03/31/2018
 */
public class QueryModel {
    public String title;
    public String column;
    public String detail;

    public QueryModel(String detail,String title, String column) {
        this.detail = detail;
        this.title = title;
        this.column = column;
    }
    public QueryModel(String title, String column) {
        this.title = title;
        this.column = column;
    }

    public QueryModel() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
