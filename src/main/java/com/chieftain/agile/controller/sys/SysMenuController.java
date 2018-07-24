package com.chieftain.agile.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.chieftain.agile.common.auth.Principal;
import com.chieftain.agile.common.auth.PrincipalUtil;
import com.chieftain.agile.common.cache.api.JedisClient;
import com.chieftain.agile.entity.sys.SysPermission;
import com.chieftain.agile.service.sys.ILoginService;
import com.chieftain.agile.service.sys.ISysMenuService;
import com.chieftain.agile.utils.FastJsonTools;

/**
 * com.chieftain.agile.controller.sys [workset_idea_01]
 * Created by Richard on 2018/5/28
 *
 * @author Richard on 2018/5/28
 */
@Controller
@RequestMapping(value = "/menu")
public class SysMenuController {

    @Autowired
    private ILoginService nameLoginService;
    @Autowired
    private ILoginService emailLoginService;
    @Autowired
    private ILoginService mobileLoginService;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private ISysMenuService menuService;

    private final String loginTypePerffix = "loginType:";
    private final String menusPerffix = "menus:";

    @ResponseBody
    @RequestMapping("/findMenuByLogined")
    public String findMenuByLogined(String loginInfo) throws Exception {
        JSONArray jsonArray = new JSONArray();
        List<SysPermission> permissions = null;
        Principal principal = PrincipalUtil.getPrincipal(SecurityUtils.getSubject().getPrincipal());
        String loginTypeKey = loginTypePerffix + principal.getLoginName();
        String loginType = (String) jedisClient.getConvert(loginTypeKey);
        ILoginService loginService = chooseLoginMethod(loginType);
        String menusKey = menusPerffix + principal.getLoginName();
        permissions = menuService.doGetMenus(loginService,jedisClient,principal.getLoginName());
        String jsonStr = FastJsonTools.toJSONFeaturesString(permissions);
        return jsonStr;
    }

    public ILoginService chooseLoginMethod(String loginType) {
        ILoginService loginService = null;
        switch (loginType) {
            case ("nameLogin"):
                loginService = nameLoginService;
                break;
            case ("emailLogin"):
                loginService = emailLoginService;
                break;
            case ("mobileLogin"):
                loginService = mobileLoginService;
                break;
            default:
                loginService = nameLoginService;
                break;
        }
        return loginService;
    }

    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request, HttpServletResponse response){
        String menuName = request.getParameter("menuName");
        Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
        List<SysPermission> list = menuService.findPage(menuName,pageNum,pageSize);
        return "sys/menu_list";
    }
}
