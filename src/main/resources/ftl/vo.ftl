package ${parentPackageName}.vo;

import com.founder.framework.utils.UUID;
import com.alibaba.fastjson.annotation.JSONField;
import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;
import com.founder.framework.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import javax.ws.rs.FormParam;

import java.io.Serializable;

public class ${entityName}VO implements Serializable {
	private static final long serialVersionUID = 1L;

<#list columus as colume>
    <#if !colume.getColumn_name()?starts_with("xt")>
    @FieldDesc("${colume.getComments()}")
        <#if colume.getJavaType() == "java.util.Date">
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
        </#if>
    private ${colume.getJavaType()} ${colume.getProperty_name()};
    </#if>
</#list>

    public ${entityName}VO(){
<#list columus as colume>
    <#if colume.isPk()>
        this.${colume.getProperty_name()} = UUID.create().substring(0,22);
    </#if>
</#list>
    }

<#list columus as colume>
    <#if !colume.getColumn_name()?starts_with("xt")>
    public ${colume.getJavaType()} get${colume.getProperty_name()}(){
        <#if colume.getJavaType() == 'java.lang.String'>
        return ${colume.getProperty_name()}==null?"":${colume.getProperty_name()};
        <#elseif colume.getJavaType() == 'java.util.Date'>
        return ${colume.getProperty_name()}==null?new java.util.Date():${colume.getProperty_name()};
        <#elseif colume.getJavaType() == 'java.lang.Integer'||
        colume.getJavaType() == 'java.lang.Double' ||
        colume.getJavaType() == 'java.lang.Long'
        >
        return ${colume.getProperty_name()}==null?0:${colume.getProperty_name()};
        </#if>
    }

    public void set${colume.getProperty_name()}(${colume.getJavaType()} ${colume.getProperty_name()}) {
        this.${colume.getProperty_name()} = ${colume.getProperty_name()};
    }
    </#if>
</#list>

}