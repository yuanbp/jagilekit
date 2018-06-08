package com.chieftain.agile.controller.sys;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chieftain.agile.entity.sys.SysUserTest;
import com.chieftain.agile.service.sys.ISysUserTestService;

/**
 * com.chieftain.junite.controller [workset_idea_01]
 * Created by Richard on 2018/5/9
 *
 * @author Richard on 2018/5/9
 */
@Controller
@RequestMapping(value = "sysuser")
public class SysUserTestController {

    private static final Logger LOGGER = LogManager.getLogger(SysUserTestController.class);

    @Value("${application.message:this is sysuser page}")
    private String message="this is sysuser page";

    @Autowired
    private ISysUserTestService sysUserTestService;

    @RequestMapping(value = "findList/{name}",method = RequestMethod.GET)
    @ResponseBody
    public List<SysUserTest> findList(@PathVariable String name, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        LOGGER.debug("@#####name:" + name);
        List<SysUserTest> users = sysUserTestService.findUserByName(name);
        return users;
    }

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, HttpServletResponse response, String name){
        List<SysUserTest> users = sysUserTestService.findUserByName(name);
        request.setAttribute("users",users);
        request.setAttribute("time", new Date());
        request.setAttribute("message", "sysuser list");
        return "/sys/sysuser";
    }
}
