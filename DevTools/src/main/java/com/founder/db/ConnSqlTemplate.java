package com.founder.db;

/**
 * Package: com.founder.db
 * ClassName: ConnSqlTemplate
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016-04-15
 * Version: 1.0
 */
public interface ConnSqlTemplate {
    public String getKeyColumnSql(String tableName);

    public String queryColumesSql(String tableName);

}
