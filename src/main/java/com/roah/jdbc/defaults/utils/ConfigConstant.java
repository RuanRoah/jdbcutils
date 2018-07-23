package com.roah.jdbc.defaults.utils;

import java.io.File;

public class ConfigConstant {

    // 系统文件分割符
    private static final String SEP = File.separator;
    // 工程目录
    private static String MAIN_DIR = null;
    // 配置文件目录
    private static final String MAIN_CONFIG = getMainHome() + SEP + "conf" + SEP;

    // 配置文件目录
    private static final String MAIN_CONF = getMainHome() ;


    /**
     * Log4j配置文件
     */
    public static final String CONFIG_LOG4J_PROPERTIES = MAIN_CONFIG + "log4j.properties";

    /**
     * Log4j配置文件动态加载间隔时间
     */
    public static final int INTERVAL_LOG4J_WATCH = 10 * 60 * 1000;


    /**
     * getMainHome 获取工程目录
     *
     * @return
     */
    public static String getMainHome() {
        if (MAIN_DIR == null) {
//			MAIN_DIR = ConfigConstant.class.getClass().getResource("/").getPath();
            MAIN_DIR = ConfigConstant.class.getProtectionDomain().getCodeSource().getLocation().getPath();

            MAIN_DIR = MAIN_DIR.substring(0, MAIN_DIR.length() - 1);
            MAIN_DIR = MAIN_DIR.substring(0, MAIN_DIR.lastIndexOf("/"));
        }
        return MAIN_DIR;
    }
}