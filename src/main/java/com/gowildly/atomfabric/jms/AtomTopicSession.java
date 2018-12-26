package com.gowildly.atomfabric.jms;

import com.gowildly.atomfabric.sockets.AtomTopicClientEndpoint;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.jms.*;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

public class AtomTopicSession extends AtomSession implements TopicSession {

    public AtomTopicSession(Connection connection, boolean transacted, int ackMode) {
        logger.info("AtomTopicSession(" + connection + ", " + transacted + ", " + ackMode + ")");
        this.connection = connection;
        this.ackMode = ackMode;
    }

    public static TopicPublisher _createPublisher(Topic topic, Connection connection) throws JMSException {
        logger.info("AtomTopicSession._createPublisher(" + topic + ", " + connection + ")");
        AtomTopicPublisher pub = null;

        if (topic != null) {
            AtomTopic atomTopic = (AtomTopic)topic;
            pub = new AtomTopicPublisher(atomTopic);
            atomTopic.addPublisher(pub, connection);
        }

        return pub;
    }

    public static TopicSubscriber _createSubscriber(Topic topic, String messageSelector, boolean noLocal, Connection connection, String url) throws JMSException {
        logger.info("AtomTopicSession._createSubscriber(" + topic + ", " + messageSelector + ", " + noLocal + ", " + connection + ")");
        AtomTopicClientEndpoint atomTopicClientEndpoint = null;

        if (topic != null) {
            AtomTopic atomTopic = (AtomTopic)topic;
            atomTopicClientEndpoint = new AtomTopicClientEndpoint(atomTopic, messageSelector, noLocal);
            atomTopic.addSubscriber(atomTopicClientEndpoint, connection);

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            try {
                container.connectToServer(atomTopicClientEndpoint, new URI(url));
            } catch (URISyntaxException e) {
                logger.error(e.getMessage());
            } catch (IOException eio) {
                logger.error(eio.getMessage());
            } catch (DeploymentException de) {
                logger.error(de.getMessage());
            }
        }

        return atomTopicClientEndpoint;
    }

    public static Topic _createTopic(String topicName) throws JMSException {
        logger.info("AtomTopicSession._createTopic(" + topicName + ")");
        Topic topic = new AtomTopic(topicName);

        return topic;
    }

    public TopicSubscriber createDurableSubscriber(Topic topic, String name) throws JMSException {
        logger.info("AtomTopicSession.createDurableSubscriber(" + topic + ", " + name + ")");
        return super.createDurableSubscriber(topic, name, null, false);
    }

    public TopicSubscriber createDurableSubscriber(Topic topic,
                                                   String name,
                                                   String messageSelector,
                                                   boolean noLocal) throws JMSException {
        logger.info("AtomTopicSession.createDurableSubscriber(" + topic + ", " + name + ", " + messageSelector + ", " + noLocal + ")");
        return super.createDurableSubscriber(topic, name, messageSelector, noLocal);
    }

    public TopicPublisher createPublisher(Topic topic) throws JMSException {
        logger.info("AtomTopicSession.createTopic(" + topic + ")");
        return (TopicPublisher) super.createProducer(topic);
    }

    public TopicSubscriber createSubscriber(Topic topic) throws JMSException {
        logger.info("AtomTopicSession.createSubscriber(" + topic + ")");
        return (TopicSubscriber) super.createConsumer(topic, null, false);
    }

    public TopicSubscriber createSubscriber(Topic topic, String messageSelector, boolean noLocal) throws JMSException {
        logger.info("AtomTopicSession.createSubscriber(" + topic + ", " + messageSelector + ", " + noLocal + ")");
        return (TopicSubscriber) super.createConsumer(topic, messageSelector, noLocal);
    }

    public TemporaryTopic createTemporaryTopic() throws JMSException {
        logger.info("AtomTopicSession.createTemporaryTopic()");
        return super.createTemporaryTopic();
    }

    public Topic createTopic(String topicName) throws JMSException {
        logger.info("AtomTopicSession.createTopic(" + topicName + ")");
        return super.createTopic(topicName);
    }

    public void unsubscribe(String name) throws JMSException, InvalidDestinationException {
        logger.info("AtomTopicSession.unsubscribe(" + name + ")");
    }
}
