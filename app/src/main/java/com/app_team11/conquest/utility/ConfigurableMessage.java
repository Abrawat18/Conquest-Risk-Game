package com.app_team11.conquest.utility;

/**
 * Configurable Message is the utility class which sends messages when required
 * Created by Vasu on 06-10-2017.
 */
public class ConfigurableMessage {

    private int msgCode;
    //msg code = 0 for error; =1 for success
    private String msgText;

    /**
     *Passes the message code and message text
     * @param msgCode this code defines the type of message
     * @param msgText depending on code the text of the message is passed
     * 
     */
    public ConfigurableMessage(int msgCode, String msgText) {
        this.msgCode = msgCode;
        this.msgText = msgText;
    }

    /**
     * Returns the message code
     * @return msgcode this code defines the type of message
     */
    public int getMsgCode() {
        return msgCode;
    }

    /**
     * Sets the message code
     * @param msgCode this code defines the type of message
     */
    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    /**
     * Returns the message text
     * @return msgText depending on code the text of the message is passed
     */
    public String getMsgText() {
        return msgText;
    }

    /**
     * Sets the message text
     * @param msgText  depending on code the text of the message is passed
     */
    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }
}
