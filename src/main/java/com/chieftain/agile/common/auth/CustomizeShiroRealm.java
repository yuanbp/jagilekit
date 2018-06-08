package com.chieftain.agile.common.auth;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chieftain.agile.common.auth.exception.VerifyException;
import com.chieftain.agile.common.cache.api.JedisClient;
import com.chieftain.agile.entity.sys.SysUser;
import com.chieftain.agile.service.sys.ILoginService;
import com.chieftain.agile.service.sys.ISysMenuService;
import com.chieftain.agile.service.sys.ISysUserService;
import com.chieftain.agile.utils.CustomizSerializationUtils;

/**
 * com.chieftain.junite.common.auth [workset_idea_01]
 * Created by Richard on 2018/5/14
 *
 * @author Richard on 2018/5/14
 */
@Component
public class CustomizeShiroRealm extends AuthorizingRealm {

    private Logger logger = LogManager.getLogger(CustomizeShiroRealm.class);

    private Map<String, String> userStateMap = new HashMap<String, String>() {{
        //未激活
        put("unactive", "0");
        //正常
        put("normal", "1");
        //已锁定
        put("locked", "2");
        //已删除
        put("deleted", "3");
    }};

    private Map<String,String> verifyExcpCode = new HashMap<String,String>(){{
        put("0","0");
        put("1","1");
    }};

    private Map<String,String> verifyExcpMsg = new HashMap<String,String>(){{
        put("0","Lost Verify Code.");
        put("1","VerifyCode Not Match.");
    }};

    @Value("${shiro.credentials.hashIterations}")
    private int hashIterations;
    @Value("${shiro.credentials.hashAlgorithmName}")
    private String hashAlgorithmName;

    private String authenticatedUser = "user";

    private final String loginTypePerffix = "loginType:";

    private final String menusPerffix = "menus:";

    public CustomizeShiroRealm customizeShiroRealm;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private JedisClient jedisClient;

    private ILoginService loginService;

    @Autowired
    private ILoginService nameLoginService;
    @Autowired
    private ILoginService emailLoginService;
    @Autowired
    private ILoginService mobileLoginService;

    @Autowired
    private ISysMenuService sysMenuService;

    @PostConstruct
    public void initialization() {
        customizeShiroRealm = this;
        setName("defaultRealm");
    }

    public Map<String, String> authenticateUser(String username, String password,String verifyCode,
                                                String savedVerify,String loginType) {
        Map<String, String> map = new HashMap<>();
        String msg = "";
        String status = "";
        try {
            if (!SecurityUtils.getSubject().isAuthenticated()) {
                if (username == null) {
                    throw new AuthenticationException("Username is null");
                }
                if (password == null) {
                    throw new AuthenticationException("Password is null");
                }
                if(null == verifyCode || null == savedVerify){
                    throw new VerifyException(verifyExcpCode.get("0"),verifyExcpMsg.get("0"));
                }
                if(!verifyCode.equals(savedVerify)){
                    throw new VerifyException(verifyExcpCode.get("1"),verifyExcpMsg.get("1"));
                }

                logger.info(String.format("Authenticating  user %s", username));
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                token.setRememberMe(false);
                jedisClient.setConvert(loginTypePerffix+username,loginType);
                SecurityUtils.getSubject().login(token);
                authenticatedUser = username;
            }
        } catch (DisabledAccountException e){
            msg = "账户已禁用";
        } catch (UnknownAccountException e) {
            msg = "用户名 / 密码错误";
        } catch (IncorrectCredentialsException e) {
            msg = "用户名 / 密码错误";
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败多次，账户锁定 5 分钟";
        } catch (AuthenticationException e) {
            msg = "其他错误：" + e.getMessage();
        }catch (VerifyException e) {
            if(e.getRetCode().equals("0")){
                msg = "验证码丢失，请尝试刷新验证码";
            }else if(e.getRetCode().equals("1")){
                msg = "验证码不匹配，请重新输入";
            }
        } catch (Exception e) {
            logger.error("@#####登陆异常:", e);
            msg = "登陆出现异常.";
        }
        if (StringUtils.isNotBlank(msg)) {
            status = "fail";
        } else {
            status = "success";
            msg = "success";
        }
        map.put("status", status);
        map.put("msg", msg);
        return map;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        try {
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

            String username = token.getUsername();
            String password = String.valueOf(token.getPassword());

            logger.info(String.format("Authenticating user %s", username));

            loginService = chooseLoginMethod(CustomizSerializationUtils.deserialize(jedisClient.getByte(loginTypePerffix+username)).toString());
            SysUser user = loginService.findByLogin(username);
            if (user == null) {
                // 抛出 帐号找不到异常
                throw new UnknownAccountException();
            }
            if (user.getPassword() == null) {
                throw new AuthenticationException("Authentication failed");
            }
            if (userStateMap.get("locked").equals(user.getUserstate())) {
                // 抛出 帐号锁定异常
                throw new LockedAccountException();
            }
            if(userStateMap.get("deleted").equals(user.getUserstate())){
                throw new DisabledAccountException("the account is disabled.");
            }
            return new SimpleAuthenticationInfo(
                    new Principal(user,false, CustomizSerializationUtils.deserialize(jedisClient.getByte(loginTypePerffix+username)).toString()),
                    user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        } catch (Exception e) {
            logger.error("Error while authenticating", e);
            throw new AuthenticationException("Authentication failed", e);
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        try {

            // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
            // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
            if (!SecurityUtils.getSubject().isAuthenticated()) {
                doClearCache(principals);
                SecurityUtils.getSubject().logout();
                return null;
            }

            if (principals == null) {
                throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
            }
            Collection<Principal> principalsList = principals.fromRealm(getName());
            if (principalsList.isEmpty()) {
                throw new AuthorizationException("Empty principals list!");
            }
            Object object = principals.getPrimaryPrincipal();
            Principal principal = PrincipalUtil.getPrincipal(object);
            String username = principal.getLoginName();

            loginService = chooseLoginMethod(CustomizSerializationUtils.deserialize(jedisClient.getByte(loginTypePerffix+username)).toString());

            Set<String> roles = new HashSet<>();
            roles.addAll(loginService.findLoginRoles(username));
            roles.add("basic");

            Set<String> permissions = new HashSet<>();
            roles.addAll(loginService.findLoginPermissions(username));
            permissions.add("logined:index");

            sysMenuService.doGetMenus(loginService,jedisClient,username);

            logger.info(String.format("Authorizing user %s with roles %s", username, roles));

            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.setRoles(roles);
            authorizationInfo.setStringPermissions(permissions);

            return authorizationInfo;

        } catch (Exception e) {
            logger.error("Error while authorizing", e);
            throw new AuthorizationException("Authorization failed", e);
        }
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

    public ILoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    public ILoginService chooseLoginMethod(String loginType){
        ILoginService loginService = null;
        switch (loginType){
            case ("nameLogin") :
                loginService = nameLoginService;
                break;
            case ("emailLogin") :
                loginService = emailLoginService;
                break;
            case ("mobileLogin") :
                loginService = mobileLoginService;
                break;
            default:
                loginService = nameLoginService;
                break;
        }
        return loginService;
    }
}
