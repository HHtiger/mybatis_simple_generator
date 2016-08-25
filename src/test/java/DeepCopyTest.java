import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Package: PACKAGE_NAME
 * ClassName: testStrem
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016/8/10
 * Version: 1.0
 */

public class DeepCopyTest {

    private static Logger log = LoggerFactory.getLogger(DeepCopyTest.class);


    @Test
    public void test1() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<PersionA> a = new ArrayList<>();
        a.add(new PersionA("a1",new PersionB("b1")));
        a.add(new PersionA("a2",new PersionB("b2")));
        a.add(new PersionA("a3",new PersionB("b3")));
        ArrayList<PersionA> b = a.stream().collect(ArrayList<PersionA>::new,
                (c, e) -> c.add((PersionA) e.clone()),
                ArrayList<PersionA>::addAll);
        a.get(0).getPersionB().setA("3333");

        a.stream().forEach(c -> log.debug("{}",c.getPersionB().getA()));
        b.stream().forEach(c -> log.debug("{}",c.getPersionB().getA()));

    }

}

class PersionB implements Cloneable{
    public PersionB(String a) {
        this.a = a;
    }

    public String a;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    @Override
    protected Object clone() {

        PersionB o = null;
        try {
            o = (PersionB) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return o;

    }
}

class PersionA implements Cloneable{

    private PersionB persionB;
    private String a;

    PersionA() {}

    PersionA(String a,PersionB b) {
        this.a = a;
        persionB = b;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public PersionB getPersionB() {
        return persionB;
    }

    public void setPersionB(PersionB persionB) {
        this.persionB = persionB;
    }

    @Override
    protected Object clone() {

        PersionA o = null;
        try {
            o = (PersionA) super.clone();
            o.setPersionB((PersionB) o.getPersionB().clone());
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return o;

    }
}

