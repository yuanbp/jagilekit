package com.chieftain.agile.common.auth.exception;

import java.io.Serializable;

/**
 * com.chieftain.agile.common.auth.exception [workset_idea_01]
 * Created by Richard on 2018/5/23
 *
 * @author Richard on 2018/5/23
 */
public class VerifyException extends Exception implements Serializable {

    private static final long serialVersionUID = 562303826298073928L;

    private String retCode;
    private String msgDes;

    public VerifyException(){
        super();
    }

    public VerifyException(String message) {
        super(message);
        msgDes = message;
    }

    public VerifyException(String retCd, String msgDes) {
        super();
        this.retCode = retCd;
        this.msgDes = msgDes;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMsgDes() {
        return msgDes;
    }

    public void setMsgDes(String msgDes) {
        this.msgDes = msgDes;
    }
}
