package com.chieftain.agile.controller.sys;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.chieftain.agile.common.auth.CustomizeShiroRealm;
import com.chieftain.agile.common.auth.Principal;
import com.chieftain.agile.common.auth.PrincipalUtil;
import com.chieftain.agile.common.cache.api.JedisClient;
import com.chieftain.agile.service.sys.ILoginService;
import com.chieftain.agile.utils.SpringContextHolder;
import com.google.code.kaptcha.impl.DefaultKaptcha;

/**
 * com.chieftain.junite.controller.sys [workset_idea_01]
 * Created by Richard on 2018/5/15
 *
 * @author Richard on 2018/5/15
 */
@Controller
public class SysController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private ILoginService nameLoginService;
    @Autowired
    private ILoginService emailLoginService;
    @Autowired
    private ILoginService mobileLoginService;

    @Autowired
    private JedisClient jedisClient;

    private final String loginTypePerffix = "loginType:";
    private final String menusPerffix = "menus:";

    @RequiresRoles("basic")
    @RequiresPermissions("logined:index")
    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (!parameterMap.isEmpty()) {
            if (parameterMap.containsKey("status")) {
                request.setAttribute("status", parameterMap.get("status")[0]);
            }
            if (parameterMap.containsKey("msg")) {
                request.setAttribute("msg", parameterMap.get("msg")[0]);
            }
        }
        return "index";
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/do_Login";
        } else {
            if (!parameterMap.isEmpty()) {
                if (parameterMap.containsKey("status")) {
                    request.setAttribute("status", parameterMap.get("status")[0]);
                }
                if (parameterMap.containsKey("msg")) {
                    request.setAttribute("msg", parameterMap.get("msg")[0]);
                }
            }
            return "redirect:/go_Login";
        }
    }

    @RequestMapping("/go_Login")
    public String goLogin(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/do_Login";
        } else {
            if (!parameterMap.isEmpty()) {
                if (parameterMap.containsKey("status")) {
                    request.setAttribute("status", parameterMap.get("status")[0]);
                }
                if (parameterMap.containsKey("msg")) {
                    request.setAttribute("msg", parameterMap.get("msg")[0]);
                }
            }
        }
        return "/login";
    }

    /**
     * @param request
     * @param response
     * @param attr
     * @param loginName
     * @param pwd
     * @param verify
     * @return
     */
    @RequestMapping("/do_Login")
    public String login(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr,
                        String loginName, String pwd, String verify, String loginType) {
        String verifyCode = request.getSession().getAttribute("vrifyCode").toString();
        CustomizeShiroRealm shiroRealm = (CustomizeShiroRealm) SpringContextHolder.getBean("customizeShiroRealm");
        Map<String, String> map = shiroRealm.authenticateUser(loginName, pwd, verify, verifyCode, loginType);
        request.setAttribute("status", map.get("status"));
        request.setAttribute("msg", map.get("msg"));
        request.setAttribute("loginName", loginName);
        request.setAttribute("pwd", pwd);
        request.setAttribute("loginType", loginType);
        attr.addAttribute("status", map.get("status"));
        attr.addAttribute("msg", map.get("msg"));
        attr.addFlashAttribute("loginName", loginName);
        attr.addFlashAttribute("pwd", pwd);
        attr.addFlashAttribute("loginType", loginType);
        if (SecurityUtils.getSubject().isAuthenticated()) {

        } else {
            return "redirect:/go_Login";
        }
        return "redirect:/index";
    }

    @RequiresAuthentication
    @RequestMapping("/doLogout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Principal principal = PrincipalUtil.getPrincipal(SecurityUtils.getSubject().getPrincipal());
        jedisClient.delConvert(loginTypePerffix+principal.getLoginName());
        jedisClient.delConvert(menusPerffix+principal.getLoginName());
        SecurityUtils.getSubject().logout();
        return "redirect:/index";
    }

    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到 session 中
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute("vrifyCode", createText);
            // 使用生产的验证码字符串返回一个 BufferedImage 对象并转为 byte 写入到 byte 数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义 response 输出类型为 image/jpeg 类型，使用 response 输出流输出图片的 byte 数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    @RequiresAuthentication
    @RequestMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        Principal principal = PrincipalUtil.getPrincipal(SecurityUtils.getSubject().getPrincipal());
        return "redirect:/";
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

    @RequiresAuthentication
    @ResponseBody
    @RequestMapping("userInfo")
    public String getUserInfo(HttpServletRequest request,HttpServletResponse response,String param) {
        Principal principal = PrincipalUtil.getPrincipal(SecurityUtils.getSubject().getPrincipal());
        return JSONObject.toJSONString(principal);
    }
}
