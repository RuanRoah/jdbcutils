package com.roah.jdbc.defaults.helper.impl;

import com.roah.jdbc.defaults.entity.QueryModel;

import java.util.Map;

/**
 * TODO: Add Description
 *
 * @author Roah
 * @since 03/31/2018
 */

public class SQLExtentionHelper extends DefaultSQLHelperImpl {
    public static String mapToSelectSQL(Map<String, QueryModel> maps) {
        StringBuffer sql = new StringBuffer();
        int i = 0;
        for (Map.Entry<String, QueryModel> map : maps.entrySet()) {
            sql.append(map.getKey()).append(" AS ").append(map.getValue().column);
            i++;
            if (i < maps.size()) {
                sql.append(",");
            }

        }
        return "";
    }


}
