package com.founder.yaml;

import com.founder.yaml.anjian.model.DataRelation;
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
public class DataRelationTest {

    @Test
    public void loadYaml() throws FileNotFoundException {
        DataRelation dataRelation = YamlUtil.loadYaml("demoDataRelation.yaml",DataRelation.class);
        System.out.println(dataRelation);
    }

    public DataRelation create_DataRelation(String root_table_name, String[] relation_names){
        DataRelation root = new DataRelation();
        root.setRoot_table_name(root_table_name);

        for(String name:relation_names){
            DataRelation r = new DataRelation();
            r.setRoot_table_name(name);
            root.getData_from_tables().add(r);
        }

        return root;
    }

    public DataRelation create_DataRelation(String root_table_name,DataRelation[] relations){
        DataRelation root = new DataRelation();
        root.setRoot_table_name(root_table_name);

        for(DataRelation r:relations){
            root.getData_from_tables().add(r);
        }

        return root;
    }

    @Test
    public void dumpYaml() {
        try {

            String dy_root_table_name = "TB_ST_WP_DY";//弹药信息
            String[] dy_global_names = {};
            String[] dy_relation_names = {
                    "TB_XW_KYDY",   /*扣押弹药*/
                    "TB_XW_DSDY",   /*丢失弹药*/
                    "TB_XW_SSDY",   /*损失弹药*/
                    "TB_XW_FXKYDY"  /*发现可疑弹药信息*/
            };

            DataRelation dyxx = create_DataRelation(dy_root_table_name, dy_relation_names);

            String qz_root_table_name = "TB_ST_WP_QZ";/*枪支信息*/

            String[] qz_relation_names = {
                    "TB_XW_KYQZ",   /*扣押枪支*/
                    "TB_XW_DSQZ",   /*丢失枪支*/
                    "TB_XW_SSQZ",   /*损失枪支*/
                    "TB_XW_FXZASYQZ",/*发现作案使用枪支信息*/
                    "TB_XW_FXKYQZ", /*发现可疑枪支信息*/
            };
            DataRelation qzxx = create_DataRelation(qz_root_table_name, qz_relation_names);


            String sa_root_table_name = "TB_ST_SAWP";/*一般物品信息*/
            DataRelation[] sa_relations = {dyxx, qzxx};

            DataRelation saxx = create_DataRelation(sa_root_table_name, sa_relations);

            String wp_root_table_name = "TB_ST_WP";/*物品基本信息*/
            DataRelation[] wp_relations = {saxx};

            DataRelation wpxx = create_DataRelation(wp_root_table_name, wp_relations);

            Yaml yaml = new Yaml(new Constructor(DataRelation.class));
            yaml.dump(wpxx, new FileWriter("src/main/resources/yaml4orm/demoDataRelation.yaml"));
            System.out.println(yaml.dump(wpxx));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}