package com.founder.db;

import org.junit.Test;

import java.util.List;
import java.util.Map;

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
    public void testDoList() throws Exception {
        Tools tool=new Tools();
        DbCon conn = new DbCon();
        List<Map<String, Object>> list = conn.queryColumes("SYFW_FWZPB");
        tool.doList(list);
    }

    @Test
    public void testDb2Java() throws Exception {

    }

    @Test
    public void testDb2SqlMap() throws Exception {

    }

    @Test
    public void testAppendInsert() throws Exception {

    }

    @Test
    public void testAppendUpdate() throws Exception {

    }

    @Test
    public void testAppendDel() throws Exception {

    }

    @Test
    public void testAppendQuery() throws Exception {

    }

    @Test
    public void testAppendQueryPage() throws Exception {

    }

    @Test
    public void testCreateDao() throws Exception {

    }

    @Test
    public void testWriteFile() throws Exception {

    }
}