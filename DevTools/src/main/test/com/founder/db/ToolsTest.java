package com.founder.db;

import org.junit.Test;

/**
 * Package: com.founder.db
 * ClassName: ToolsTest
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016/4/15
 * Version: 1.0
 */
public class ToolsTest {

    @Test
    public void testCreateService() throws Exception {
        Tools tool=new Tools();
        String[] tablenames = {
                "SYFW_CHENGZUXXB",
                "SYFW_CHUZUXXB",
                "SYFW_FWGDB",
                "SYFW_FWGLXXB",
                "SYFW_FWJBXXB",
                "SYFW_FWKZXX_CQRXXB",
                "SYFW_FWRCJC_FJB",
                "SYFW_FWRCJCB",
                "SYFW_FWZPB"
        };
        for(String tablename : tablenames){
            tool.create(tablename);
            tool.doModel();
            tool.doDao();
            tool.doService();
            tool.doSqlmap();
        }
    }
}