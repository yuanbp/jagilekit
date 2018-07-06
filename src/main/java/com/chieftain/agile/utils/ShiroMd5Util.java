package com.chieftain.agile.utils;

import javax.annotation.PostConstruct;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chieftain.agile.entity.sys.SysUser;

/**
 * com.chieftain.junite.utils [workset_idea_01]
 * Created by Richard on 2018/5/15
 *
 * @author Richard on 2018/5/15
 */
@Component
public class ShiroMd5Util {

    @Value("${shiro.credentials.hashIterations}")
    private int hashIterations;
    @Value("${shiro.credentials.hashAlgorithmName}")
    private String hashAlgorithmName;

    private static ShiroMd5Util shiroMd5Util;

    @PostConstruct
    public void initialization() {
        shiroMd5Util = this;
    }

    //添加user的密码加密方法
    public static String  encryMd5(SysUser user) {
        Object crdentials =user.getPwd();//密码原值
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());//以账号作为盐值
        SimpleHash hash = new SimpleHash(shiroMd5Util.hashAlgorithmName,crdentials,salt,shiroMd5Util.hashIterations);
        return hash.toString();
    }

    public static String  encryMd5(String pwd,String salt) {
        ByteSource byteSource = ByteSource.Util.bytes(salt);
        SimpleHash hash = new SimpleHash("MD5",pwd,byteSource,3);
        return hash.toString();
    }

    public static void main(String[] args) {
        System.out.println(encryMd5("123456","admin"));
    }

}
