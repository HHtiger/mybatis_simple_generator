package ${parentPackageName}.service.impl;

import com.founder.dao.${entityName}Dao;
import com.founder.framework.annotation.MethodAnnotation;
import com.founder.framework.annotation.MethodAnnotation.logType;
import com.founder.framework.base.service.BaseService;
import com.founder.framework.utils.EasyUIPage;
import com.founder.model.${entityName};
import com.founder.service.${entityName}Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("${entityName?uncap_first}Service")
@Transactional
public class ${entityName}ServiceImpl extends BaseService implements ${entityName}Service{

    @Resource(name = "${entityName?uncap_first}Dao")
    private ${entityName}Dao ${entityName?uncap_first}Dao;

    /**
    * 新增业务对象
    */
    @MethodAnnotation(value = "新增", type = logType.insert)
    public void insert(${entityName} entity){
        ${entityName?uncap_first}Dao.insert(entity);
    }

    /**
    * 根据主键ID修改对象
    */
    @MethodAnnotation(value = "更新", type = logType.update)
    public void update(${entityName} entity){
        ${entityName?uncap_first}Dao.update(entity);
    }

    /**
    * 通过主键ID注销对象，只是修改注销状态为（已注销）
    */
    public void delete(${entityName} entity){
        ${entityName?uncap_first}Dao.delete(entity);
    }

    /**
    * 通过主键ID查询单个业务实体对象
    */
    public ${entityName} queryById(String ${pk}){
        return ${entityName?uncap_first}Dao.queryById(${pk});
    }

    /**
    * 根据业务实体查询业务实体列表
    */
    public List<${entityName}> queryByEntity(${entityName} entity){
        return ${entityName?uncap_first}Dao.queryByEntity(entity);
    }

    /**
    * 查询分页对象
    */
    public EasyUIPage queryPageList(Map<String, Object> map, EasyUIPage page){
        return ${entityName?uncap_first}Dao.queryPageList(map,page);
    }

}
