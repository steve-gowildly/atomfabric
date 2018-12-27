package com.gowildly.atomfabric.jms;

import javax.jms.*;

public class AtomTopicPublisherAndSubscriber extends AtomMessageProducerAndConsumer implements TopicPublisher, TopicSubscriber {
    private Topic topic;
    private boolean noLocal;

    public AtomTopicPublisherAndSubscriber(Topic topic, String messageSelector, boolean noLocal) {
        logger.info("AtomTopicPublisherAndSubscriber(" + topic + ", " + messageSelector + ", " + noLocal + ")");
        this.topic = topic;
        this.messageSelector = messageSelector;
        this.noLocal = noLocal;
        this.destination = topic;
    }

    public void publish(Message message) throws JMSException {
        logger.info("AtomTopicPublisherAndSubscriber.publish(" + message + ")");
        super.send(destination, message);
    }

    public void publish(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        logger.info("AtomTopicPublisherAndSubscriber.publish(" + message + ", " + deliveryMode + ", " + priority + ", " + timeToLive + ")");
        super.send(destination, message, deliveryMode, priority, timeToLive);
    }

    public void publish(Topic topic, Message message) throws JMSException {
        logger.info("AtomTopicPublisherAndSubscriber.publish(" + topic + ", " + message + ")");
        super.send(topic, message);
    }

    public void publish(Topic topic, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        logger.info("AtomTopicPublisherAndSubscriber.publish(" + topic + ", " + message + ", " + deliveryMode + ", " + priority + ", " + timeToLive + ")");
        super.send(topic, message, deliveryMode, priority, timeToLive);
    }

    public void close() throws JMSException {
        logger.info("AtomTopicPublisherAndSubscriber.close()");
        super.close();

        this.topic = null;
        this.messageListener = null;
        this.messageSelector = null;
    }

    public boolean getNoLocal() throws JMSException {
        logger.info("AtomTopicPublisherAndSubscriber.getNoLocal()");
        return noLocal;
    }

    public Topic getTopic() throws JMSException {
        logger.info("AtomTopicPublisherAndSubscriber.getTopic()");
        return topic;
    }
}
