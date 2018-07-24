package com.chieftain.agile.service.sys;

import java.util.List;

import com.chieftain.agile.common.cache.api.JedisClient;
import com.chieftain.agile.entity.sys.SysPermission;

/**
 * com.chieftain.agile.service.sys [workset_idea_01]
 * Created by Richard on 2018/6/7
 *
 * @author Richard on 2018/6/7
 */
public interface ISysMenuService {

    public List<SysPermission> doGetMenus(ILoginService loginService, JedisClient jedisClient, String username) throws Exception;

    public List<SysPermission> findPage(String menuName,Integer pageNum,Integer pageSize);
}
