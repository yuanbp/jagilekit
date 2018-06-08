package com.chieftain.agile.utils;

import java.util.ArrayList;
import java.util.List;

import com.chieftain.agile.entity.sys.SysPermission;

/**
 * com.chieftain.agile.utils [workset_idea_01]
 * Created by Richard on 2018/6/7
 *
 * @author Richard on 2018/6/7
 */
public class MenuTools {

    /**
     * 根据 id 获取所有父级目录 一维
     * @param list
     * @param pid
     * @return
     */
    public static List<SysPermission> getParents(List<SysPermission> list, String pid) {
        List<SysPermission> arr = new ArrayList<SysPermission>();
        for (SysPermission sysPermission : list) {
            if (pid.equals(sysPermission.getMenuid())) {
                arr.addAll(getParents(list, sysPermission.getParentids()));
                arr.add(sysPermission);
                break;
            }
        }
        return arr;
    }

    /**
     * 根据 id 获取所有子集分类 (一维 List 集合)
     * 1 11 111
     *
     * @param list
     * @param pid
     * @return
     */
    public static List<SysPermission> getChilds(List<SysPermission> list, String pid) {
        List<SysPermission> arr = new ArrayList<SysPermission>();
        for (SysPermission sysPermission : list) {
            if (pid.equals(sysPermission.getParentids())) {
                arr.addAll(getChilds(list, sysPermission.getMenuid()));
                arr.add(sysPermission);

            }
        }
        return arr;
    }

    /**
     * 根据 id 返回所有子集分类,(多维 List 集合, List 中含有子集 List)
     * <p>
     * 1
     * 11
     * 111
     * 2
     * 22
     * 222
     *
     * @param list
     * @param topParentId 顶级菜单的父菜单
     * @return
     */
    public static List<SysPermission> getChildsManyGroup(List<SysPermission> list, String topParentId) {
        List<SysPermission> arr = new ArrayList<SysPermission>();
        for (SysPermission sysPermission : list) {
            if (topParentId.equals(sysPermission.getParentids())) {
                sysPermission.setNodes(getChildsManyGroup(list, sysPermission.getMenuid()));
                arr.add(sysPermission);
            }
        }
        return arr;
    }

    /**
     * 组合为一维集合
     *
     * @param list
     * @param pid
     * @return
     */
    public static List<SysPermission> pushOneGroup(List<SysPermission> list, String pid) {
        List<SysPermission> arr = new ArrayList<SysPermission>();
        for (SysPermission sysPermission : list) {
            if (pid.equals(sysPermission.getParentids())) {
                arr.add(sysPermission);
                arr.addAll(pushOneGroup(list, sysPermission.getMenuid()));
            }
        }
        return arr;
    }

    /**
     * 组合为多维集合
     * 用途：系统首页的导航菜单，常见的 2-3 级通过下面的方法压栈成多维集合在供前台显示
     *
     * @param list 要便利的集合数据
     * @param pid  父 id
     * @return
     */
    public static List<SysPermission> pushManyGroup(List<SysPermission> list, String pid) {
        List<SysPermission> arr = new ArrayList<SysPermission>();
        for (SysPermission sysPermission : list) {
            if (pid.equals(sysPermission.getParentids())) {
                sysPermission.setNodes(pushManyGroup(list, sysPermission.getMenuid()));
                arr.add(sysPermission);
            }
        }
        return arr;
    }
}
