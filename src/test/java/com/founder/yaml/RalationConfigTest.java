package com.founder.yaml;

import com.founder.yaml.anjian.model.RelationConfig;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileNotFoundException;
import java.io.FileWriter;

/**
 * Package: com.founder.yaml
 * ClassName: DataRelationTest
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016/7/25
 * Version: 1.0
 */
public class RalationConfigTest {

    @Test
    public void loadYaml() throws FileNotFoundException {
        RelationConfig[] relationConfig = YamlUtil.loadYaml("relationConfig.yaml",RelationConfig[].class);
        System.out.println(relationConfig);
    }

    @Test
    public void dumpYaml() {
        try {

            RelationConfig relationConfig = new RelationConfig();
            relationConfig.setXwPackage("com.founder.anjian.autoEntry.xw.dsqz");
            relationConfig.setStClasses(new String[]{
                    "com.founder.anjian.autoEntry.st.service.TbStSawpService",
                    "com.founder.anjian.autoEntry.st.service.TbStWpService"
            });

            RelationConfig relationConfig2 = new RelationConfig();
            relationConfig2.setXwPackage("com.founder.anjian.autoEntry.xw.dsdy");
            relationConfig2.setStClasses(new String[]{
                    "com.founder.anjian.autoEntry.st.service.TbStSawpService",
                    "com.founder.anjian.autoEntry.st.service.TbStWpService"
            });


            Yaml yaml = new Yaml(new Constructor(RelationConfig[].class));
            yaml.dump(new RelationConfig[]{relationConfig,relationConfig2}, new FileWriter("src/main/resources/yaml4orm/relationConfig.yaml"));
            System.out.println(yaml.dump(relationConfig));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}