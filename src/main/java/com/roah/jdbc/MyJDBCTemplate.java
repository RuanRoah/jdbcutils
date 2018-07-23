package com.roah.jdbc;

import com.roah.jdbc.common.conf.ICommonDAO;
import com.roah.jdbc.common.util.DBInit;
import com.roah.jdbc.defaults.helper.SQLHelper;
import com.roah.jdbc.defaults.helper.impl.DefaultSQLHelperImpl;
import com.roah.jdbc.defaults.helper.impl.SQLInjectHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * TODO: Add Description
 *
 * @author Roah
 * @since 02/27/2018
 */
public class MyJDBCTemplate implements ICommonDAO {
    Logger logger = LoggerFactory.getLogger(MyJDBCTemplate.class);
    SQLHelper sqlHelper = (SQLHelper) new DefaultSQLHelperImpl();

    private static MyJDBCTemplate myJDBCTemplate;

    public static synchronized MyJDBCTemplate getMyJDBCTemplate() {
        if (myJDBCTemplate == null) {
            myJDBCTemplate = new MyJDBCTemplate();
        }
        return myJDBCTemplate;
    }
    public static synchronized MyJDBCTemplate getMiaCommonTemplate(String conf) {
        if (myJDBCTemplate == null) {
            myJDBCTemplate = new MyJDBCTemplate();
        }
        return myJDBCTemplate;
    }
    static{
        DBInit dbUtilsHelper = DBInit.init("dc.properties");
    }
    QueryRunner qr = DBInit.getRunner(DBInit.getDataSource());

    @Override
    public Object insert(Object T) {
        String sql = null;
        try {
            sql = sqlHelper.createInsert(T);
            qr.update(sql);
        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            return null;
        }
    }

