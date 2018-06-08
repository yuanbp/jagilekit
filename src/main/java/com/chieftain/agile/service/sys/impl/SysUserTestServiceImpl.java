package com.chieftain.agile.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chieftain.agile.dao.sys.SysUserTestMapper;
import com.chieftain.agile.entity.sys.SysUserTest;
import com.chieftain.agile.service.sys.ISysUserTestService;

/**
 * com.chieftain.junite.service.impl [workset_idea_01]
 * Created by Richard on 2018/5/9
 *
 * @author Richard on 2018/5/9
 */
@Service
public class SysUserTestServiceImpl implements ISysUserTestService {

    @Autowired
    private SysUserTestMapper sysUserTestMapper;

    @Override
    public List<SysUserTest> findUserByName(String name) {
        return sysUserTestMapper.findUserByName(name);
    }
}
