package com.roah.jdbc.defaults.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 数据库表名与类映射工具相关工具
 *
 * @author Roah
 * @since 03/01/2018
 */
public class ClassDBUtils {
    private static Map<Class<?>, ClassInfoUtil> classInfoCache = new HashMap();

    public static String getDBCloumnName(Class<?> clazz, Field f) {
        ClassInfoUtil classInfoUtil = getClassInfo(clazz);
        return (String)classInfoUtil.getMapDBColumnName().get(f.getName());
    }
    //获取类的详情类
    private static ClassInfoUtil getClassInfo(Class<?> clazz) {
        ClassInfoUtil classinfoutil = (ClassInfoUtil) classInfoCache.get(clazz);
        if (classinfoutil == null) {
            classinfoutil = new ClassInfoUtil(clazz);
            classInfoCache.put(clazz, classinfoutil);
        }
        return classinfoutil;
    }
    //获得插入的字段
    public static List<Field> getInsertableFields(Class<?> clazz) {
        ClassInfoUtil classinfoutil = getClassInfo(clazz);
        Collection<Field> coll = classinfoutil.getMapInsertableField().values();
        List<Field> fields = new ArrayList();
        Iterator var5 = coll.iterator();

        while (var5.hasNext()) {
            Field f = (Field) var5.next();
            fields.add(f);
        }

        return fields;
    }
    //获取Update的字段类映射
    public static List<Field> getUpdatableFields(Class<?> clazz) {
        ClassInfoUtil classinfoutil = getClassInfo(clazz);
        Collection<Field> coll = classinfoutil.getMapUpdatableField().values();
        List<Field> fields = new ArrayList();
        Iterator var5 = coll.iterator();

        while (var5.hasNext()) {
            Field f = (Field) var5.next();
            fields.add(f);
        }

        return fields;
    }
    //获取类名
    public static Method getGetterMethod(Class<?> clazz, Field field) {
        ClassInfoUtil classInfoUtil = getClassInfo(clazz);
        Map<String, Method> mapGetterMethod = classInfoUtil.getMapGetMethod();
        return (Method)mapGetterMethod.get(field.getName());
    }
    //获取ID
    public static List<Field> getIdFields(Class<?> clazz) {
        ClassInfoUtil classInfoUtil = getClassInfo(clazz);
        Collection<Field> coll = classInfoUtil.getMapIDField().values();
        List<Field> fields = new ArrayList();
        Iterator var5 = coll.iterator();

        while(var5.hasNext()) {
            Field f = (Field)var5.next();
            fields.add(f);
        }

        return fields;
    }
}