package com.chieftain.agile.service.sys;

import java.util.List;

import com.chieftain.agile.entity.sys.SysUserTest;

/**
 * com.chieftain.junite.service [workset_idea_01]
 * Created by Richard on 2018/5/9
 *
 * @author Richard on 2018/5/9
 */
public interface ISysUserTestService {

    public List<SysUserTest> findUserByName(String name);
}
