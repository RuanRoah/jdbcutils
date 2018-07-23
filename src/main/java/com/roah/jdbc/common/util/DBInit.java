package com.roah.jdbc.common.util;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Roah
 * @since 03/03/2018
 */
public class DBInit {
    private static DataSource dataSource;
    private static DatabaseMetaData dbmb;
    private static Properties p = new Properties();

    public DBInit() {
        super();
    }

    public DBInit(String properties) {

    }


    static Logger logger = LoggerFactory.getLogger(DBInit.class);

    static {
        try {

            p.load(DBInit.class.getClassLoader().getResourceAsStream("dc.properties"));
            dataSource = BasicDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    public static synchronized DBInit init(String prop) {
        if (prop != null) {
            return new DBInit(prop);
        } else
            return null;

    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static QueryRunner getRunner() {
        return new QueryRunner();
    }

    public static QueryRunner getRunner(DataSource ds) {
        return new QueryRunner(ds);
    }

}
