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
public class Oracle_ConnSqlTemplate implements ConnSqlTemplate {

    public String getKeyColumnSql(String tableName) {
        return SQLUtils.formatOracle("SELECT " +
                " cu.column_name " +
                " FROM " +
                " user_cons_columns cu," +
                " user_constraints au" +
                " WHERE" +
                " cu.constraint_name = au.constraint_name" +
                " AND au.constraint_type = 'P'" +
                " AND au.table_name = '" + tableName + "';",
                SQLUtils.DEFAULT_FORMAT_OPTION);
    }

    public String queryColumesSql(String tableName) {
        return SQLUtils.formatOracle("" +
                "select a.COLUMN_NAME, a.DATA_TYPE, b.COMMENTS, a.DATA_PRECISION, a.DATA_SCALE, a.NULLABLE, a.DATA_LENGTH " +
                "from user_tab_columns a left join user_col_comments b on( "
                + "b.TABLE_NAME=a.TABLE_NAME and b.COLUMN_NAME=a.COLUMN_NAME) " +
                "where a.TABLE_NAME='" + tableName + "'",
                SQLUtils.DEFAULT_FORMAT_OPTION);

    }

}
