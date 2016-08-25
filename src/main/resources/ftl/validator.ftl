package ${parentPackageName}.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.founder.framework.base.validator.BaseValidator;
import ${parentPackageName}.vo.${entityName}VO;


@Component
public class ${entityName}VOValidator extends BaseValidator {

    @Override
    public boolean supports(Class<?> paramClass) {
        return ${entityName}VO.class.equals(paramClass);
    }

    @Override
    public void execute(Object paramObject, Errors paramErrors) {
        ${entityName}VO vo=(${entityName}VO)paramObject;

    <#list columus as colume>
        <#if colume.getJavaType() == 'java.lang.String'>
            <#if !colume.isNullable()>
        if(StringUtils.isBlank(vo.get${colume.getProperty_name()}())){
            ValidationUtils.rejectIfEmptyOrWhitespace(paramErrors, "${colume.getColumn_name()}", "${colume.getColumn_name()}.isEmpty", "【${colume.getComments()}】不能为空");
            return;
        }
            </#if>
        if(vo.get${colume.getProperty_name()}().length()>${colume.getRealDataLength()?c}){
            paramErrors.rejectValue( "${colume.getColumn_name()}", "${colume.getColumn_name()}.error", "【${colume.getComments()}】长度过长");
            return;
        }
        <#elseif colume.getJavaType() == 'java.lang.Integer'||
                 colume.getJavaType() == 'java.lang.Double' ||
                 colume.getJavaType() == 'java.lang.Long'
        >
        if(vo.get${colume.getProperty_name()}()>=${colume.getRealDataLength()?c}){
            paramErrors.rejectValue( "${colume.getColumn_name()}", "${colume.getColumn_name()}.error", "【${colume.getComments()}】长度过长");
            return;
        }
        </#if>
    </#list>

    }
}