    @Override
    public void upateEntity(Object var1) throws Exception {
        String sql;
        try {
            sql = sqlHelper.createUpdateEntity(var1);
            qr.update(sql);
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    @Override
    public void updateByCustom(Class<?> var1, String var2, String var3) throws Exception {
        String sql = sqlHelper.createUpdateByCustom(var1, var2, var3, null);
        qr.update(sql);
    }

    @Override
    public void updateByCustom(Class<?> classes, String column, String condition, Object... args) throws Exception {
        column = SQLInjectHelper.simpleFilterSql(column);
        String sql = sqlHelper.createUpdateByCustom(classes, column, condition, args);
        qr.update(sql, args);
    }

    @Override
    public <I> void deleteByID(Class<?> var1, I var2) throws Exception {
        String sql = sqlHelper.createDelete(var1, "");
        qr.update(sql);
    }

    @Override
    public void deleteByCustom(Class<?> var1, String var2) throws Exception {
        String sql = sqlHelper.createDelete(var1, var2);
        qr.update(sql);
    }

    @Override
    public void deleteByCustom(Class<?> var1, String var2, Object... var3) throws Exception {
        String sql = sqlHelper.createDelete(var1, var2);
        qr.update(sql, var3);
    }

    @Override
    public <I> Object get(Class<?> var1, I var2) throws Exception {
        String sql = sqlHelper.createGetEntity(var1, var2);


        throw new NotImplementedException();
    }

    @Override
    public List getListByCustom(Class<?> clazz, String columns, String condition, String orderBy) throws Exception {
        String sql = sqlHelper.createGetByCustom(clazz, columns, condition, orderBy, true, null);
        DataSource dataSource =DBInit.getDataSource();
        System.out.println(dataSource);
        List<?> resClass = qr.query(sql, new BeanListHandler<>(clazz));
        return resClass;
    }

    @Override
    public List getListByCustom(Class<?> var1, String var2, String var3, String var4, Object... var5) throws Exception {
        String sql = sqlHelper.createGetByCustom(var1, var2, var3, var4, true, var5);
        List<?> resClass = qr.query(sql, new BeanListHandler<>(var1));
        return resClass;
    }

    @Override
    public List getListByCustom(Class<?> var1, String var2, String var3, String var4, boolean var5) throws Exception {
        String sql = sqlHelper.createGetByCustom(var1, var2, var3, var4, var5, null);
        List<? extends Class> resClass = qr.query(sql, new BeanListHandler<>(var1.getClass()));
        return resClass;
    }

    @Override
    public List getListByCustom(Class<?> var1, String var2, String var3, String var4, boolean var5, Object... var6) throws Exception {
        String sql = sqlHelper.createGetByCustom(var1, var2, var3, var4, var5, null);
        List<? extends Class> resClass = qr.query(sql, new BeanListHandler<>(var1.getClass()));
        return resClass;
    }

    @Override
    public List getListByPage(Class<?> clazz, String condition, String columns, int page, int pageSize, String orderBy, Object[] args) throws Exception {
        columns = SQLInjectHelper.simpleFilterSql(columns);
        String sql = sqlHelper.createGetByPage(clazz, condition, columns, page, pageSize, orderBy, null);
        List<? extends Class> resClass = qr.query(sql, new BeanListHandler<>(clazz.getClass()));
        return resClass;
    }

    @Override
    public List getListByPage(Class<?> clazz, String condition, String columns, int page, int pageSize, String orderBy) throws Exception {
        columns = SQLInjectHelper.simpleFilterSql(columns);
        String sql = sqlHelper.createGetByPage(clazz, condition, columns, page, pageSize, orderBy, null);
        List<? extends Class> resClass = qr.query(sql, new BeanListHandler<>(clazz.getClass()));
        return resClass;
    }

    @Override
    public List<Map<String, Object>> customSql(String var1, Object... var2) throws SQLException {
        return qr.query(var1, new MapListHandler(), var2);
    }

    @Override
    public void customSqlNoReturn(String var1, Object... var2) throws SQLException {
        qr.update(var1);
    }

    @Override
    public Long getCount(Class<?> var1, String var2) throws Exception {
        String sql = sqlHelper.createGetCount(var1, var2, null, null);
        long res = qr.query(sql, new BeanHandler<>(Long.class));
        return res;
    }

    @Override
    public int getCount(Class<?> var1, String var2, Object... var3) throws Exception {
        String sql = sqlHelper.createGetCount(var1, var2, var3);
        int res = qr.query(sql, new BeanHandler<>(Integer.class), var3);
        return res;
    }

    @Override
    public int getCount(Class<?> var1, String var2, String var3) {
        throw new NotImplementedException();
    }

    @Override
    public int getCount(Class<?> var1, String var2, String var3, Object... var4) {
        throw new NotImplementedException();
        //return 0;
    }

    @Override
    public Number getSum(Class<?> clazz, String column, String condition) throws Exception {
        column = SQLInjectHelper.simpleFilterSql(column);
        condition = SQLInjectHelper.simpleFilterSql(condition);
        String sql = sqlHelper.createGetSum(clazz, column, condition, null);
        long res = qr.query(sql, new BeanHandler<>(Long.class));
        return res;
    }

    @Override
    public Number getSum(Class<?> clazz, String column, String condition, Object... var4) throws Exception {
        column = SQLInjectHelper.simpleFilterSql(column);
        condition = SQLInjectHelper.simpleFilterSql(condition);
        String sql = sqlHelper.createGetSum(clazz, column, condition, null);
        long res = qr.query(sql, new BeanHandler<>(Long.class), var4);
        return res;
    }

    @Override
    public Object insert(Object var1, int var2) throws Exception {
        String sql = sqlHelper.createInsert(var1);
        qr.update(sql);
        return null;
    }

    @Override
    public void upateEntity(Object var1, int var2) throws Exception {
        String sql = sqlHelper.createUpdateEntity(var1);
        qr.update(sql);
    }

    @Override
    public void updateByCustom(Class<?> var1, String var2, String var3, int var4) throws Exception {
        String sql = sqlHelper.createUpdateByCustom(var1, var2, var3, null);
        qr.update(sql);

    }

    @Override
    public void updateByCustom(Class<?> var1, String var2, String var3, int var4, Object... var5) throws Exception {
        String sql = sqlHelper.createUpdateByCustom(var1, var2, var3, var5);
        qr.update(sql, var5);
    }

    @Override
    public <I> void deleteByID(Class<?> var1, I var2, int var3) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteByCustom(Class<?> var1, String var2, int var3) throws Exception {
        String sql = sqlHelper.createDeleteByCustom(var1, var2, null);
        qr.update(sql);
    }

    @Override
    public void deleteByCustom(Class<?> var1, String var2, int var3, Object... var4) throws Exception {
        String sql = sqlHelper.createDeleteByCustom(var1, var2, var4);
        qr.update(sql);
    }

    @Override
    public <I> Object get(Class<?> var1, I var2, int var3) throws Exception {
        String sql = sqlHelper.createGetEntity(var1, var2);
        //I i = (I) qr.query(sql, new BeanHandler<>());
        throw new NotImplementedException();
    }


    @Override
    public <I> Object get(String column, String condition, Class<I> clazz) throws Exception {
        I i;
        column = SQLInjectHelper.simpleFilterSql(column);
        String sql = sqlHelper.createGetByCustom(clazz, column, condition, null, true, null);
        i = (I) qr.query(sql, new BeanHandler<>(clazz));
        return i;
    }


    @Override
    public List getListByCustom(Class<?> var1, String var2, String var3, String var4, int var5) throws Exception {
        String sql = sqlHelper.createGetByCustom(var1, var2, var3, var4, true, null);
        List<?> resClass = qr.query(sql, new BeanListHandler<>(var1));
        return resClass;
    }

    @Override
    public List getListByCustom(Class<?> var1, String var2, String var3, String var4, int var5, Object... var6) {
        throw new NotImplementedException();
    }

    @Override
    public List getListByCustom(Class<?> var1, String var2, String var3, String var4, boolean var5, int var6) {
        throw new NotImplementedException();
    }

    @Override
    public List getListByCustom(Class<?> var1, String var2, String var3, String var4, boolean var5, int var6, Object... var7) {
        throw new NotImplementedException();
    }

    @Override
    public List getListByPage(Class<?> clazz, String condition, String columns, int page, int pageSize, String orderBy, int timeOut) throws Exception {
        columns = SQLInjectHelper.simpleFilterSql(columns);
        String sql = sqlHelper.createGetByPage(clazz, condition, columns, page, pageSize, orderBy, null);
        List<? extends Class> resClass = qr.query(sql, new BeanListHandler<>(clazz.getClass()));
        return resClass;
    }

    @Override
    public List getListByPage(Class<?> var1, String var2, String var3, int var4, int var5, String var6, int var7, Object... var8) {
        throw new NotImplementedException();
    }

    @Override
    public List<Map<String, Object>> customSql(String var1, int var2, Object... var3) throws SQLException {
        return qr.query(var1, new MapListHandler());
    }

    @Override
    public void customSqlNoReturn(String var1, int var2, Object... var3) throws SQLException {
        qr.update(var1, var3);
    }

    @Override
    public int getCount(Class<?> clazz, String var2, int var3) throws Exception {
        String sql = sqlHelper.createGetCount(clazz, var2, null);
        int res = qr.query(sql, new BeanHandler<>(Integer.class));
        return res;
    }

    @Override
    public int getCount(Class<?> var1, String var2, int var3, Object... var4) throws Exception {
        String sql = sqlHelper.createGetCount(var1, var2, var4);
        int res = qr.query(sql, new BeanHandler<>(Integer.class));
        return res;
    }

    @Override
    public int getCount(Class<?> var1, String var2, String var3, int var4) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public int getCount(Class<?> var1, String var2, String var3, int var4, Object... var5) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public Number getSum(Class<?> var1, String var2, String var3, int var4) throws Exception {
        String sql = sqlHelper.createGetSum(var1, var2, var3, null);
        int res = qr.query(sql, new BeanHandler<>(Integer.class));
        return res;
    }

    @Override
    public Number getSum(Class<?> var1, String var2, String var3, int var4, Object... var5) throws Exception {
        throw new NotImplementedException();
    }
}
