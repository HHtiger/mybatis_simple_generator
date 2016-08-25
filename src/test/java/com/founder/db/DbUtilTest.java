package com.founder.db;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Package: com.founder.db
 * ClassName: DbUtilTest
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016/8/15
 * Version: 1.0
 */
public class DbUtilTest {

    private static Logger log = LoggerFactory.getLogger(DbUtilTest.class);
    DbUtil dbUtil;

    @Before
    public void before(){
        dbUtil = DbUtil.INSTANCE;
    }

    @Test
    public void testExeute() throws Exception {

    }

    @Test
    public void testGetPKColumn() throws Exception {

    }

    @Test
    public void testQueryColumes() throws Exception {
        List<ColumesObject> lo = dbUtil.queryColumes("TB_ST_WP");
        lo.stream().forEach(i->log.debug(i.getColumn_name()));
    }

    @Test
    public void testCloseConn() throws Exception {

    }
}