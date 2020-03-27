package cn.edu.ujs.lp.intells.common.utils;


import cn.edu.ujs.lp.intells.common.exception.AdmissionException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *将一个对象拷贝到另一个对象上去
 *
 * @author Meredith
 * @date 2019-09-18
 */
public class BeanUtils {

    /**
     * 将一个对象拷贝到另一个对象上去，忽略空值
     *
     * @param dest
     * @param orgin
     */
    public static void copyBeanIgnoreNull(Object dest,Object orgin){
        try {
            org.springframework.beans.BeanUtils.copyProperties(orgin,dest,getNullPropertyNames(orgin));
        }catch (Exception e){
            throw new AdmissionException("数据异常");
        }
    }


    /**
     * 复制对象
     * @param dest
     * @param orgin
     */
    public static void copyBean(Object dest,Object orgin){
        try {
            org.springframework.beans.BeanUtils.copyProperties(orgin,dest,new String[]{});
        }catch (Exception e){
            throw new AdmissionException("数据异常");
        }
    }

    /**
     * 获取指定对象中所有非空的字段名
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
