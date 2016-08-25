package com.founder.util.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

/**
 * Package: com.founder.utils
 * ClassName: ClassPathUtil
 * Author: he_hu@founder.com.cn
 * Description:根据当前工具类定位项目路径。用户tomcat，webspere等不同环境的项目根路径获取。
 * CreateDate: 2016/3/15
 * Version: 1.0
 */
public class ClassPathUtil {
    /**
     * 取得当前类所在的文件
     * @param clazz
     * @return
     */
    private static Logger log = LoggerFactory.getLogger(ClassPathUtil.class);

    private static File getClassFile(Class clazz){
        URL path = clazz.getResource(clazz.getName().substring(
                clazz.getName().lastIndexOf(".")+1)+".classs");
        if(path == null){
            String name = clazz.getName().replaceAll("[.]", "/");
            path = clazz.getResource("/"+name+".class");
        }
        return new File(path.getFile());
    }
    /**
     * 得到当前类的路径
     * @param clazz
     * @return
     */
    private static String getClassFilePath(Class clazz){
        try{
            return java.net.URLDecoder.decode(getClassFile(clazz).getAbsolutePath(),"UTF-8").replace("\\","/");
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 取得当前类所在的ClassPath目录，比如tomcat下的classes路径
     * @param clazz
     * @return
     */
    private static File getClassPathFile(Class clazz){
        File file = getClassFile(clazz);
        for(int i=0,count = clazz.getName().split("[.]").length; i<count; i++)
            file = file.getParentFile();
        if(file.getName().toUpperCase().endsWith(".JAR!")){
            file = file.getParentFile();
        }
        return file;
    }
    /**
     * 取得当前类所在的ClassPath路径
     * @param clazz
     * @return
     */
    private static String getClassPath(Class clazz){
        try{
            return java.net.URLDecoder.decode(getClassPathFile(clazz).getAbsolutePath(),"UTF-8");
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "";
        }
    }

    public static String getRootPath(Class clazz){
        String webRootPath = getClassFilePath(clazz);
        try{
            webRootPath = webRootPath.substring(0,webRootPath.lastIndexOf("/classes/"));
        }catch (Exception e){
            log.error("make sure the class: " + clazz.getName() + " is your project class!");
            throw new RuntimeException(e);
        }
        return webRootPath;
    }

    public static String getClassesPath(Class clazz){
        return getRootPath(clazz) + "/classes/";
    }
}