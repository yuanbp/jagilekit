package com.chieftain.agile.service.sys;

import java.util.Collection;
import java.util.List;

import com.chieftain.agile.entity.sys.SysPermission;
import com.chieftain.agile.entity.sys.SysUser;

/**
 * com.chieftain.agile.service.sys [workset_idea_01]
 * Created by Richard on 2018/5/24
 *
 * @author Richard on 2018/5/24
 */
public interface ILoginService {

    public SysUser findByLogin(String loginInfo);

    public Collection<String> findLoginRoles(String loginInfo);

    public Collection<String> findLoginPermissions(String loginInfo);

    public List<SysPermission> findLoginMenu(String loginInfo);
}
