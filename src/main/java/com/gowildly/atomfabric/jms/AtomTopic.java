package com.gowildly.atomfabric.jms;

import javax.jms.*;
import java.io.IOException;

public class AtomTopic extends AtomDestination implements Topic {

    public AtomTopic(String name) {
        logger.info("AtomTopic(" + name + ")");
        this.name = name;
    }

    public void addPublisher(TopicPublisher topicPublisher, Connection connection) throws JMSException {
        logger.info("AtomTopic.addPublisher(" + topicPublisher + ", " + connection + ")");
        super.addProducer(topicPublisher, connection);
    }

    public void addSubscriber(TopicSubscriber topicSubscriber, Connection connection) throws JMSException {
        logger.info("AtomTopic.addSubscriber(" + topicSubscriber + ", " + connection + ")");
        super.addConsumer(topicSubscriber, connection);
    }

    public void publish(Message message) throws IOException, JMSException {
        logger.info("AtomTopic.publish(" + message + ")");
        if (deleted) {
            throw new JMSException("Cannot use deleted topic");
        }

        if (message == null) {
            logger.info("AtomTopic:publish() message object is null");
            return;
        }

        try {
            AtomMessage ljmsMessage = (AtomMessage) message;
            ljmsMessage.setDestinationName(this.name);
            ljmsMessage.setMessageType(AtomMessage.PUB_SUB_MESSAGE);
            ljmsMessage.setJMSDestination(this);
            ljmsMessage.setSent(true);

            // Notify clients
            // TODO
        } catch (Exception e) {
            logger.error("AtomTopic.publish Exception: " + e.getMessage());
        }
    }

    public String getTopicName() throws JMSException {
        logger.info("AtomTopic.getTopicName()");
        if (deleted) {
            throw new JMSException("Cannot use deleted topic");
        }

        return name;
    }
}
