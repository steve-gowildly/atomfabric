package com.gowildly.atomfabric.jms;

import com.gowildly.atomfabric.sockets.AtomTopicClientEndpoint;

import javax.jms.*;
import java.io.IOException;

public class AtomTopic extends AtomDestination implements Topic {

    private AtomTopicClientEndpoint atomTopicClientEndpoint;

    public AtomTopic(String name) {
        logger.info("AtomTopic(" + name + ")");
        this.name = name;
    }

    public void publish(Message message) throws IOException, JMSException {
        logger.info("AtomTopic.publish(" + message + ")");

        if (message == null) {
            logger.info("AtomTopic:publish() message object is null");
            return;
        }

        if (message instanceof TextMessage == false) {
            throw new JMSException("TextMessage is the only implemented message type.");
        }

        TextMessage textMessage = (TextMessage)message;

        if (atomTopicClientEndpoint != null) {
            atomTopicClientEndpoint.sendMessage(textMessage.getText());
        } else {
            logger.error("Client Endpoint is null so message cannot be sent: " + textMessage.getText());
        }
    }

    public String getTopicName() throws JMSException {
        logger.info("AtomTopic.getTopicName()");

        return name;
    }

    public void setClientEndpoint(AtomTopicClientEndpoint atomTopicClientEndpoint) {
        this.atomTopicClientEndpoint = atomTopicClientEndpoint;
    }

    public AtomTopicClientEndpoint getClientEndpoint() {
        return this.atomTopicClientEndpoint;
    }
}
