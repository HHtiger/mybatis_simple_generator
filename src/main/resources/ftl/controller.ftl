package ${parentPackageName}.controller;

import ${parentPackageName}.service.${entityName}Service;
import ${parentPackageName}.vo.${entityName}VO;
import ${parentPackageName}.validator.${entityName}VOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.founder.framework.annotation.TokenAnnotation;
import com.founder.framework.components.AppConst;
import com.founder.framework.base.controller.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/autoEntry/${entityName?uncap_first}")
public class ${entityName}Controller extends BaseController {

    private static Logger log = LoggerFactory.getLogger(${entityName}Controller.class);

    @Autowired
    private ${entityName}VOValidator ${entityName?uncap_first}VOValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        Object target=binder.getTarget();
        if(target!=null && target.getClass().equals(${entityName}VO.class)){
            binder.setValidator(${entityName?uncap_first}VOValidator);
        }
    }

    @Autowired
    private ${entityName}Service ${entityName?uncap_first}Service;

    @TokenAnnotation(remove=true)
    @RequestMapping(value = "/insert", method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Map<String, Object> insert(@Valid ${entityName}VO ${entityName?uncap_first}Vo,BindingResult result){

        Map<String, Object> model = new HashMap<>();

        if(!this.validateResult(model, result)){
            return model;
        }

        try {

            ${entityName?uncap_first}Service.insert(${entityName?uncap_first}Vo);

            model.put(AppConst.STATUS, AppConst.SUCCESS);
            model.put(AppConst.MESSAGES, getAddSuccess());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            model.put(AppConst.STATUS, AppConst.FAIL);
            model.put(AppConst.MESSAGES, e.getLocalizedMessage());
        }

        return model;

    }

}
