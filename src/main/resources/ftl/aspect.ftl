package ${parentPackageName}.aspect;

import com.founder.anjian.autoEntry.base.service.CRUDService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Order(1)
@Aspect
@Component
@Transactional
public class ${AspectName}Intercept {

    private static Logger log = LoggerFactory.getLogger(${AspectName}Intercept.class);

    @Autowired
    private CRUDService crudService;

<#list insertStService as stService>
    @Autowired
    private ${stService} ${stService?string?split(".")?last?uncap_first};

</#list>
    @Pointcut("execution(int ${parentPackageName}.service.*.insert(..))")
    public void declareJoinPointExpression() {}

    @Before("declareJoinPointExpression()")
    public void beforeMethod(JoinPoint joinpoint) {
        String methodName = joinpoint.getSignature().getName();
        List <Object> args = Arrays.asList(joinpoint.getArgs());
        System.out.println("前置通知：The method "+ methodName +" begins with " + args);
    }

    @After("declareJoinPointExpression()")
    public void afterMethod(JoinPoint joinpoint) {
        String methodName = joinpoint.getSignature().getName();
        System.out.println("后置通知：The method "+ methodName +" ends ");
    }

    @Around(" declareJoinPointExpression() && args(rawData) ")
    public Object doBasicProfiling(ProceedingJoinPoint pjp,Object rawData){
        return crudService.insert(rawData,pjp<#list insertStService as stService>,${stService?string?split(".")?last?uncap_first}</#list>);
    }

    @AfterReturning(value="declareJoinPointExpression()", returning="result")
    public void afterReturnning(JoinPoint joinpoint, Object result) {
        String methodName = joinpoint.getSignature().getName();
        System.out.println("返回通知：The method "+ methodName +" ends with " + result);
    }

    @AfterThrowing(value="declareJoinPointExpression()", throwing="e")
    public void afterThrowing(JoinPoint joinpoint, Exception e) {
        String methodName = joinpoint.getSignature().getName();
        System.out.println("异常通知：The method "+ methodName +" occurs exception " + e);
    }
}

