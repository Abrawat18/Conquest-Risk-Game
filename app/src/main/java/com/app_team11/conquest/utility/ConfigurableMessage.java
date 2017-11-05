package com.app_team11.conquest.utility;

/**Configurable Message is the utility class which sends messages when required
 * Created by Vasu on 06-10-2017.
 */
public class ConfigurableMessage {

    private int msgCode;
    //msg code = 0 for error; =1 for success
    private String msgText;

    /**
     *
     * @param msgCode
     * @param msgText
     * 
     */
    public ConfigurableMessage(int msgCode, String msgText) {
        this.msgCode = msgCode;
        this.msgText = msgText;
    }

    /**
     * Returns the message code
     * @return msgcode
     */
    public int getMsgCode() {
        return msgCode;
    }

    /**
     * Sets the message code
     * @param msgCode
     */
    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    /**
     * Returns the message text
     * @return msgText
     */
    public String getMsgText() {
        return msgText;
    }

    /**
     * Sets the message text
     * @param msgText
     */
    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }
}
