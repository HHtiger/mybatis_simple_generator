package com.founder.db;

import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Package: com.founder.db
 * ClassName: DbConTest
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016/4/14
 * Version: 1.0
 */
public class DbConTest extends TestCase {
    private static Logger log = LoggerFactory.getLogger(DbConTest.class);
    @Override
    public TestResult run() {
        return super.run();
    }

    @Test
    public void testGetCon() throws Exception {
        DbCon d = new DbCon();
        d.getKeyColumn("SYFW_FWZPB");
    }

    @Test
    public void testGetKeyColumn() throws Exception {

    }

    @Test
    public void testQuery() throws Exception {

    }
}