package com.founder.db;

import com.founder.main.AutoCreator;
import com.founder.yaml.YamlUtil;
import com.founder.yaml.anjian.model.RelationConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

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
    public void testAutoXW() throws Exception {

//        DataRelation dataRelation = YamlUtil.loadYaml("demoDataRelation.yaml",DataRelation.class);

//        String[] xw_dy = {
//            "TB_XW_KYDY",
//            "TB_XW_DSDY",
//            "TB_XW_SSDY",
//            "TB_XW_FXKYDY"
//        };
//
//        String filePath = "E:\\tiger\\tiegrWs\\jwzh-anjian\\src\\main";
//        String parentPackageName = "com.founder.anjian.autoEntry.xw.dsdy";
//        AutoCreator autoCreator = new AutoCreator(filePath,parentPackageName);
//        Stream.of(xw_dy).forEach(autoCreator::createXW);

        String[] TB_XW_JSASJ = {
            "TB_XW_JSASJ"
        };

        String filePath = "E:\\tiger\\tiegrWs\\jwzh-anjian\\src\\main";
        String parentPackageName = "com.founder.anjian.autoEntry.xw.jsasj";
        AutoCreator autoCreator = new AutoCreator(filePath,parentPackageName);
        Stream.of(TB_XW_JSASJ).forEach(autoCreator::createXW);

//        String[] xw_qz = {
//                "TB_XW_KYQZ",
//                "TB_XW_DSQZ",
//                "TB_XW_SSQZ",
////                "TB_XW_FXZASYQZ"
//        };
//
//        filePath = "E:\\tiger\\tiegrWs\\jwzh-anjian\\src\\main";
//        parentPackageName = "com.founder.anjian.autoEntry.xw.dsqz";
//        AutoCreator autoCreator2 = new AutoCreator(filePath,parentPackageName);
//        Stream.of(xw_qz).forEach(autoCreator2::createXW);


    }

    @Test
    public void testAutoST() throws Exception {

        String filePath = "E:\\tiger\\tiegrWs\\jwzh-anjian\\src\\main";
        String parentPackageName = "com.founder.anjian.autoEntry.st";
        AutoCreator autoCreator2 = new AutoCreator(filePath,parentPackageName);

        String[] xw_qz = {
                "TB_ST_SAWP",
                "TB_ST_WP"
        };

        Stream.of(xw_qz).forEach(autoCreator2::createST);

    }

    @Test
    public void testAutoAspect() throws Exception {

        String filePath = "E:\\tiger\\tiegrWs\\jwzh-anjian\\src\\main";
        RelationConfig[] relationConfig = YamlUtil.loadYaml("relationConfig.yaml",RelationConfig[].class);

        Stream.of(relationConfig)
                .forEach( rc -> (new AutoCreator(filePath,rc.getXwPackage())).createAspect(rc.getStClasses()));

    }

}