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
        String filePath = "E:/tiger/tiegrWs/jwzh-syfw/web_base/src/main/java";//生成的文件目录
        String parentPackageName = "com.founder";//包名:model,sqlmap及dao的上级包名
        Tools tool=new Tools(parentPackageName,filePath);
        String[] tablenames = {
                "SYFW_CHENGZUXXB",
                "SYFW_CHUZUXXB",
                "SYFW_FWGDB",
                "SYFW_FWGLXXB",
                "SYFW_FWJBXXB",
                "SYFW_FWKZXX_CQRXXB",
                "SYFW_FWRCJC_FJB",
                "SYFW_FWRCJCB",
                "SYFW_FWZPB",
                "SYFW_FWSCB"
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