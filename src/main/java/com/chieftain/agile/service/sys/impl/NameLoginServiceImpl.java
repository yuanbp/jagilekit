package com.chieftain.agile.service.sys.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chieftain.agile.dao.sys.SysUserMapper;
import com.chieftain.agile.entity.sys.SysPermission;
import com.chieftain.agile.entity.sys.SysUser;
import com.chieftain.agile.service.sys.ILoginService;

/**
 * com.chieftain.agile.service.sys.impl [workset_idea_01]
 * Created by Richard on 2018/5/24
 *
 * @author Richard on 2018/5/24
 */
@Service("nameLoginService")
public class NameLoginServiceImpl implements ILoginService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findByLogin(String loginInfo) {
        return sysUserMapper.findNameLogin(loginInfo);
    }

    @Override
    public Collection<String> findLoginRoles(String loginInfo) {
        return sysUserMapper.findLoginRolesByName(loginInfo);
    }

    @Override
    public Collection<String> findLoginPermissions(String loginInfo) {
        return sysUserMapper.findLoginPermissionsByName(loginInfo);
    }

    @Override
    public List<SysPermission> findLoginMenu(String loginInfo) {
        return sysUserMapper.findMenuByName(loginInfo);
    }
}
