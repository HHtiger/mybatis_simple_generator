package com.founder.db;

import com.alibaba.druid.sql.SQLUtils;

/**
 * Package: com.founder.db
 * ClassName: ConnSqlTemplate
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016-04-15
 * Version: 1.0
 */
public class Mysql_ConnSqlTemplate implements ConnSqlTemplate{

    public String getKeyColumnSql(String tableName){
        return SQLUtils.formatMySql(
                "SELECT k.COLUMN_NAME\n" +
                "\n" +
                " FROM information_schema.table_constraints t\n" +
                "\n" +
                " JOIN information_schema.key_column_usage k\n" +
                "\n" +
                " USING (constraint_name,table_schema,table_name)\n" +
                "\n" +
                " WHERE t.constraint_type='PRIMARY KEY'\n" +
                "\n" +
                "  AND t.table_schema='bjxt'\n" +
                "\n" +
                "  AND t.table_name='"+tableName+"'",
                SQLUtils.DEFAULT_FORMAT_OPTION);
    }

    public String queryColumesSql(String tableName){
        return SQLUtils.formatMySql(
                "select  COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT AS COMMENTS from information_schema.COLUMNS\n" +
                "\n" +
                "where TABLE_SCHEMA='bjxt'\n" +
                "\n" +
                "and TABLE_NAME='"+tableName+"'",
                SQLUtils.DEFAULT_FORMAT_OPTION);
    }

}
