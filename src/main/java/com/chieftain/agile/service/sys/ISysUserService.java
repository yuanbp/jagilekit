package com.chieftain.agile.service.sys;

import java.util.Collection;

import com.chieftain.agile.entity.sys.SysUser;

/**
 * com.chieftain.junite.service.sys.impl [workset_idea_01]
 * Created by Richard on 2018/5/14
 *
 * @author Richard on 2018/5/14
 */
public interface ISysUserService {

    SysUser findByLogin(String username);

    Collection<String> findLoginRoles(String username);

    Collection<String> findLoginPermissions(String username);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateOrInsert(SysUser record);
}
