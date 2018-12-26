package com.gowildly.atomfabric.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

public class AtomTopicSubscriber extends AtomMessageConsumer implements TopicSubscriber {

    private Topic topic;
    private boolean noLocal;

    public AtomTopicSubscriber(Topic topic, String messageSelector, boolean noLocal) {
        logger.info("AtomTopicSubscriber(" + topic + ", " + messageSelector + ", " + noLocal + ")");
        this.topic = topic;
        this.messageSelector = messageSelector;
        this.noLocal = noLocal;
        this.destination = topic;
    }

    public void onMessage(Message message) throws Exception {
        logger.info("AtomTopicSubscriber.onMessage(" + message + ")");
        super.onMessage(message);
    }

    public void close() throws JMSException {
        logger.info("AtomTopicSubscriber.close()");
        super.close();

        this.topic = null;
        this.messageListener = null;
        this.messageSelector = null;
    }

    public boolean getNoLocal() throws JMSException {
        logger.info("AtomTopicSubscriber.getNoLocal()");
        return noLocal;
    }

    public Topic getTopic() throws JMSException {
        logger.info("AtomTopicSubscriber.getTopic()");
        return topic;
    }
}
