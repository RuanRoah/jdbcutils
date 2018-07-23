package com.roah.jdbc.defaults.helper.impl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: Add Description
 *
 * @author Roah
 * @since 02/27/2018
 */
public class SQLInjectHelper {
    private static Map mapKeyWord = new HashMap();
    private static Map mapSqlKey = new HashMap();
    private static List keyList = new ArrayList();
    private static Pattern fullPatternSql = null;
    private static Pattern simplePatternSql = null;

    static {
        keyList.add(new SqlKey("sp_", "ｓｐ_", "sp_", true));
        keyList.add(new SqlKey("xp_", "ｘｐ＿", "xp_", true));
        keyList.add(new SqlKey("0x", "０ｘ", "0x", true));
        keyList.add(new SqlKey("--", "－－", "--", true));
        keyList.add(new SqlKey(";", "；", ";", true));
        keyList.add(new SqlKey("exec", "ｅｘｅｃ", "[^0-9a-zA-Z_]exec[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("call", "ｃａｌｌ", "[^0-9a-zA-Z_]call[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("declare", "ｄｅｃｌａｒｅ", "[^0-9a-zA-Z_]declare[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("netuser", "ｎｅｔ ｕｓｅｒ", "[^0-9a-zA-Z_]net\\s+user[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("ascii", "ａｓｃｉｉ", "[^0-9a-zA-Z_]ascii[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("char", "ｃｈａｒ", "[^0-9a-zA-Z_]char[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("cast", "ｃａｓｔ", "[^0-9a-zA-Z_]cast[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("unicode", "ｕｎｉｃｏｄｅ", "[^0-9a-zA-Z_]unicode[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("insert", "ｉｎｓｅｒｔ", "[^0-9a-zA-Z_]insert[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("delete", "ｄｅｌｅｔｅ", "[^0-9a-zA-Z_]delete[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("update", "ｕｐｄａｔｅ", "[^0-9a-zA-Z_]update[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("drop", "ｄｒｏｐ", "[^0-9a-zA-Z_]drop[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("create", "ｃｒｅａｔｅ", "[^0-9a-zA-Z_]create[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("database", "ｄａｔａｂａｓｅ", "[^0-9a-zA-Z_]database[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("master", "ｍａｓｔｅｒ", "[^0-9a-zA-Z_]master[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("truncate", "ｔｒｕｎｃａｔｅ", "[^0-9a-zA-Z_]truncate[^0-9a-zA-Z_]", true));
        keyList.add(new SqlKey("select", "ｓｅｌｅｃｔ", "[^0-9a-zA-Z_]select[^0-9a-zA-Z_]", false));
        keyList.add(new SqlKey("from", "ｆｒｏｍ", "[^0-9a-zA-Z_]from[^0-9a-zA-Z_]", false));
        keyList.add(new SqlKey("where", "ｗｈｅｒｅ", "[^0-9a-zA-Z_]where[^0-9a-zA-Z_]", false));
        keyList.add(new SqlKey("'", "＇", "'", false));
        StringBuffer sbFullKey = new StringBuffer();
        StringBuffer sbSimpleKey = new StringBuffer();
        Iterator iterator = keyList.iterator();

        while(iterator.hasNext()) {
            SqlKey key = (SqlKey)iterator.next();
            if (key.isSerious) {
                sbSimpleKey.append("|");
                sbSimpleKey.append("(?i)");
                sbSimpleKey.append(key.getPattern());
            }

            sbFullKey.append("|");
            sbFullKey.append("(?i)");
            sbFullKey.append(key.getPattern());
            mapKeyWord.put(key.getKey(), key.getReplace());
            mapSqlKey.put(key.getKey(), key);
        }

        sbFullKey.replace(0, 1, "");
        sbSimpleKey.replace(0, 1, "");
        fullPatternSql = Pattern.compile(sbFullKey.toString(), 2);
        simplePatternSql = Pattern.compile(sbSimpleKey.toString(), 2);
    }

    public SQLInjectHelper() {
    }

    public static String filterSql(String sql) {
        return filterSql(sql, fullPatternSql);
    }

    public static String simpleFilterSql(String sql) {
        return filterSql(sql, simplePatternSql);
    }

    private static String filterSql(String sql, Pattern pattern) {
        if (sql != null && !sql.equalsIgnoreCase("")) {
            StringBuilder sbSql = new StringBuilder(sql);
            sbSql.insert(sbSql.length(), ' ');
            sbSql.insert(0, ' ');
            sql = sbSql.toString();
            Matcher matcher = pattern.matcher(sql);

            while(matcher.find()) {
                String key = matcher.group().toLowerCase();
                String regex = null;
                if (!mapSqlKey.containsKey(key)) {
                    if (key.length() > 2) {
                        key = key.trim();
                        regex = "[^0-9a-zA-Z_](?i)" + key + "[^0-9a-zA-Z_]";
                        key = key.replaceAll("\\s", "");
                    }
                } else {
                    regex = key;
                }

                String replace = (String)mapKeyWord.get(key);
                if (regex != null && replace != null) {
                    sql = sql.replaceFirst(regex, replace);
                }
            }
        }

        return sql;
    }

    public static class SqlKey {
        private String key;
        private String pattern;
        private String replace;
        private boolean isSerious;
        private char[] charArray;

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getReplace() {
            return this.replace;
        }

        public void setReplace(String replace) {
            this.replace = replace;
        }

        public void setSerious(boolean isSerious) {
            this.isSerious = isSerious;
        }

        public boolean isSerious() {
            return this.isSerious;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return this.pattern;
        }

        public void setCharArray(char[] charArray) {
            this.charArray = charArray;
        }

        public char[] getCharArray() {
            return this.charArray;
        }

        public SqlKey(String key, String replace, String pattern, boolean isSerious) {
            this.setKey(key);
            this.setReplace(replace);
            this.setPattern(pattern);
            this.setSerious(isSerious);
            this.setCharArray(key.toCharArray());
        }
    }
}