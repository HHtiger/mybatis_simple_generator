package ${parentPackageName}.dao;

import ${parentPackageName}.model.${entityName};
import org.springframework.stereotype.Component;

@Component
public interface ${entityName}Dao {
    int deleteByPrimaryKey(${pk.getJavaType()} ${pk.getColumn_name()});

    int insert(${entityName} ${entityName?lower_case});

    int insertSelective(${entityName} ${entityName?lower_case});

    ${entityName} selectByPrimaryKey(${pk.getJavaType()} ${pk.getColumn_name()});

    int updateByPrimaryKeySelective(${entityName} ${entityName?lower_case});

    int updateByPrimaryKey(${entityName} ${entityName?lower_case});
}