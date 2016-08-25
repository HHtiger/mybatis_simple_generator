package ${parentPackageName}.service;

import com.founder.anjian.autoEntry.base.CRUDInterface;
import ${parentPackageName}.dao.${entityName}Dao;
import ${parentPackageName}.model.${entityName};

import com.founder.anjian.autoEntry.base.BeanCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service("${entityName?uncap_first}Service")
@Transactional
public class ${entityName}Service extends com.founder.framework.base.service.BaseService implements CRUDInterface {

    private static Logger log = LoggerFactory.getLogger(${entityName}Service.class);

    @Resource(name = "${entityName?uncap_first}Dao")
    private ${entityName}Dao ${entityName?uncap_first}Dao;

    public int insert(Object rawData) throws Exception {

        ${entityName} thisObj = new ${entityName}();

        BeanCopyUtil.copy(rawData,thisObj);

        return ${entityName?uncap_first}Dao.insert(thisObj);
    }

}
