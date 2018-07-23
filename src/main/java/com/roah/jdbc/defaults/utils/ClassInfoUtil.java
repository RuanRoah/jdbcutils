package com.roah.jdbc.defaults.utils;

import com.roah.jdbc.defaults.annotations.common.Column;
import com.roah.jdbc.defaults.annotations.common.Id;
import com.roah.jdbc.defaults.annotations.common.ProcedureName;
import com.roah.jdbc.defaults.annotations.table.NotDbColumn;
import com.roah.jdbc.defaults.annotations.table.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * 反射和解析类
 *
 * @author Roah
 * @since 03/01/2018
 */
public class ClassInfoUtil {
    private Map<String, Field> mapIDField;
    private Map<String, Field> mapAllDBField;
    private Map<String, Field> mapInsertableField;
    private Map<String, Field> mapUpdatableField;
    private Map<String, Field> mapIdentityField;
    private Map<String, String> mapDBColumnName;
    private Map<String, Method> mapSetMethod;
    private Map<String, Method> mapGetMethod;
    private String tableName;
    private ProcedureName procdure;

    public ClassInfoUtil(Class<?> clazz) {
        this.setTableName(this.getTableName(clazz));
        this.setMapIDField(this.getIdFields(clazz));
        this.setMapAllDBField(this.getAllDBFields(clazz));
        this.setMapInsertableField(this.getInsertableFields(clazz));
        this.setMapUpdatableField(this.getUpdatableFields(clazz));
        this.setMapDBColumnName(this.getCloumnName(clazz));
        this.setMapSetMethod(this.getSetterMethod(clazz));
        this.setMapGetMethod(this.getGetterMethod(clazz));
        this.setProcdure(this.getProc(clazz));
        this.setMapIdentityField(this.getIdentityFields(clazz));
    }

