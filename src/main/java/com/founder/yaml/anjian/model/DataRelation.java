package com.founder.yaml.anjian.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Package: PACKAGE_NAME
 * ClassName: DataRelation
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016/7/22
 * Version: 1.0
 */
public class DataRelation implements Serializable {

    private static final long serialVersionUID = 3316327648142629009L;

    private String root_table_name;

    private List<DataRelation> data_from_tables = new ArrayList<>();

    public String getRoot_table_name() {
        return root_table_name;
    }

    public void setRoot_table_name(String root_table_name) {
        this.root_table_name = root_table_name;
    }

    public List<DataRelation> getData_from_tables() {
        return data_from_tables;
    }

    public void setData_from_tables(List<DataRelation> data_from_tables) {
        this.data_from_tables = data_from_tables;
    }


    @Override
    public String toString() {
        return "DataRelation{" +
                "root_table_name='" + root_table_name + '\'' +
                ", data_from_tables=" + data_from_tables +
                '}';
    }
}
