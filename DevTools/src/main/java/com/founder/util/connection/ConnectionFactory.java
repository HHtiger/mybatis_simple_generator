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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public enum ConnectionFactory {

    INSTANCE;
    private Logger log = LoggerFactory.getLogger(ConnectionFactory.class);

    private DruidDataSource dataSource ;

    ConnectionFactory(){
        //process为文件名，切记不要加 .properties， URL是文件里的键名
        ResourceBundle bundle = ResourceBundle.getBundle("resources");
        String jdbc_driver = bundle.getString("jdbc.driverClassName");
        String jdbc_url = bundle.getString("jdbc.url");
        String jdbc_username = bundle.getString("jdbc.username");
        String jdbc_password = bundle.getString("jdbc.password");
        String jdbc_test = bundle.getString("jdbc.test");

        dataSource = new DruidDataSource();
        log.debug("init..");
        dataSource.setDriverClassName(jdbc_driver);
        dataSource.setUrl(jdbc_url);
        dataSource.setUsername(jdbc_username);
        dataSource.setPassword(jdbc_password);
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery(jdbc_test);
    }

    public DbType getDriverName(){
        String driverName = dataSource.getDriverClassName().toLowerCase();
        log.debug("getDriverName:{}",driverName);
        if(driverName.contains("mysql")){
            return DbType.Mysql;
        }else if(driverName.contains("oracle")){
            return DbType.Oracle;
        }else{
            return DbType.Undefined;
        }
    }

    public Connection getDatabaseConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

