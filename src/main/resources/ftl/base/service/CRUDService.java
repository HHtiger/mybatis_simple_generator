package com.founder.anjian.autoEntry.base.service;

import com.founder.anjian.autoEntry.base.CRUDInterface;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Package: com.founder.anjian.autoEntry.base.service
 * ClassName: CRUDService
 * Author: Founder123
 * Description:
 * CreateDate: 2016-08-18 09:48:37
 * Version: 1.0
 */
@Transactional
@Service
public class CRUDService{

    public int insert(Object object, ProceedingJoinPoint pjp, CRUDInterface... services){
        try {
            pjp.proceed();
            for(CRUDInterface service : services){
                    service.insert(object);
            }
            return 1;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(e);
        } catch (Throwable e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(e);
        }

    }

}
