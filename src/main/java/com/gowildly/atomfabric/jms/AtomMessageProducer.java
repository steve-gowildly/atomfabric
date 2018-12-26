package com.gowildly.atomfabric.jms;

import org.apache.log4j.Logger;

import javax.jms.*;

public class AtomMessageProducer implements MessageProducer {

    protected static Logger logger = Logger.getLogger(AtomMessageProducer.class);

    protected int deliveryMode = DeliveryMode.NON_PERSISTENT;
    protected int priority = Message.DEFAULT_PRIORITY;
    protected long timeToLive = Message.DEFAULT_TIME_TO_LIVE;
    protected boolean disableMessageID = true;
    protected boolean disableMessageTimestamp = true;
    protected Destination destination = null;

    public AtomMessageProducer() {
        logger.info("AtomMessageProducer()");
    }

    public void send(Message message) throws JMSException {
        logger.info("AtomMessageProducer.send(" + message + ")");
        this.send(destination, message, this.deliveryMode, this.priority, this.timeToLive);
    }

    public void send(Message message, int deliveryMode, int priority, long TTL) throws JMSException {
        logger.info("AtomMessageProducer.send(" + message + ", " + deliveryMode + ", " + priority + ", " + TTL + ")");
        this.send(destination, message, deliveryMode, priority, TTL);
    }

    public void send(Destination destination, Message message) throws JMSException {
        logger.info("AtomMessageProducer.send(" + destination + ", " + message + ")");
        this.send(destination, message, this.deliveryMode, this.priority, this.timeToLive);
    }

    public void send(Destination destination, Message message, int deliveryMode, int priority, long TTL) throws JMSException {
        logger.info("AtomMessageProducer.send(" + destination + ", " + message + ", " + deliveryMode + ", " + priority + ", " + TTL + ")");
        try {
            message.setJMSExpiration(TTL);
            message.setJMSPriority(priority);
            message.setJMSDeliveryMode(deliveryMode);

            if (message instanceof BytesMessage) {
                ((BytesMessage) message).reset();
            } else if (message instanceof StreamMessage) {
                ((StreamMessage) message).reset();
            }

            Topic topic = (Topic) destination;
            if (topic != null) {
                AtomTopicPublisher._publish(topic, message, deliveryMode, priority, TTL);
            }
        } catch (Exception e) {
            logger.error("AtomMessageProducer.send() Exception: " + e.getMessage());
        }
    }

    public Destination getDestination() throws JMSException {
        logger.info("AtomMessageProducer.getDestination()");
        return destination;
    }

    public int getDeliveryMode() throws JMSException {
        logger.info("AtomMessageProducer.getDeliveryMode()");
        return deliveryMode;
    }

    public boolean getDisableMessageID() throws JMSException {
        logger.info("AtomMessageProducer.getDisableMessageID()");
        return disableMessageID;
    }

    public boolean getDisableMessageTimestamp() throws JMSException {
        logger.info("AtomMessageProducer.getDisableMessageTimestamp()");
        return disableMessageTimestamp;
    }

    public int getPriority() throws JMSException {
        logger.info("AtomMessageProducer.getPriority()");
        return priority;
    }

    public long getTimeToLive() throws JMSException {
        logger.info("AtomMessageProducer.getTimeToLive()");
        return timeToLive;
    }

    public void setDeliveryMode(int deliveryMode) throws JMSException {
        logger.info("AtomMessageProducer.setDeliveryMode(" + deliveryMode + ")");
        this.deliveryMode = deliveryMode;
    }

    public void setDisableMessageID(boolean value) throws JMSException {
        logger.info("AtomMessageProducer.setDisableMessageID(" + value + ")");
        this.disableMessageID = value;
    }

    public void setDisableMessageTimestamp(boolean value) throws JMSException {
        logger.info("AtomMessageProducer.setDisableMessageTimestamp(" + value + ")");
        this.disableMessageTimestamp = value;
    }

    public void setPriority(int priority) throws JMSException {
        logger.info("AtomMessageProducer.setPriority(" + priority + ")");
        this.priority = priority;
    }

    public void setTimeToLive(long timeToLive) throws JMSException {
        logger.info("AtomMessageProducer.setTimeToLive(" + timeToLive + ")");
        this.timeToLive = timeToLive;
    }

    public void close() throws JMSException {
        logger.info("AtomMessageProducer.close()");
        ((AtomDestination) destination).removeProducer(this);
        destination = null;
    }
}
