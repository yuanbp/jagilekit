package com.chieftain.agile.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chieftain.agile.common.cache.api.JedisClient;
import com.chieftain.agile.dao.sys.SysPermissionMapper;
import com.chieftain.agile.entity.sys.SysPermission;
import com.chieftain.agile.service.sys.ILoginService;
import com.chieftain.agile.service.sys.ISysMenuService;
import com.chieftain.agile.utils.MenuTools;

/**
 * com.chieftain.agile.service.sys.impl [workset_idea_01]
 * Created by Richard on 2018/6/7
 *
 * @author Richard on 2018/6/7
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    private final String menusPerffix = "menus:";

    @Autowired
    private SysPermissionMapper mapper;

    @Override
    public List<SysPermission> doGetMenus(ILoginService loginService, JedisClient jedisClient, String username) throws Exception {
        List<SysPermission> sysPermissionList = null;
        String menusKey = menusPerffix + username;
        sysPermissionList = (List<SysPermission>) jedisClient.getConvert(menusKey);
        if (null == sysPermissionList || sysPermissionList.size() <= 0) {
            sysPermissionList = loginService.findLoginMenu(username);
            sysPermissionList = MenuTools.getChildsManyGroup(sysPermissionList, "0");
            jedisClient.setConvert(menusKey, sysPermissionList);
        }
        return sysPermissionList;
    }

    @Override
    public List<SysPermission> findPage(String menuName, Integer pageNum, Integer pageSize) {
        return mapper.findPage(menuName,pageNum,pageSize);
    }
}
