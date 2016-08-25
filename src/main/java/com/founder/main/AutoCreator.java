package com.founder.main;

import com.founder.db.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Package: com.founder.main
 * ClassName: AutoCreator
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016/8/8
 * Version: 1.0
 */
public class AutoCreator {

    private static Logger log = LoggerFactory.getLogger(AutoCreator.class);

    Tools tool = null;
    /*
    * filePath 生成的文件目录
    * parentPackageName 包名:model,sqlmap及dao的上级包名
    * */
    public AutoCreator(String filePath, String parentPackageName) {
        tool = new Tools(parentPackageName,filePath);
    }

    public void createXW(String tableName){

        tool.create(tableName);

        tool.doSqlmap();
        tool.doDao();
        tool.doModel();
        tool.doService();
        tool.doVo();
        tool.doValidator();
        tool.doHtml();
        tool.doController();

    }

    public void createAspect(String[] insertStService){
        tool.doAspect(insertStService);
    }

    public void createST(String tableName){

        tool.create(tableName);

        tool.doSqlmap();
        tool.doDao();
        tool.doModel();
        tool.doService();

    }

}
