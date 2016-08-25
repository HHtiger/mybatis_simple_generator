package com.founder.db;

public class Comment implements Cloneable{
    private String comment;
    private boolean isNessesary;
    private boolean isEditable;
    private boolean isShow;
    private String dictName;
    private String showType;
    private Integer sort;

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                ", isNessesary=" + isNessesary +
                ", isEditable=" + isEditable +
                ", isShow=" + isShow +
                ", dictName='" + dictName + '\'' +
                ", showType='" + showType + '\'' +
                ", sort=" + sort +
                '}';
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isNessesary() {
        return isNessesary;
    }

    public void setNessesary(boolean nessesary) {
        isNessesary = nessesary;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public Comment clone() {

        Comment o = null;
        try {
            o = (Comment) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return o;

    }

}
