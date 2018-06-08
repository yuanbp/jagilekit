package com.chieftain.agile.common.auth;

import java.io.Serializable;

import com.chieftain.agile.utils.DozerUtil;

/**
 * com.chieftain.agile.common.auth [workset_idea_01]
 * Created by Richard on 2018/5/25
 *
 * @author Richard on 2018/5/25
 */
public class PrincipalUtil implements Serializable {

    private static final long serialVersionUID = 5519483936758267214L;

    public static Principal getPrincipal(Object object){
        Principal principal = DozerUtil.map(object, Principal.class);
        return principal;
    }
}
