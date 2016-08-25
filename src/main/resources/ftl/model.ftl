package ${parentPackageName}.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;
import com.founder.framework.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import javax.ws.rs.FormParam;

import java.io.Serializable;

@DBInfoAnnotation(tableName = "${tableName}" , pk = "${pk.getProperty_name()}")
public class ${entityName} extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

<#list columus as colume>

    @FieldDesc("${colume.getComments()}")
    @FormParam("${colume.getProperty_name()}")
    <#if colume.getJavaType() == "java.util.Date">
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    </#if>
    private ${colume.getJavaType()} ${colume.getProperty_name()};

</#list>

<#list columus as colume>

    public ${colume.getJavaType()} get${colume.getProperty_name()}(){
        return ${colume.getProperty_name()};
    }

    public void set${colume.getProperty_name()}(${colume.getJavaType()} ${colume.getProperty_name()}) {

        <#if colume.getJavaType() == 'java.lang.String'>
        this.${colume.getProperty_name()} = ${colume.getProperty_name()}==null?"":${colume.getProperty_name()};
        <#elseif colume.getJavaType() == 'java.util.Date'>
        this.${colume.getProperty_name()} = ${colume.getProperty_name()}==null?new java.util.Date():${colume.getProperty_name()};
        <#elseif colume.getJavaType() == 'java.lang.Integer'||
        colume.getJavaType() == 'java.lang.Double' ||
        colume.getJavaType() == 'java.lang.Long'
        >
        this.${colume.getProperty_name()} = ${colume.getProperty_name()}==null?0:${colume.getProperty_name()};
        </#if>
    }

</#list>

    @Override
    public String toString() {
        return "${entityName}{" +
        <#list columus as colume>
            <#if colume_index !=0 >
                "," +
            </#if>
                "\"${colume.getProperty_name()}\" : \"" + ${colume.getProperty_name()} + "\"" +
        </#list>
        <#list xt_columes as colume>
            ",\"${colume.getProperty_name()}\" : \"" + get${colume.getProperty_name()?cap_first}() + "\"" +
        </#list>
        '}';
    }

}