package com.chieftain.agile.service.sys.impl;

import java.util.Collection;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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

    @Override
    public int insertSelective(SysUser record) {
        return sysUserMapper.insertSelective(record);
    }

    public int updateOrInsert(SysUser record){
        if(StringUtils.isNotBlank(record.getId())){
            return sysUserMapper.updateByPrimaryKeySelective(record);
        }else {
            record.setId(UUID.randomUUID().toString().replaceAll("-",""));
            return sysUserMapper.insertSelective(record);
        }
    }

    @Override
    public SysUser selectByPrimaryKey(String id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }


}
