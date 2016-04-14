package com.founder.util.connection;
/**
 * Package: com.founder.util.connection
 * ClassName: ConnectionFactory
 * Author: he_hu@founder.com.cn
 * Description: 数据库log日志连接
 * CreateDate: 2016-03-30
 * Version: 1.0
 */

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionFactory {
    private static interface Singleton {
        final ConnectionFactory INSTANCE = new ConnectionFactory();
    }

    private final DruidDataSource dataSource ;

    private ConnectionFactory(){
        //process为文件名，切记不要加 .properties， URL是文件里的键名
        ResourceBundle bundle = ResourceBundle.getBundle("resources");
        String jdbc_driver = bundle.getString("jdbc.driverClassName");
        String jdbc_url = bundle.getString("jdbc.url");
        String jdbc_username = bundle.getString("jdbc.username");
        String jdbc_password = bundle.getString("jdbc.password");
        String jdbc_test = bundle.getString("jdbc.test");

        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbc_driver);
        dataSource.setUrl(jdbc_url);
        dataSource.setUsername(jdbc_username);
        dataSource.setPassword(jdbc_password);
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery(jdbc_test);
    }

    public static Connection getDatabaseConnection() throws SQLException {
        return Singleton.INSTANCE.dataSource.getConnection();
    }
}
