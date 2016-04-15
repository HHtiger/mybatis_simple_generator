package ${parentPackageName}.model;

import com.founder.framework.annotation.DBInfoAnnotation;
import com.founder.framework.annotation.FieldDesc;
import com.founder.framework.base.entity.BaseEntity;

import java.io.Serializable;

@DBInfoAnnotation(tableName = "${tableName}" , pk = "${pk}")
public class ${entityName} extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	<#list columus as colume>
	@FieldDesc("${colume["COMMENTS"]}") private ${db2JavaMap[colume["DATA_TYPE"]]} ${colume["COLUMN_NAME"]?lower_case};
    public ${db2JavaMap[colume["DATA_TYPE"]]} get${colume["COLUMN_NAME"]?lower_case?cap_first}(){
    return ${colume["COLUMN_NAME"]?lower_case};
    }
    public void set${colume["COLUMN_NAME"]?lower_case?cap_first}(${db2JavaMap[colume["DATA_TYPE"]]} ${colume["COLUMN_NAME"]?lower_case}) {
    this.${colume["COLUMN_NAME"]?lower_case} = ${colume["COLUMN_NAME"]?lower_case};
    }
	</#list>

}