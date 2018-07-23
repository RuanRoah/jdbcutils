package com.roah.jdbc.common.conf;

import java.util.List;
import java.util.Map;

/**
 * 基础方法集合
 *
 * @author Roah
 * @since 02/27/2018
 */
public interface ICommonDAO {
    Object insert(Object var1) throws Exception;

    void upateEntity(Object var1) throws Exception;

    void updateByCustom(Class<?> var1, String var2, String var3) throws Exception;

    void updateByCustom(Class<?> var1, String var2, String var3, Object... var4) throws Exception;

    <I> void deleteByID(Class<?> var1, I var2) throws Exception;

    void deleteByCustom(Class<?> var1, String var2) throws Exception;

    void deleteByCustom(Class<?> var1, String var2, Object... var3) throws Exception;

    <I> Object get(Class<?> var1, I var2) throws Exception;

    <I> Object get(String column, String condition, Class<I> clazz) throws Exception;

    List getListByCustom(Class<?> var1, String var2, String var3, String var4) throws Exception;

    List getListByCustom(Class<?> var1, String var2, String var3, String var4, Object... var5) throws Exception;

    List getListByCustom(Class<?> var1, String var2, String var3, String var4, boolean var5) throws Exception;

    List getListByCustom(Class<?> var1, String var2, String var3, String var4, boolean var5, Object... var6) throws Exception;

    List getListByPage(Class<?> var1, String var2, String var3, int var4, int var5, String var6) throws Exception;

    List getListByPage(Class<?> var1, String var2, String var3, int var4, int var5, String var6, Object... var7) throws Exception;

    /**
     * @deprecated
     */
    @Deprecated
    List<Map<String, Object>> customSql(String var1, Object... var2) throws Exception;

    /**
     * @deprecated
     */
    @Deprecated
    void customSqlNoReturn(String var1, Object... var2) throws Exception;

    Long getCount(Class<?> var1, String var2) throws Exception;

    int getCount(Class<?> var1, String var2, Object... var3) throws Exception;

    int getCount(Class<?> var1, String var2, String var3) throws Exception;

    int getCount(Class<?> var1, String var2, String var3, Object... var4) throws Exception;

    Number getSum(Class<?> var1, String var2, String var3) throws Exception;

    Number getSum(Class<?> var1, String var2, String var3, Object... var4) throws Exception;

    Object insert(Object var1, int var2) throws Exception;

    void upateEntity(Object var1, int var2) throws Exception;

    void updateByCustom(Class<?> var1, String var2, String var3, int var4) throws Exception;

    void updateByCustom(Class<?> var1, String var2, String var3, int var4, Object... var5) throws Exception;

    <I> void deleteByID(Class<?> var1, I var2, int var3) throws Exception;

    void deleteByCustom(Class<?> var1, String var2, int var3) throws Exception;

    void deleteByCustom(Class<?> var1, String var2, int var3, Object... var4) throws Exception;

    <I> Object get(Class<?> var1, I var2, int var3) throws Exception;


    List getListByCustom(Class<?> var1, String var2, String var3, String var4, int var5) throws Exception;

    List getListByCustom(Class<?> var1, String var2, String var3, String var4, int var5, Object... var6) throws Exception;

    List getListByCustom(Class<?> var1, String var2, String var3, String var4, boolean var5, int var6) throws Exception;

    List getListByCustom(Class<?> var1, String var2, String var3, String var4, boolean var5, int var6, Object... var7) throws Exception;

    List getListByPage(Class<?> var1, String var2, String var3, int var4, int var5, String var6, int var7) throws Exception;

    List getListByPage(Class<?> var1, String var2, String var3, int var4, int var5, String var6, int var7, Object... var8) throws Exception;

    /**
     * @deprecated
     */
    @Deprecated
    List<Map<String, Object>> customSql(String var1, int var2, Object... var3) throws Exception;

    /**
     * @deprecated
     */
    @Deprecated
    void customSqlNoReturn(String var1, int var2, Object... var3) throws Exception;

    int getCount(Class<?> var1, String var2, int var3) throws Exception;

    int getCount(Class<?> var1, String var2, int var3, Object... var4) throws Exception;

    int getCount(Class<?> var1, String var2, String var3, int var4) throws Exception;

    int getCount(Class<?> var1, String var2, String var3, int var4, Object... var5) throws Exception;

    Number getSum(Class<?> var1, String var2, String var3, int var4) throws Exception;

    Number getSum(Class<?> var1, String var2, String var3, int var4, Object... var5) throws Exception;


}