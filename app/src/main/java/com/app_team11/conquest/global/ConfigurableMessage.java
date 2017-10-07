package com.app_team11.conquest.global;

public class ConfigurableMessage {

    private int msgCode;
    //msg code = 0 for error; =1 for success
    private String msgText;

    public ConfigurableMessage(int msgCode, String msgText) {
        this.msgCode = msgCode;
        this.msgText = msgText;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }
}
