package com.gowildly.atomfabric.jms;

import org.apache.log4j.Logger;

import javax.jms.*;

public class AtomMessageProducerAndConsumer implements MessageProducer, MessageConsumer {

    protected static Logger logger = Logger.getLogger(AtomMessageProducerAndConsumer.class);

    protected int deliveryMode = DeliveryMode.NON_PERSISTENT;
    protected int priority = Message.DEFAULT_PRIORITY;
    protected long timeToLive = Message.DEFAULT_TIME_TO_LIVE;
    protected boolean disableMessageID = true;
    protected boolean disableMessageTimestamp = true;
    protected Destination destination = null;
    protected MessageListener messageListener = null;
    protected Message message = null;
    protected String messageSelector = null;
    protected boolean clientBlocking = false;

    public AtomMessageProducerAndConsumer() {
        logger.info("AtomMessageProducerAndConsumer()");
    }

    public void send(Message message) throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.send(" + message + ")");
        this.send(destination, message, this.deliveryMode, this.priority, this.timeToLive);
    }

    public void send(Message message, int deliveryMode, int priority, long TTL) throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.send(" + message + ", " + deliveryMode + ", " + priority + ", " + TTL + ")");
        this.send(destination, message, deliveryMode, priority, TTL);
    }

    public void send(Destination destination, Message message) throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.send(" + destination + ", " + message + ")");
        this.send(destination, message, this.deliveryMode, this.priority, this.timeToLive);
    }

    public void send(Destination destination, Message message, int deliveryMode, int priority, long TTL) throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.send(" + destination + ", " + message + ", " + deliveryMode + ", " + priority + ", " + TTL + ")");
        try {
            message.setJMSExpiration(TTL);
            message.setJMSPriority(priority);
            message.setJMSDeliveryMode(deliveryMode);

            if (message instanceof TextMessage == false) {
                throw new JMSException("TextMessage is the only implemented message type.");
            }

            AtomTopic atomTopic = (AtomTopic)destination;
            atomTopic.publish(message);
        } catch (Exception e) {
            logger.error("AtomMessageProducerAndConsumer.send() Exception: " + e.getMessage());
        }
    }

    public synchronized void onMessage(Message message) throws Exception {
        logger.info("AtomMessageProducerAndConsumer.onMessage(" + message + ")");
        if (messageListener != null) {
            logger.info("AtomMessageProducerAndConsumer: calling client onMessage()");
            messageListener.onMessage(message);
        } else {
            throw new NullPointerException("MessageListener is null and not available to process message");
        }
    }

    public void setMessageListener(MessageListener messageListener) throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.setMessageListener(" + messageListener + ")");
        this.messageListener = messageListener;
    }

    public MessageListener getMessageListener() throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.getMessageListener()");
        return messageListener;
    }

    public String getMessageSelector() throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.getMessageSelector()");
        return messageSelector;
    }

    public Message receive() throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.receive()");
        return receive(0);
    }

    public synchronized Message receive(long timeout) throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.receive(" + timeout + ")");
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
        logger.info("AtomMessageProducerAndConsumer.receiveNoWait()");
        Message message = this.message;
        this.message = null;

        return message;
    }

    public void close() throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.close()");
        destination = null;
        messageListener = null;
    }

    public Destination getDestination() throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.getDestination()");
        return destination;
    }

    public int getDeliveryMode() throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.getDeliveryMode()");
        return deliveryMode;
    }

    public boolean getDisableMessageID() throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.getDisableMessageID()");
        return disableMessageID;
    }

    public boolean getDisableMessageTimestamp() throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.getDisableMessageTimestamp()");
        return disableMessageTimestamp;
    }

    public int getPriority() throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.getPriority()");
        return priority;
    }

    public long getTimeToLive() throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.getTimeToLive()");
        return timeToLive;
    }

    public void setDeliveryMode(int deliveryMode) throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.setDeliveryMode(" + deliveryMode + ")");
        this.deliveryMode = deliveryMode;
    }

    public void setDisableMessageID(boolean value) throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.setDisableMessageID(" + value + ")");
        this.disableMessageID = value;
    }

    public void setDisableMessageTimestamp(boolean value) throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.setDisableMessageTimestamp(" + value + ")");
        this.disableMessageTimestamp = value;
    }

    public void setPriority(int priority) throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.setPriority(" + priority + ")");
        this.priority = priority;
    }

    public void setTimeToLive(long timeToLive) throws JMSException {
        logger.info("AtomMessageProducerAndConsumer.setTimeToLive(" + timeToLive + ")");
        this.timeToLive = timeToLive;
    }
}
