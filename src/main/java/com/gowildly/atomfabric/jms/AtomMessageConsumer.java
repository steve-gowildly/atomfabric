package com.gowildly.atomfabric.jms;

import org.apache.log4j.Logger;

import javax.jms.*;

public class AtomMessageConsumer implements MessageConsumer {

    protected static Logger logger = Logger.getLogger(AtomMessageConsumer.class);

    protected Destination destination;
    protected MessageListener messageListener = null;
    protected Message message = null;
    protected String messageSelector = null;
    protected boolean clientBlocking = false;

    public AtomMessageConsumer() {
        logger.info("AtomMessageConsumer()");
    }

    public synchronized void onMessage(Message message) throws Exception {
        logger.info("AtomMessageConsumer.onMessage(" + message + ")");
        if (messageListener != null) {
            logger.info("AtomMessageConsumer: calling client onMessage()");
            messageListener.onMessage(message);
        } else {
            throw new NullPointerException("MessageListener is null and not available to process message");
        }
    }

    public void close() throws JMSException {
        logger.info("AtomMessageConsumer.close()");
        ((AtomDestination) destination).removeConsumer(this);
        destination = null;
        messageListener = null;
    }

    public void setMessageListener(MessageListener messageListener) throws JMSException {
        logger.info("AtomMessageConsumer.setMessageListener(" + messageListener + ")");
        this.messageListener = messageListener;
    }

    public MessageListener getMessageListener() throws JMSException {
        logger.info("AtomMessageConsumer.getMessageListener()");
        return messageListener;
    }

    public String getMessageSelector() throws JMSException {
        logger.info("AtomMessageConsumer.getMessageSelector()");
        return messageSelector;
    }

    public Message receive() throws JMSException {
        logger.info("AtomMessageConsumer.receive()");
        return receive(0);
    }

    public synchronized Message receive(long timeout) throws JMSException {
        logger.info("AtomMessageConsumer.receive(" + timeout + ")");
        try {
            this.clientBlocking = true;
            while (this.message == null) {
                wait(timeout);
            }
            this.clientBlocking = false;

            Message message = this.message;
            this.message = null;

            return message;
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized Message receiveNoWait() throws JMSException {
        logger.info("AtomMessageConsumer.receiveNoWait()");
        Message message = this.message;
        this.message = null;

        return message;
    }
}