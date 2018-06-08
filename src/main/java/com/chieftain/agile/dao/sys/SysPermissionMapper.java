package com.chieftain.agile.dao.sys;

import com.chieftain.agile.entity.sys.SysPermission;

public interface SysPermissionMapper {
    int deleteByPrimaryKey(String menuid);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(String menuid);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
}