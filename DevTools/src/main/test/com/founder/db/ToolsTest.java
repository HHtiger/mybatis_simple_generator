package com.founder.db;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Package: com.founder.db
 * ClassName: ToolsTest
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016/4/15
 * Version: 1.0
 */
public class ToolsTest {
    private static Logger log = LoggerFactory.getLogger(ToolsTest.class);

    @Test
    public void testCreateService() throws Exception {
        Tools tool=new Tools();
        String[] tablenames = {
                "SYRK_USER",
        };
        for(String tablename : tablenames){
            tool.create(tablename);
            tool.doModel();
            tool.doDao();
            tool.doService();
            tool.doServiceImpl();
            tool.doSqlmap();
        }
    }
}