package com.chieftain.agile.entity.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysPermission implements Serializable {
    private String menuid;

    private String menuname;

    private String type;

    private String permission;

    private String parentid;

    private String parentids;

    private String level;

    private String menuurl;

    private Integer menunodetype;

    private Integer menustate;

    private Integer sort;

    private String icon;

    private String updater;

    private Date updatetime;

    private Date createtime;

    private List<SysPermission> nodes;

    private static final long serialVersionUID = 1L;

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid == null ? null : menuid.trim();
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname == null ? null : menuname.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getParentids() {
        return parentids;
    }

    public void setParentids(String parentids) {
        this.parentids = parentids == null ? null : parentids.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getMenuurl() {
        return menuurl;
    }

    public void setMenuurl(String menuurl) {
        this.menuurl = menuurl == null ? null : menuurl.trim();
    }

    public Integer getMenunodetype() {
        return menunodetype;
    }

    public void setMenunodetype(Integer menunodetype) {
        this.menunodetype = menunodetype;
    }

    public Integer getMenustate() {
        return menustate;
    }

    public void setMenustate(Integer menustate) {
        this.menustate = menustate;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public List<SysPermission> getNodes() {
        return nodes;
    }

    public void setNodes(List<SysPermission> nodes) {
        this.nodes = nodes;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", menuid=").append(menuid);
        sb.append(", menuname=").append(menuname);
        sb.append(", type=").append(type);
        sb.append(", permission=").append(permission);
        sb.append(", parentid=").append(parentid);
        sb.append(", parentids=").append(parentids);
        sb.append(", level=").append(level);
        sb.append(", menuurl=").append(menuurl);
        sb.append(", menunodetype=").append(menunodetype);
        sb.append(", menustate=").append(menustate);
        sb.append(", sort=").append(sort);
        sb.append(", icon=").append(icon);
        sb.append(", updater=").append(updater);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", createtime=").append(createtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysPermission other = (SysPermission) that;
        return (this.getMenuid() == null ? other.getMenuid() == null : this.getMenuid().equals(other.getMenuid()))
            && (this.getMenuname() == null ? other.getMenuname() == null : this.getMenuname().equals(other.getMenuname()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getPermission() == null ? other.getPermission() == null : this.getPermission().equals(other.getPermission()))
            && (this.getParentid() == null ? other.getParentid() == null : this.getParentid().equals(other.getParentid()))
            && (this.getParentids() == null ? other.getParentids() == null : this.getParentids().equals(other.getParentids()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getMenuurl() == null ? other.getMenuurl() == null : this.getMenuurl().equals(other.getMenuurl()))
            && (this.getMenunodetype() == null ? other.getMenunodetype() == null : this.getMenunodetype().equals(other.getMenunodetype()))
            && (this.getMenustate() == null ? other.getMenustate() == null : this.getMenustate().equals(other.getMenustate()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getIcon() == null ? other.getIcon() == null : this.getIcon().equals(other.getIcon()))
            && (this.getUpdater() == null ? other.getUpdater() == null : this.getUpdater().equals(other.getUpdater()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMenuid() == null) ? 0 : getMenuid().hashCode());
        result = prime * result + ((getMenuname() == null) ? 0 : getMenuname().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getPermission() == null) ? 0 : getPermission().hashCode());
        result = prime * result + ((getParentid() == null) ? 0 : getParentid().hashCode());
        result = prime * result + ((getParentids() == null) ? 0 : getParentids().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getMenuurl() == null) ? 0 : getMenuurl().hashCode());
        result = prime * result + ((getMenunodetype() == null) ? 0 : getMenunodetype().hashCode());
        result = prime * result + ((getMenustate() == null) ? 0 : getMenustate().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getIcon() == null) ? 0 : getIcon().hashCode());
        result = prime * result + ((getUpdater() == null) ? 0 : getUpdater().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        return result;
    }
}