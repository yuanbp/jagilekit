package com.chieftain.agile.service.sys.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chieftain.agile.dao.sys.SysUserMapper;
import com.chieftain.agile.entity.sys.SysUser;
import com.chieftain.agile.service.sys.ISysUserService;

/**
 * com.chieftain.junite.service.sys.impl [workset_idea_01]
 * Created by Richard on 2018/5/14
 *
 * @author Richard on 2018/5/14
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findByLogin(String username) {
        return sysUserMapper.findByLogin(username);
    }

    @Override
    public Collection<String> findLoginRoles(String username) {
        return sysUserMapper.findLoginRoles(username);
    }

    @Override
    public Collection<String> findLoginPermissions(String username) {
        return sysUserMapper.findLoginPermissions(username);
    }
}
