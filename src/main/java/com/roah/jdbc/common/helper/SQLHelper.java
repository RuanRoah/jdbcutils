package com.roah.jdbc.common.helper;

/**
 * TODO: Add Description
 *
 * @author Roah
 * @since 02/28/2018
 */
public interface SQLHelper {
    <I> String createDelete(Class<?> var1, I var3) throws Exception;

    String createDeleteByCustom(Class<?> var1, String var3, Object[] var4) throws Exception;

    String createGetByCustom(Class<?> var1, String var3, String var4, String var5, boolean var6, Object[] var7) throws Exception;

    String createGetByPage(Class<?> var1, String var3, String var4, int var5, int var6, String var7, Object[] var8) throws Exception;

    <I> String createGetEntity(Class<?> var1, I var3) throws Exception;

    String createUpdateByCustom(Class<?> var1, String var3, String var4, Object[] var5) throws Exception;

    String createUpdateEntity(Object var1) throws Exception;

    String createInsert(Object var1) throws Exception;

    String createGetCount(Class<?> var1, String var3, Object[] var4) throws Exception;

    String createGetCount(Class<?> var1, String var3, String var4, Object[] var5) throws Exception;

    String createGetSum(Class<?> var1, String var3, String var4, Object[] var5) throws Exception;
}
