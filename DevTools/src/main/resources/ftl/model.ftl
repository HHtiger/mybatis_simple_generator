package ${parentPackageName}.model;

import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;
import com.founder.framework.base.entity.BaseEntity;

import java.io.Serializable;

@DBInfoAnnotation(tableName = "${tableName}" , pk = "${pk}")
public class ${entityName} extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	<#list columus as colume>
        <#if !colume["COLUMN_NAME"]?lower_case?starts_with("xt_")>
            @FieldDesc("${colume["COMMENTS"]!''}") private ${db2JavaMap[colume["DATA_TYPE"]]} ${colume["COLUMN_NAME"]?lower_case};
        </#if>
	</#list>

    <#list columus as colume>
        <#if !colume["COLUMN_NAME"]?lower_case?starts_with("xt_")>
            public ${db2JavaMap[colume["DATA_TYPE"]]} get${colume["COLUMN_NAME"]?lower_case?cap_first}(){
                return ${colume["COLUMN_NAME"]?lower_case};
            }

            public void set${colume["COLUMN_NAME"]?lower_case?cap_first}(${db2JavaMap[colume["DATA_TYPE"]]} ${colume["COLUMN_NAME"]?lower_case}) {
                this.${colume["COLUMN_NAME"]?lower_case} = ${colume["COLUMN_NAME"]?lower_case};
            }
        </#if>
    </#list>

    @Override
    public String toString() {
        return "${entityName}{" +
        <#list columus as colume>
            <#if colume_index !=0 >
                "," +
            </#if>
            <#if colume["COLUMN_NAME"]?lower_case?starts_with("xt_")>
                "\"${colume["COLUMN_NAME"]?lower_case}\" : \"" + get${colume["COLUMN_NAME"]?lower_case?cap_first}() + "\"" +
            <#else>
                "\"${colume["COLUMN_NAME"]?lower_case}\" : \"" + ${colume["COLUMN_NAME"]?lower_case} + "\"" +
            </#if>
        </#list>
        '}';
    }

}