    private String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = (Table)clazz.getAnnotation(Table.class);
            if (!table.name().equalsIgnoreCase("className")) {
                return table.name();
            }
        }

        String name = clazz.getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }

    private Map<String, Field> getInsertableFields(Class<?> clazz) {
        Map<String, Field> mapFfields = new HashMap();
        Field[] fields = clazz.getDeclaredFields();
        Field[] var7 = fields;
        int var6 = fields.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            Field f = var7[var5];
            if (!f.isAnnotationPresent(NotDbColumn.class)) {
                if (f.isAnnotationPresent(Id.class)) {
                    Id id = (Id)f.getAnnotation(Id.class);
                    if (id.insertable()) {
                        mapFfields.put(f.getName(), f);
                    }
                } else {
                    mapFfields.put(f.getName(), f);
                }
            }
        }

        return mapFfields;
    }

    private Map<String, Field> getAllDBFields(Class<?> clazz) {
        Map<String, Field> mapFfields = new HashMap();
        Field[] fields = clazz.getDeclaredFields();
        Field[] var7 = fields;
        int var6 = fields.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            Field f = var7[var5];
            if (!f.isAnnotationPresent(NotDbColumn.class)) {
                mapFfields.put(f.getName(), f);
            }
        }

        return mapFfields;
    }

    private Map<String, Field> getIdFields(Class<?> clazz) {
        Map<String, Field> mapFfields = new HashMap();
        Field[] fields = clazz.getDeclaredFields();
        Field[] var7 = fields;
        int var6 = fields.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            Field f = var7[var5];
            if (f.isAnnotationPresent(Id.class)) {
                mapFfields.put(f.getName(), f);
            }
        }

        return mapFfields;
    }

    private Map<String, Field> getUpdatableFields(Class<?> clazz) {
        Map<String, Field> mapFfields = new HashMap();
        Field[] fields = clazz.getDeclaredFields();
        Field[] var7 = fields;
        int var6 = fields.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            Field f = var7[var5];
            if (!f.isAnnotationPresent(NotDbColumn.class)) {
                if (f.isAnnotationPresent(Id.class)) {
                    Id id = (Id)f.getAnnotation(Id.class);
                    if (id.updatable()) {
                        mapFfields.put(f.getName(), f);
                    }
                } else {
                    mapFfields.put(f.getName(), f);
                }
            }
        }

        return mapFfields;
    }

    private Map<String, String> getCloumnName(Class<?> clazz) {
        Map<String, String> mapNames = new HashMap();
        Collection<Field> fList = this.mapAllDBField.values();
        Iterator var5 = fList.iterator();

        while(var5.hasNext()) {
            Field f = (Field)var5.next();
            if (f.isAnnotationPresent(Column.class)) {
                Column column = (Column)f.getAnnotation(Column.class);
                if (!column.name().equalsIgnoreCase("fieldName")) {
                    mapNames.put(f.getName(), column.name());
                } else {
                    mapNames.put(f.getName(), f.getName());
                }
            } else {
                mapNames.put(f.getName(), f.getName());
            }
        }

        return mapNames;
    }

    private Map<String, Method> getSetterMethod(Class<?> clazz) {
        Map<String, Method> mapMethod = new HashMap();
        Collection<Field> fList = this.mapAllDBField.values();
        Iterator var5 = fList.iterator();

        while(var5.hasNext()) {
            Field f = (Field)var5.next();
            String setFunName = "";
            if (f.isAnnotationPresent(Column.class)) {
                Column column = (Column)f.getAnnotation(Column.class);
                if (!column.setFuncName().equalsIgnoreCase("setField")) {
                    setFunName = column.setFuncName();
                } else {
                    setFunName = "set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                }
            } else {
                setFunName = "set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
            }

            Method setterMethod = null;
            Method[] var11;
            int var10 = (var11 = clazz.getMethods()).length;

            for(int var9 = 0; var9 < var10; ++var9) {
                Method m = var11[var9];
                if (m.getName().equals(setFunName)) {
                    setterMethod = m;
                }
            }

            mapMethod.put(f.getName(), setterMethod);
        }

        return mapMethod;
    }

    private Map<String, Method> getGetterMethod(Class<?> clazz) {
        Map<String, Method> mapMethod = new HashMap();
        Collection<Field> fList = this.mapAllDBField.values();
        Iterator var5 = fList.iterator();

        while(var5.hasNext()) {
            Field f = (Field)var5.next();
            String getFunName = "";
            if (f.isAnnotationPresent(Column.class)) {
                Column column = (Column)f.getAnnotation(Column.class);
                if (!column.getFuncName().equalsIgnoreCase("getField")) {
                    getFunName = column.getFuncName();
                } else {
                    getFunName = "get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                }
            } else {
                getFunName = "get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
            }

            Method getterMethod = null;
            Method[] var11;
            int var10 = (var11 = clazz.getMethods()).length;

            for(int var9 = 0; var9 < var10; ++var9) {
                Method m = var11[var9];
                if (m.getName().equals(getFunName)) {
                    getterMethod = m;
                }
            }

            mapMethod.put(f.getName(), getterMethod);
        }

        return mapMethod;
    }

    private ProcedureName getProc(Class<?> clazz) {
        return (ProcedureName)clazz.getAnnotation(ProcedureName.class);
    }

    private Map<String, Field> getIdentityFields(Class<?> clazz) {
        Map<String, Field> mapField = new HashMap();
        Field[] fields = clazz.getDeclaredFields();
        Field[] var7 = fields;
        int var6 = fields.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            Field f = var7[var5];
            if (f.isAnnotationPresent(Id.class)) {
                Id id = (Id)f.getAnnotation(Id.class);
                if (!id.insertable() && !id.updatable()) {
                    mapField.put(f.getName(), f);
                }
            }
        }

        return mapField;
    }

    public Map<String, Field> getMapIDField() {
        return this.mapIDField;
    }

    public void setMapIDField(Map<String, Field> mapIDField) {
        this.mapIDField = mapIDField;
    }

    public Map<String, Field> getMapAllDBField() {
        return this.mapAllDBField;
    }

    public void setMapAllDBField(Map<String, Field> mapAllDBField) {
        this.mapAllDBField = mapAllDBField;
    }

    public Map<String, Field> getMapInsertableField() {
        return this.mapInsertableField;
    }

    public void setMapInsertableField(Map<String, Field> mapInsertableField) {
        this.mapInsertableField = mapInsertableField;
    }

    public Map<String, Field> getMapUpdatableField() {
        return this.mapUpdatableField;
    }

    public void setMapUpdatableField(Map<String, Field> mapUpdatableField) {
        this.mapUpdatableField = mapUpdatableField;
    }

    public Map<String, String> getMapDBColumnName() {
        return this.mapDBColumnName;
    }

    public void setMapDBColumnName(Map<String, String> mapDBColumnName) {
        this.mapDBColumnName = mapDBColumnName;
    }

    public Map<String, Method> getMapSetMethod() {
        return this.mapSetMethod;
    }

    public void setMapSetMethod(Map<String, Method> mapSetMethod) {
        this.mapSetMethod = mapSetMethod;
    }

    public Map<String, Method> getMapGetMethod() {
        return this.mapGetMethod;
    }

    public void setMapGetMethod(Map<String, Method> mapGetMethod) {
        this.mapGetMethod = mapGetMethod;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setProcdure(ProcedureName procdure) {
        this.procdure = procdure;
    }

    public ProcedureName getProcdure() {
        return this.procdure;
    }

    public void setMapIdentityField(Map<String, Field> mapIdentityField) {
        this.mapIdentityField = mapIdentityField;
    }

    public Map<String, Field> getMapIdentityField() {
        return this.mapIdentityField;
    }
}

