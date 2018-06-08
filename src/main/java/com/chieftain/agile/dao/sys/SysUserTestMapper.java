package com.chieftain.agile.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chieftain.agile.entity.sys.SysUserTest;

@Mapper
@Repository
public interface SysUserTestMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUserTest record);

    int insertSelective(SysUserTest record);

    SysUserTest selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUserTest record);

    int updateByPrimaryKey(SysUserTest record);

    List<SysUserTest> findUserByName(@Param("name")String name);
}