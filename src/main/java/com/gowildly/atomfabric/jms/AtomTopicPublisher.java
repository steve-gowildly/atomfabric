package com.gowildly.atomfabric.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.jms.TopicPublisher;

public class AtomTopicPublisher extends AtomMessageProducer implements TopicPublisher {

    public AtomTopicPublisher(Topic topic) {
        logger.info("AtomTopicPublisher(" + topic + ")");
        destination = topic;
    }

    public static void _publish(Topic topic, Message message, int deliveryMode, int priority, long timeToLive) throws Exception {
        logger.info("AtomTopicPublisher._publish(" + topic + ", " + message + ", " + deliveryMode + ", " + priority + ", " + timeToLive + ")");
        AtomTopic atomTopic = (AtomTopic) topic;
        atomTopic.publish(message);
    }

    public void close() throws JMSException {
        logger.info("AtomTopicPublisher.close()");
        super.close();
        destination = null;
    }

    public Topic getTopic() throws JMSException {
        logger.info("AtomTopicPublisher.getTopic()");
        return (Topic) destination;
    }

    public void publish(Message message) throws JMSException {
        logger.info("AtomTopicPublisher.publish(" + message + ")");
        super.send(destination, message);
    }

    public void publish(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        logger.info("AtomTopicPublisher.publish(" + message + ", " + deliveryMode + ", " + priority + ", " + timeToLive + ")");
        super.send(destination, message, deliveryMode, priority, timeToLive);
    }

    public void publish(Topic topic, Message message) throws JMSException {
        logger.info("AtomTopicPublisher.publish(" + topic + ", " + message + ")");
        super.send(topic, message);
    }

    public void publish(Topic topic, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        logger.info("AtomTopicPublisher.publish(" + topic + ", " + message + ", " + deliveryMode + ", " + priority + ", " + timeToLive + ")");
        super.send(topic, message, deliveryMode, priority, timeToLive);
    }
}
