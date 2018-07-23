package com.roah.jdbc.defaults.helper.impl;


import com.roah.jdbc.defaults.helper.SQLHelper;
import com.roah.jdbc.defaults.utils.AnnotationUtils;
import com.roah.jdbc.defaults.utils.ClassDBUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Field;
import java.util.List;

/**
 * TODO: Add Description
 *
 * @author Roah
 * @since 02/28/2018
 */
public class DefaultSQLHelperImpl implements SQLHelper {

    @Override
    public <I> String createDelete(Class<?> clazz, I id) throws Exception {
        StringBuffer sbSql = new StringBuffer("DELETE FROM ");
        sbSql.append(AnnotationUtils.getTables(clazz));
        sbSql.append(" WHERE ");
        sbSql.append("`");
        sbSql.append(AnnotationUtils.getTables(clazz));
        sbSql.append("`");
        sbSql.append("=?");
        return sbSql.toString();
    }

    @Override
    public String createDeleteByCustom(Class<?> clazz, String condition, Object[] objects) throws Exception {
        StringBuffer sbSql = new StringBuffer("DELETE FROM ");
        sbSql.append(AnnotationUtils.getTables(clazz));
        sbSql.append(" WHERE ");
        if (condition == null || condition == "") {
            condition = "1=2";
        }

        sbSql.append(condition);
        return sbSql.toString();
    }

    @Override
    public String createGetByCustom(Class<?> clazz, String columns, String condition, String orderBy, boolean cursor, Object[] args) throws Exception {
        StringBuffer sbSql = new StringBuffer("SELECT ");
        if (columns != null && !columns.trim().equals("")) {
            sbSql.append(columns);
        } else {
            sbSql.append("*");
        }

        sbSql.append(" FROM ");
        sbSql.append(AnnotationUtils.getTables(clazz));
        if (condition != null && !condition.trim().equals("")) {
            if (!condition.trim().toLowerCase().matches("^group.*?by.*")) {
                sbSql.append(" WHERE ");
            }

            sbSql.append(condition);
        }

        if (orderBy != null && !orderBy.trim().equals("")) {
            sbSql.append(" ORDER BY ");
            sbSql.append(orderBy);
        }

        return sbSql.toString();
    }

    @Override
    public String createGetByPage(Class<?> clazz, String condition, String columns, int page, int pageSize, String orderBy, Object[] args) throws Exception {
        int offset = pageSize * (page - 1);
        StringBuffer sbSql = new StringBuffer("SELECT ");
        if (columns != null && !columns.trim().equalsIgnoreCase("")) {
            sbSql.append(columns);
        } else {
            sbSql.append("*");
        }

        sbSql.append(" FROM ");
        sbSql.append(AnnotationUtils.getTables(clazz));
        if (condition != null && !condition.equalsIgnoreCase("")) {
            if (!condition.trim().toLowerCase().matches("^group.*?by.*")) {
                sbSql.append(" WHERE ");
            }

            sbSql.append(condition);
        }

        if (orderBy != null && !orderBy.equalsIgnoreCase("")) {
            sbSql.append(" ORDER BY ");
            sbSql.append(orderBy);
        }

        sbSql.append(" LIMIT ");
        sbSql.append(offset);
        sbSql.append(",");
        sbSql.append(pageSize);
        return sbSql.toString();
    }

    @Override
    public <I> String createGetEntity(Class<?> var1, I var3) throws Exception {
        String idColumnName = "";
        List<Field> fieldList = ClassDBUtils.getIdFields(var1);
        StringBuffer sbSql = new StringBuffer("SELECT * ");
        if (fieldList.size() != 1) {
            throw new Exception("无法根据主键ID获取数据：主键不存在 或 有两个以上的主键");
        }else {
            idColumnName = ClassDBUtils.getDBCloumnName(var1, (Field) fieldList.get(0));
            sbSql.append(" FROM ");
            sbSql.append(AnnotationUtils.getTables(var1));
            sbSql.append(" WHERE ");
            sbSql.append("`");
            sbSql.append(idColumnName);
            sbSql.append("`");
            sbSql.append("=?");
        }
        return sbSql.toString();
    }

