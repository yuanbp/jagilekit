package com.chieftain.agile.dao.sys;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chieftain.agile.entity.sys.SysPermission;
import com.chieftain.agile.entity.sys.SysUser;

@Mapper
@Repository
public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findByLogin(@Param("username") String username);

    Collection<String> findLoginRoles(@Param("username") String username);

    Collection<String> findLoginPermissions(@Param("username") String username);

    SysUser findNameLogin(@Param("loginName") String loginName);

    Collection<String> findLoginRolesByName(@Param("loginName") String loginName);

    Collection<String> findLoginPermissionsByName(@Param("loginName") String loginName);

    SysUser findEmailLogin(@Param("email") String email);

    Collection<String> findLoginRolesByEmail(@Param("email") String email);

    Collection<String> findLoginPermissionsByEmail(@Param("email") String email);

    SysUser findMobileLogin(@Param("mobile") String mobile);

    Collection<String> findLoginRolesByMobile(@Param("mobile") String mobile);

    Collection<String> findLoginPermissionsByMobile(@Param("mobile") String mobile);

    List<SysPermission> findMenuByMobile(@Param("mobile") String mobile);

    List<SysPermission> findMenuByName(@Param("loginName") String loginName);

    List<SysPermission> findMenuByEmail(@Param("email") String email);
}