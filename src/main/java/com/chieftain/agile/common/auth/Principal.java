package com.chieftain.agile.common.auth;

import java.io.Serializable;

import com.chieftain.agile.entity.sys.SysUser;

/**
 * com.chieftain.junite.common.auth [workset_idea_01]
 * Created by Richard on 2018/5/15
 *
 * @author Richard on 2018/5/15
 */
public class Principal implements Serializable {

    private static final long serialVersionUID = 8455638205227417752L;
    private String id;
    private String loginName;
    private String name;
    private boolean mobileLogin;
    private String loginType;
    private SysUser user;

    public Principal(){

    }

    public Principal(SysUser user, boolean mobileLogin, String loginType) {
        this.id = user.getId();
        this.loginName = this.getLoginName(user, loginType);
        this.name = user.getUsername();
        this.mobileLogin = mobileLogin;
        this.loginType = loginType;
        this.user = user;
    }

    public String getLoginName(SysUser user, String loginType){
        String loginName = null;
        switch (loginType){
            case "nameLogin" :
                loginName = user.getLoginname();
                break;
            case "emailLogin" :
                loginName = user.getEmail();
                break;
            case "mobileLogin" :
                loginName = user.getMobile();
                break;
            default:
                loginName = user.getLoginname();
                break;
        }
        return loginName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }

    public void setMobileLogin(boolean mobileLogin) {
        this.mobileLogin = mobileLogin;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }
}
