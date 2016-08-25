package com.founder.anjian.autoEntry.base;

import com.founder.anjian.autoEntry.st.model.TbStSawp;
import com.founder.anjian.autoEntry.xw.dsdy.model.TbXwDsdy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Package: com.founder.anjian.autoEntry.base
 * ClassName: BeanCopyUtil
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016/8/17
 * Version: 1.0
 */
public class BeanCopyUtil {

    private static Logger log = LoggerFactory.getLogger(com.founder.anjian.autoEntry.base.BeanCopyUtil.class);

    /*
    * xw接收行为表参数，st接收实体表参数，不可交换
    * */
    public static void copy(Object xw, Object st)  {
        for(Field fxw : xw.getClass().getDeclaredFields()){
            fxw.setAccessible(true);
            for(Field fst : st.getClass().getDeclaredFields()){
                try {
                    fst.setAccessible(true);

                    boolean canCopy = false;

                    if(fxw.getName().equals(fst.getName())){
                        canCopy = true;
                    }else if(fst.getName().equals("sawpbh")){
                        if(fxw.getName().equals("dsdySawpbh")){
                            canCopy = true;
                        }
                    }

                    if(canCopy){
                            log.debug("{},{}", fxw.getName(), fst.getName());
                            log.debug("{},{}", fxw.get(xw), fst.get(st));
                            fst.set(st, fxw.get(xw));
                            log.debug("{},{}", fxw.get(xw), fst.get(st));
                    }
                } catch (IllegalAccessException e) {
                    log.warn(e.getMessage());
                    continue;
                }
            }
        }
    }

    public static void main(String[] args) {
        TbStSawp tbStWp = new TbStSawp();
        TbXwDsdy tbXwDsdy = new TbXwDsdy();
        tbXwDsdy.setxxzjbh("123");
        tbXwDsdy.setwpjzrmby(12.0);
        com.founder.anjian.autoEntry.base.BeanCopyUtil.copy(tbXwDsdy,tbStWp);
        System.out.println();
    }

}
