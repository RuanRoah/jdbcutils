package com.roah.jdbc.defaults.utils;


import com.google.common.base.Strings;
import com.roah.jdbc.defaults.annotations.common.Column;
import com.roah.jdbc.defaults.annotations.common.Id;
import com.roah.jdbc.defaults.annotations.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.ArrayList;

/**
 * TODO: Add Description
 *
 * @author Roah
 * @since 02/28/2018
 */
public class AnnotationUtils {
    Logger logger = LoggerFactory.getLogger(AnnotationUtils.class);

    public static String getTables(Class<?> clazz) {
        Table t = clazz.getAnnotation(Table.class);
        String tableName = t.name();
        return tableName;
    }

    public static String getId(Class<?> entityClass) {
        String primaryKey = null;
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                if (field.isAnnotationPresent(Column.class)) ;
                Column column = field.getAnnotation(Column.class);
                return column.name();
            }
        }
        return primaryKey;
    }

    public static ArrayList<String> columns(Class<?> entityClass) {
        ArrayList<String> res = new ArrayList<>();
        Method[] methods = entityClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Column.class)) {
                Column column = method.getAnnotation(Column.class);
                res.add(column.name());

            }
        }
        return res;
    }

    public static String getQueryString(Class<?> entityClass) {
        Method[] methods = entityClass.getDeclaredMethods();
        Field[] fields = entityClass.getDeclaredFields();
        StringBuffer sql = new StringBuffer();
        int i = 0;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                if (!Strings.isNullOrEmpty(column.getFuncName())) {
                    sql.append(column.getFuncName()).append(" AS ").append(column.name());
                } else {
                    sql.append(column.name());
                }
                i++;
                if (i < fields.length) {
                    sql.append(",");
                }

            }
        }
        return sql.toString();
    }

    public String getWhereClause(Object o) throws IllegalAccessException {
        StringBuffer sql = new StringBuffer();
        Field[] fields = o.getClass().getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            if(field.get(o).equals(null)){

            }
        }
        return null;
    }

}
