package com.chieftain.agile.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chieftain.agile.entity.sys.SysPermission;

public interface SysPermissionMapper {
    int deleteByPrimaryKey(String menuid);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(String menuid);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    List<SysPermission> findPage(@Param("menuName") String menuName, @Param("pageNumKey") Integer pageNum, @Param("pageSizeKey") Integer pageSize);
}