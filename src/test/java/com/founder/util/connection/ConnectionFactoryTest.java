package com.founder.util.connection;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Package: com.founder.util.connection
 * ClassName: ConnectionFactoryTest
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016-04-15
 * Version: 1.0
 */
public class ConnectionFactoryTest {
    private static Logger log = LoggerFactory.getLogger(ConnectionFactoryTest.class);

    @Test
    public void getDriverName() throws Exception {
        ConnectionFactory.INSTANCE.getDriverName();
    }

    @Test
    public void getDatabaseConnection() throws Exception {
    }
}