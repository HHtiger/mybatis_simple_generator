package com.founder.yaml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.net.URL;

/**
 * Package: com.founder.yaml
 * ClassName: YamlUtil
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016/7/25
 * Version: 1.0
 */
public class YamlUtil {

    private static Logger log = LoggerFactory.getLogger(YamlUtil.class);

    public static <T> T loadYaml(String filename,Class<T> clazz){

        try {
            /*
             *   https://bitbucket.org/asomov/snakeyaml/wiki/Documentation#markdown-header-javabeans
            */
            Yaml yaml = new Yaml();
            URL url = YamlUtil.class.getClassLoader().getResource("yaml4orm/"+filename);
            if (url != null) {
                return yaml.loadAs(new FileInputStream(url.getFile()),clazz);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
