package com.founder.db;

import com.founder.util.connection.ClassPathUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tools {

    private static Logger log = LoggerFactory.getLogger(Tools.class);

    private String tableName = null;//数据库表名
    private String entityName = null;//生成的业务实体（java bean）名
    private String paramName = null;//生成的业务实体（java bean）参数名
    private String parentPackageName = null;//"com.founder";//包名:model,sqlmap及dao的上级包名
    private String filePath = null;//"E:/tiger/tiegrWs/jwzh-syfw/web_base/src/main/java";//生成的文件目录
//    public String filePath = "I:/MyGit/jwzh-syfw/web_base/src/main/java";//生成的文件目录

    private String pk = null;

    public static Map<String, String> db2JavaMap;//映射为public可配置

    private static Configuration configuration = null;
    private static Map<String, Template> allTemplates = null;
    Map<String,Object> ftlmap = new HashMap<>();

    static {

        db2JavaMap = new HashMap<>();
        db2JavaMap.put("VARCHAR2", "String");
        db2JavaMap.put("varchar", "String");
        db2JavaMap.put("int", "int");
        db2JavaMap.put("DATE", "java.sql.Date");
        db2JavaMap.put("NUMBER", "int");
        db2JavaMap.put("BLOB", "byte[]");

        configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("utf-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(ClassPathUtil.getClassesPath(Tools.class) + "/ftl/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        allTemplates = new HashMap<>();    // Java 7 钻石语法
        try {
            allTemplates.put("service", configuration.getTemplate("service.ftl"));
            allTemplates.put("serviceImpl", configuration.getTemplate("serviceImpl.ftl"));
            allTemplates.put("dao", configuration.getTemplate("dao.ftl"));
            allTemplates.put("model", configuration.getTemplate("model.ftl"));
            allTemplates.put("sqlmap", configuration.getTemplate("sqlmap.ftl"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Tools(String parentPackageName, String filePath) {
        this.filePath = filePath;
        this.parentPackageName = parentPackageName;
    }

    public void create(String name) {
        tableName = name;
        String[] names = name.split("_");
        name = "";
        for(String n:names){
            n = toUpperCaseFirstOne(n.toLowerCase());
            name += n;
        }
        entityName = name;
        paramName = name;
        pk = DbUtil.INSTANCE.getOracleKeyColumn(tableName);

        ftlmap.put("parentPackageName",parentPackageName);
        ftlmap.put("entityName",entityName);
        ftlmap.put("tableName",tableName);
        ftlmap.put("paramName",paramName);
        ftlmap.put("pk",pk);
        List<Map<String, Object>> columes = DbUtil.INSTANCE.queryColumes(tableName);
        ftlmap.put("columus",columes);
        ftlmap.put("db2JavaMap",db2JavaMap);
    }

    //首字母转小写
    private static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    //首字母转大写
    private static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    private String createFile(Map<?, ?> dataMap, String packageName) {

        String path = this.filePath + "/" + parentPackageName.replace(".", "/") + "/" + packageName;

        String fileName;

        if(packageName.equals("sqlmap")){
            fileName = entityName  + ".xml";
        }else if(packageName.equals("model")){
            fileName = entityName + ".java";
        }else if(packageName.equals("serviceImpl")){
            fileName = entityName + toUpperCaseFirstOne(packageName) + ".java";
            path = path.replace("serviceImpl","service/impl");
        }else{
            fileName = entityName + toUpperCaseFirstOne(packageName) + ".java";
        }

        path = path.replace("//", "/").trim();

        File dir = new File(path);
        log.info(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(path + "/" + fileName);

        Template t = allTemplates.get(packageName);
        try {
            Writer w = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            t.process(dataMap, w);
            w.flush();
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        return path;
    }

    public void doModel () {
        createFile(ftlmap,"model");
    }

    public void doService () {
        createFile(ftlmap,"service");
    }

    public void doServiceImpl () {
        createFile(ftlmap,"serviceImpl");
    }

    public void doDao () {
        createFile(ftlmap,"dao");
    }

    public void doSqlmap () {
        createFile(ftlmap,"sqlmap");
    }

}