    @Override
    public String createUpdateByCustom(Class<?> clazz, String updateStatement, String condition, Object[] objects) throws
            Exception {
        StringBuffer sbSql = new StringBuffer("UPDATE ");
        sbSql.append(AnnotationUtils.getTables(clazz));
        sbSql.append(" SET ");
        sbSql.append(updateStatement);
        sbSql.append(" WHERE ");
        if (condition == null || condition.trim().equals("")) {
            condition = "1=2";
        }

        sbSql.append(condition);
        return sbSql.toString();
    }

    @Override
    public String createUpdateEntity(Object var1) throws Exception {
        Class<?> clazz = var1.getClass();
        List<Field> idFields = ClassDBUtils.getIdFields(clazz);
        if (idFields.size() == 0) {
            throw new Exception("无法根据实体更新：主键不存在");
        } else {
            List<Field> listField = ClassDBUtils.getUpdatableFields(clazz);
            if (listField.size() <= 0) {
                throw new Exception("表实体没有字段");
            } else {
                StringBuffer sbSql = new StringBuffer("UPDATE ");
                sbSql.append(AnnotationUtils.getTables(clazz));
                boolean isFirst = true;

                int i;
                for (i = 0; i < listField.size(); ++i) {
                    if (isFirst) {
                        sbSql.append(" SET ");
                    } else {
                        sbSql.append(", ");
                    }

                    sbSql.append("`");
                    sbSql.append(ClassDBUtils.getDBCloumnName(clazz, (Field) listField.get(i)));
                    sbSql.append("`");
                    sbSql.append("=?");
                    isFirst = false;
                }

                sbSql.append(" WHERE ");
                isFirst = true;

                for (i = 0; i < idFields.size(); ++i) {
                    if (!isFirst) {
                        sbSql.append(" AND ");
                    }

                    sbSql.append("`");
                    sbSql.append(ClassDBUtils.getDBCloumnName(clazz, (Field) idFields.get(i)));
                    sbSql.append("`");
                    sbSql.append("=?");
                    isFirst = false;
                }

                return sbSql.toString();
            }
        }
    }

    @Override
    public String createInsert(Object bean) throws Exception {
        Class<?> clazz = bean.getClass();
        StringBuffer sbSql = new StringBuffer("INSERT INTO ");
        sbSql.append(AnnotationUtils.getTables(clazz));
        sbSql.append("(");
        List<Field> listField = ClassDBUtils.getInsertableFields(clazz);
        StringBuilder sbColumn = new StringBuilder();
        StringBuilder sbValue = new StringBuilder();
        boolean isFirst = true;

        for(int i = 0; i < listField.size(); ++i) {
            if (!isFirst) {
                sbColumn.append(", ");
                sbValue.append(", ");
            }

            sbColumn.append("`");
            sbColumn.append(ClassDBUtils.getDBCloumnName(clazz, (Field)listField.get(i)));
            sbColumn.append("`");
            sbValue.append("?");
            isFirst = false;
        }

        sbSql.append(sbColumn);
        sbSql.append(") VALUES (");
        sbSql.append(sbValue);
        sbSql.append(")");
        return sbSql.toString();
    }


    @Override
    public String createGetCount(Class<?> clazz, String condition, Object[] args) throws Exception {
        StringBuffer sbSql = new StringBuffer("SELECT COUNT(0) FROM ");
        sbSql.append(AnnotationUtils.getTables(clazz));
        if (condition != null && !condition.trim().equals("")) {
            if (!condition.trim().toLowerCase().matches("^group.*?by.*")) {
                sbSql.append(" WHERE ");
            }
            sbSql.append(condition);
        }
        return sbSql.toString();
    }

    @Override
    public String createGetCount(Class<?> clazz, String condition, String var4, Object[] var5) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public String createGetSum(Class<?> clazz, String column, String condition, Object[] objects) throws
            Exception {
        StringBuffer sbSql = new StringBuffer("SELECT sum(");
        sbSql.append(column).append(") FROM ");
        sbSql.append(AnnotationUtils.getTables(clazz));
        if (condition != null && !condition.trim().equals("")) {
            if (!condition.trim().toLowerCase().matches("^group.*?by.*")) {
                sbSql.append(" WHERE ");
            }

            sbSql.append(condition);
        }

        return sbSql.toString();
    }
}
