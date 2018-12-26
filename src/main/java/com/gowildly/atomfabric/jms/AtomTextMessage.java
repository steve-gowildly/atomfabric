package com.gowildly.atomfabric.jms;

import org.apache.log4j.Logger;

import javax.jms.JMSException;
import javax.jms.MessageNotWriteableException;
import javax.jms.TextMessage;

public class AtomTextMessage extends AtomMessage implements TextMessage {

    protected static Logger logger = Logger.getLogger(AtomTextMessage.class);

    protected String text = "";

    public AtomTextMessage() {
        logger.info("AtomTextMessage()");
    }

    public AtomTextMessage(String text) {
        logger.info("AtomTextMessage(" + text + ")");
        this.text = new String(text);
    }

    public void setText(String text) throws JMSException, MessageNotWriteableException {
        logger.info("AtomTextMessage.setText(" + text + ")");
        if (state == SENT_STATE) {
            throw new MessageNotWriteableException("Cannot change body after message is sent");
        }

        this.text = new String(text);
    }

    public String getText() throws JMSException {
        logger.info("AtomTextMessage.getText()");
        return text;
    }
}

