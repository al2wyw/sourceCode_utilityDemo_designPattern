package reflect;

import demoObject.HcrmXiaoerSettlePerformancePO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Created by johnny.ly on 2016/1/19.
 */
public class testBeanProperty {

    public static void main(String[] args){
        HcrmXiaoerSettlePerformancePO t1 = new HcrmXiaoerSettlePerformancePO();
        t1.setConfirmOrderCount(200L);
        HcrmXiaoerSettlePerformancePO t2 = new HcrmXiaoerSettlePerformancePO();
        t2.setConfirmOrderCount(100L);
        addUpLongProperty(t1, t2);
        System.out.print(t1.getConfirmOrderCount());
    }

    private static void addUpLongTypeProperty(HcrmXiaoerSettlePerformancePO target, HcrmXiaoerSettlePerformancePO po){
        try {
            PropertyDescriptor[] props = Introspector.getBeanInfo(target.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor p : props){
                Method m = p.getReadMethod();
                Class t = m.getReturnType();
                if(t.equals(Long.class)){
                    Object ori = p.getReadMethod().invoke(target);
                    Object add = p.getReadMethod().invoke(po);
                    long v1 = ori==null ? 0L : (Long)ori;
                    long v2 = add==null ? 0L : (Long)add;
                    long res = v1+v2;
                    p.getWriteMethod().invoke(target,res);
                }
            }
        }catch (Exception e){

        }
    }

    private static void addUpLongProperty(HcrmXiaoerSettlePerformancePO target, HcrmXiaoerSettlePerformancePO po){
        try {
            PropertyDescriptor[] props = Introspector.getBeanInfo(target.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor p : props){
                String s = p.getName();
                Class t = p.getPropertyType();
                if(t.equals(Long.class)){
                    String ori = BeanUtils.getProperty(target, s);
                    String add = BeanUtils.getProperty(po, s);
                    long v1 = 0L;
                    long v2 = 0L;
                    if(StringUtils.isNoneEmpty(ori)){
                        v1 = Long.valueOf(ori);
                    }
                    if(StringUtils.isNoneEmpty(add)){
                        v2 = Long.valueOf(add);
                    }
                    long res = v1+v2;
                    BeanUtils.setProperty(target, s, res);
                }
            }
        }catch (Exception e){

        }
    }
}
