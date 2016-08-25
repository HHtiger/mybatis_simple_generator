package com.founder.db;

import com.founder.util.connection.ClassPathUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Tools {

    private static Logger log = LoggerFactory.getLogger(Tools.class);

    private String tableName = null;//数据库表名
    private String entityName = null;//生成的业务实体（java bean）名
    private String paramName = null;//生成的业务实体（java bean）参数名
    private String parentPackageName = null;//"com.founder";//包名:model,sqlmap及dao的上级包名
    private String filePath = null;//"E:/tiger/tiegrWs/jwzh-syfw/web_base/src/main/java";//生成的文件目录

    private static Configuration configuration = null;
    private static Map<String, Template> allTemplates = null;
    Map<String,Object> ftlmap = new HashMap<>();

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("utf-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(ClassPathUtil.getClassesPath(Tools.class) + "/ftl/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        allTemplates = new HashMap<>();
        try {
            allTemplates.put("service", configuration.getTemplate("service.ftl"));
            allTemplates.put("dao", configuration.getTemplate("dao.ftl"));
            allTemplates.put("model", configuration.getTemplate("model.ftl"));
            allTemplates.put("sqlmap", configuration.getTemplate("sqlmap.ftl"));
            allTemplates.put("vo", configuration.getTemplate("vo.ftl"));
            allTemplates.put("validator", configuration.getTemplate("validator.ftl"));
            allTemplates.put("controller", configuration.getTemplate("controller.ftl"));
            allTemplates.put("html", configuration.getTemplate("html.ftl"));
            allTemplates.put("aspect", configuration.getTemplate("aspect.ftl"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Tools(String parentPackageName, String filePath) {
        this.filePath = filePath;
        this.parentPackageName = parentPackageName;
        ftlmap.put("parentPackageName",parentPackageName);
    }

    public Map<String,Object> create(String name) {
        tableName = name;
        String[] names = name.split("_");
        name = "";
        for(String n:names){
            n = toUpperCaseFirstOne(n.toLowerCase());
            name += n;
        }
        entityName = name;
        paramName = name;

        List<ColumesObject> columes = DbUtil.INSTANCE.queryColumes(tableName);

        List<ColumesObject> xt_columes = columes.stream()
                .filter(c->c.getColumn_name().toLowerCase().startsWith("xt_"))
                .collect(Collectors.toList());

        columes.removeAll(xt_columes);

        columes.sort(ColumesObject::compareTo);
        xt_columes.sort(ColumesObject::compareTo);

        ColumesObject pk = DbUtil.INSTANCE.getPKColumn(tableName,columes);

        ftlmap.put("entityName",entityName);
        ftlmap.put("tableName",tableName);
        ftlmap.put("paramName",paramName);
        ftlmap.put("pk",pk);
        ftlmap.put("columus",columes);
        ftlmap.put("xt_columes",xt_columes);

        return ftlmap;
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    private void createFile(String dirName,String fileName,Template t) {

        dirName = dirName.replace("\\", "/").trim();

        File dir = new File(dirName);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dirName+fileName);
        if(file.exists()){
            file.delete();
        }

        log.debug("{}",dirName+fileName);

        try {
            Writer w = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            t.process(ftlmap, w);
            w.flush();
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }

    public void doModel () {
        String dir = String.format(
                "%s/java/%s/model/",
                this.filePath,
                this.parentPackageName.replace(".", "/")
        );
        String fileName = String.format("%s.java", this.entityName);
        createFile(dir,fileName,allTemplates.get("model"));
    }

    public void doService () {
        String dir = String.format(
                "%s/java/%s/service/",
                this.filePath,
                this.parentPackageName.replace(".", "/")
        );
        String fileName = String.format("%sService.java", this.entityName);
        createFile(dir,fileName,allTemplates.get("service"));
    }

    public void doDao () {
        String dir = String.format(
                "%s/java/%s/dao/",
                this.filePath,
                this.parentPackageName.replace(".", "/")
        );
        String fileName = String.format("%sDao.java", this.entityName);
        createFile(dir,fileName,allTemplates.get("dao"));
    }

    public void doSqlmap () {
        String dir = String.format(
                "%s/java/%s/sqlmap/",
                this.filePath,
                this.parentPackageName.replace(".", "/")
        );
        String fileName = String.format("%sDao.xml", this.entityName);
        createFile(dir,fileName,allTemplates.get("sqlmap"));
    }

    public void doVo () {
        String dir = String.format(
                "%s/java/%s/vo/",
                this.filePath,
                this.parentPackageName.replace(".", "/")
        );
        String fileName = String.format("%sVO.java", this.entityName);
        createFile(dir,fileName,allTemplates.get("vo"));
    }

    public void doValidator () {
        String dir = String.format(
                "%s/java/%s/validator/",
                this.filePath,
                this.parentPackageName.replace(".", "/")
        );
        String fileName = String.format("%sVOValidator.java", this.entityName);
        createFile(dir,fileName,allTemplates.get("validator"));
    }

    public void doController () {
        String dir = String.format(
                "%s/java/%s/controller/",
                this.filePath,
                this.parentPackageName.replace(".", "/")
        );
        String fileName = String.format("%sController.java", this.entityName);
        createFile(dir,fileName,allTemplates.get("controller"));
    }

    public void doHtml () {
        String dir = String.format(
                "%s/webapp/template/",
                this.filePath,
                this.entityName
        );
        String fileName = String.format("%s.html", this.entityName);
        createFile(dir,fileName,allTemplates.get("html"));
    }

    public void doAspect (String[] insertStService) {
        String dir = String.format(
                "%s/java/%s/aspect/",
                this.filePath,
                this.parentPackageName.replace(".", "/")
        );
        String name = toUpperCaseFirstOne(parentPackageName.split("\\.")[parentPackageName.split("\\.").length-1]) ;
        String fileName = String.format("%sIntercept.java", name);
        ftlmap.put("AspectName", name);
        ftlmap.put("insertStService", insertStService);
        createFile(dir,fileName,allTemplates.get("aspect"));
    }
}
