package com.founder.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Package: com.founder.db
 * ClassName: ColumesObject
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016/7/27
 * Version: 1.0
 */
public class ColumesObject implements Comparable, Cloneable {

    private static Logger log = LoggerFactory.getLogger(ColumesObject.class);

    public static Map<String, String> db2jdbc;//映射为public可配置
    public static Map<String, String> db2Java;//映射为public可配置

    static {
        db2jdbc = new HashMap<>();
        db2jdbc.put("VARCHAR2", "VARCHAR");
        db2jdbc.put("DATE", "DATE");
        db2jdbc.put("TIMESTAMP", "TIMESTAMP");
        db2jdbc.put("CLOB", "CLOB");
    }

    static {
        db2Java = new HashMap<>();
        db2Java.put("VARCHAR2", "java.lang.String");
        db2Java.put("varchar", "java.lang.String");
        db2Java.put("DATE", "java.util.Date");
        db2Java.put("TIMESTAMP", "java.util.Date");
        db2Java.put("BLOB", "byte[]");
        db2Java.put("CLOB", "java.lang.String");
    }

    public String[] get0Jdbc1java() {

        if (data_precision != 0) {
            if (data_scale != 0) {
                return new String[]{"DOUBLE", "java.lang.Double"};
            } else {
                if (data_precision < 32) {
                    return new String[]{"INTEGER", "java.lang.Integer"};
                } else if (data_precision >= 32 && data_precision < 64) {
                    return new String[]{"BIGINT", "java.lang.Long"};
                } else {
                    return new String[]{"NUMERIC", "java.lang.Long"};
                }
            }
        } else {
            return new String[]{db2jdbc.get(data_type), db2Java.get(data_type)};
        }
    }

    public String getJavaType() {
        return get0Jdbc1java()[1];
    }

    public String getJdbcType() {
        return get0Jdbc1java()[0];
    }

    public String getDictUrl() {
        return "http://www.jwzh.com:9016/jwzh-main/common/dict/" + transformComment.getDictName() + ".js";
    }


    /*
    * 数据库字段名
    * */
    private String column_name = "";
    /*
    * 数据库数据类型
    * */
    private String data_type = "";
    /*
    * 注释
    * */
    private String comments = "";
    /*
    *
    * */
    private Integer data_precision;
    private Integer data_scale;
    private String property_name = "";
    private Boolean isPk = false;

    private Boolean nullable = false;

    private Integer data_length;
    private Comment transformComment = new Comment();
    private Boolean isCommentTransform = false;

    public Boolean IsCommentTransform() {
        return isCommentTransform;
    }

    public void setIsCommentTransform(Boolean commentTransform) {
        isCommentTransform = commentTransform;
    }

    public Comment getTransformComment() {
        return transformComment;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name.toLowerCase();
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
        String[] commentSlipt = comments.split("#");
        if (commentSlipt.length == 1) {
//            this.comments = commentSlipt[0];
            isCommentTransform = false;
            transformComment.setComment(commentSlipt[0]);
            return;
        }
        if(comments.startsWith("<!>")){
            transformComment.setShow(false);
        }
        isCommentTransform = true;
        transformComment.setComment(commentSlipt[0].replace("CN",""));
        transformComment.setNessesary(commentSlipt[1].equals("1"));
        transformComment.setEditable(commentSlipt[2].equals("1"));
        transformComment.setShow(commentSlipt[3].equals("1"));
        transformComment.setDictName(commentSlipt[4]);
        transformComment.setShowType(commentSlipt[5]);
        transformComment.setSort(Integer.parseInt(commentSlipt[6]));

    }

    public Integer getData_precision() {
        return data_precision;
    }

    public void setData_precision(String data_precision) {
        this.data_precision = data_precision.equals("") ? 0 : Integer.valueOf(data_precision);
    }

    public Integer getData_scale() {
        return data_scale;
    }

    public void setData_scale(String data_scale) {
        this.data_scale = data_scale.equals("") ? 0 : Integer.valueOf(data_scale);
    }

    public String getProperty_name() {

        if(column_name.contains("xt")){
            return column_name;
        }

        String s = Stream.of(column_name.split("_"))
                .map( String::toLowerCase )
                .map( Tools::toUpperCaseFirstOne )
                .reduce( "" , String::concat);

        return property_name.equals("") ? Tools.toLowerCaseFirstOne(s) : property_name;
    }

    public void setProperty_name(String property_name) {
        this.property_name = property_name;
    }

    public boolean isPk() {
        return isPk;
    }

    public void setPk(boolean pk) {
        isPk = pk;
    }

    public boolean isNullable() {
        return nullable && !getTransformComment().isNessesary();
    }

    public void setNullable(String nullable) {
        this.nullable = nullable.equals("Y");
    }

    public Integer getData_length() {
        return data_length;
    }

    public void setData_length(String data_length) {
        this.data_length = data_length.equals("") ? 0 : Integer.valueOf(data_length);
    }

    public Integer getRealDataLength() {

        if (data_precision == 0 && data_scale == 0) {
            if (isPk()) {
                return data_length;
            }
            if(comments.contains("CN")){
                return data_length / 3 ;
            }else{
                return data_length ;
            }

        } else {
            return (int) Math.pow(10, data_precision - data_scale);
        }
    }

    public String getRealDataLengthComent() {

        if (data_precision == 0 && data_scale == 0) {
            if (isPk()) {
                return "";
            }
            if(comments.contains("CN")){
                return String.format("长度在0~%d个字符",data_length / 3);

            }else{
                return String.format("长度在0~%d个字符",data_length );
            }

        } else {
            return String.format("范围在0~%d", (int) Math.pow(10, data_precision - data_scale) );
        }
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;
        if (this == obj) return true;
        if (obj instanceof ColumesObject) {
            ColumesObject columesObject = (ColumesObject) obj;
            return this.getProperty_name().equals(columesObject.getProperty_name());
        } else return false;

    }

    @Override
    public int hashCode() {
        return getProperty_name().hashCode();
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof ColumesObject) {
            ColumesObject columesObject = (ColumesObject) o;
            if (this.IsCommentTransform() && columesObject.IsCommentTransform()) {
                if (this.getTransformComment().getSort() > columesObject.getTransformComment().getSort()) {
                    return 1;
                } else if (this.getTransformComment().getSort().intValue() == columesObject.getTransformComment().getSort().intValue()) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (!this.IsCommentTransform() && columesObject.IsCommentTransform()) {
                return 1;
            } else if (this.IsCommentTransform() && !columesObject.IsCommentTransform()) {
                return -1;
            } else if (!this.IsCommentTransform() && !columesObject.IsCommentTransform()) {
                if (this.getComments().hashCode() < columesObject.getComments().hashCode()) {
                    return 1;
                } else if (this.getComments().hashCode() == columesObject.getComments().hashCode()) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                return 0;
            }

        } else {
            throw new RuntimeException("cannot cast to class ColumesObject");
        }
    }

    @Override
    public ColumesObject clone() {

        ColumesObject o = null;
        try {
            o = (ColumesObject) super.clone();
            o.transformComment = o.transformComment.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return o;

    }
}

