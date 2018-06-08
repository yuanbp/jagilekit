package com.chieftain.agile.common.datasource;

import java.io.Serializable;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * com.chieftain.junite.common.druid [workset_idea_01]
 * Created by Richard on 2018/5/9
 *
 * @author Richard on 2018/5/9
 */
@WebServlet(urlPatterns = "/druid/*",
    initParams = {
        @WebInitParam(name = "allow", value = ""),// IP 白名单 (没有配置或者为空，则允许所有访问)
        @WebInitParam(name = "deny", value = "192.168.145.145"),// IP 黑名单 (存在共同时，deny 优先于 allow)
        @WebInitParam(name = "loginUsername", value = "root"),// 用户名
        @WebInitParam(name = "loginPassword", value = "123456"),// 密码
        @WebInitParam(name = "resetEnable", value = "false")// 禁用 HTML 页面上的 “Reset All” 功能
    })
public class DruidStatViewServlet extends StatViewServlet implements Serializable {

    private static final long serialVersionUID = 3650499995851391347L;
}
