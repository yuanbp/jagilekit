package com.chieftain.agile.utils;

import java.util.Collection;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.google.common.collect.Lists;

/**
 * com.chieftain.agile.utils [workset_idea_01]
 * Created by Richard on 2018/5/25
 *
 * @author Richard on 2018/5/25
 */
public class DozerUtil {

    /**
     * 持有 Dozer 单例, 避免重复创建 DozerMapper 消耗资源.
     */
    private static DozerBeanMapper dozer = new DozerBeanMapper();


    /**
     * 基于 Dozer 转换对象的类型.
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }


    /**
     * 基于 Dozer 转换 Collection 中对象的类型.
     */
    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass) {
        List<T> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }


    /**
     * 基于 Dozer 将对象 A 的值拷贝到对象 B 中.
     */
    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }
}
