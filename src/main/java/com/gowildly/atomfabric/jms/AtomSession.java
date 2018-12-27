package com.gowildly.atomfabric.jms;

import com.gowildly.atomfabric.sockets.AtomTopicClientEndpoint;
import org.apache.log4j.Logger;

import javax.jms.*;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class AtomSession implements Session {

    protected static Logger logger = Logger.getLogger(AtomSession.class);

    protected int ackMode = 0;
    protected Connection connection = null;
    protected HashMap<String, AtomTopicClientEndpoint> topicEndpoints = new HashMap<>();
    protected String url;

    public AtomSession() { logger.info("AtomSession()"); }

    public AtomSession(Connection connection, String url, boolean transacted, int ackMode) {
        logger.info("AtomSession(" + connection + ", " + url + ", " + transacted + ", " + ackMode + ")");
        this.connection = connection;
        this.ackMode = ackMode;
        this.url = url;
    }

    private AtomTopicClientEndpoint getEndpointConsumer(Destination destination, String messageSelector, boolean noLocal) {
        AtomTopicClientEndpoint atomTopicClientEndpoint = null;
        AtomTopic atomTopic = (AtomTopic)destination;

        try {
            if (topicEndpoints.containsKey(atomTopic.getTopicName())) {
                atomTopicClientEndpoint = topicEndpoints.get(atomTopic.getTopicName());
            } else {
                atomTopicClientEndpoint = new AtomTopicClientEndpoint(atomTopic, messageSelector, noLocal);

                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                try {
                    container.connectToServer(atomTopicClientEndpoint, new URI(this.url));
                } catch (URISyntaxException e) {
                    logger.error(e.getMessage());
                } catch (IOException eio) {
                    logger.error(eio.getMessage());
                } catch (DeploymentException de) {
                    logger.error(de.getMessage());
                }
            }
        } catch (JMSException je) {
            logger.error(je.getMessage());
        }

        atomTopic.setClientEndpoint(atomTopicClientEndpoint);

        return atomTopicClientEndpoint;
    }

    public MessageConsumer createConsumer(Destination destination) throws JMSException {
        logger.info("AtomSession.createConsumer(" + destination + ")");
        return createConsumer(destination, null, false);
    }

    public MessageConsumer createConsumer(Destination destination, String messageSelector) throws JMSException {
        logger.info("AtomSession.createConsumer(" + destination + ", " + messageSelector + ")");
        return createConsumer(destination, messageSelector, false);
    }

    public MessageConsumer createConsumer(Destination destination, String messageSelector, boolean noLocal) throws JMSException {
        logger.info("AtomSession.createConsumer(" + destination + ", " + messageSelector + ", " + noLocal + ")");
        return this.getEndpointConsumer(destination, messageSelector, noLocal);
    }

    public MessageProducer createProducer(Destination destination) throws JMSException {
        logger.info("AtomSession.createProducer(" + destination + ")");
        return this.getEndpointConsumer(destination, null, false);
    }

    public Queue createQueue(String queueName) throws JMSException {
        logger.info("AtomSession.createQueue(" + queueName + ")");
        throw new JMSException("Session.createQueue() not implemented.");
    }

    public Topic createTopic(String topicName) throws JMSException {
        logger.info("AtomSession.createTopic(" + topicName + ")");
        return new AtomTopic(topicName);
    }

    public QueueBrowser createBrowser(Queue queue) throws JMSException {
        logger.info("AtomSession.createBrowser(" + queue + ")");
        throw new JMSException("Session.createBrowser() not implemented.");
    }

    public QueueBrowser createBrowser(Queue queue, String messageSelector) throws JMSException {
        logger.info("AtomSession.createBrowser(" + queue + ", " + messageSelector + ")");
        throw new JMSException("Session.createBrowser() not implemented.");
    }

    public TemporaryQueue createTemporaryQueue() throws JMSException {
        logger.info("AtomSession.createTemporaryQueue()");
        throw new JMSException("Session.createTemporaryQueue() not implemented.");
    }

    public TemporaryTopic createTemporaryTopic() throws JMSException {
        logger.info("AtomSession.createTemporaryTopic()");
        throw new JMSException("Session.createTemporaryTopic() not implemented.");
    }

    public BytesMessage createBytesMessage() throws JMSException {
        logger.info("AtomSession.createBytesMessage()");
        throw new JMSException("Session.createBytesMessage() not implemented.");
    }

    public MapMessage createMapMessage() throws JMSException {
        logger.info("AtomSession.createMapMessage()");
        throw new JMSException("Session.createMapMessage() not implemented.");
    }

    public Message createMessage() throws JMSException {
        logger.info("AtomSession.createMessage()");
        throw new JMSException("Session.createMessage() not implemented.");
    }

    public ObjectMessage createObjectMessage() throws JMSException {
        logger.info("AtomSession.createObjectMessage()");
        throw new JMSException("Session.createObjectMessage() not implemented.");
    }

    public ObjectMessage createObjectMessage(java.io.Serializable obj) throws JMSException {
        logger.info("AtomSession.createObjectMessage(" + obj + ")");
        throw new JMSException("Session.createObjectMessage() not implemented.");
    }

    public StreamMessage createStreamMessage() throws JMSException {
        logger.info("AtomSession.createStreamMessage()");
        throw new JMSException("Session.createStreamMessage() not implemented.");
    }

    public TextMessage createTextMessage() throws JMSException {
        logger.info("AtomSession.createTextMessage()");
        return new AtomTextMessage();
    }

    public TextMessage createTextMessage(String text) throws JMSException {
        logger.info("AtomSession.createTextMessage(" + text + ")");
        return new AtomTextMessage(text);
    }

    public void close() throws JMSException {
        logger.info("AtomSession.close()");
        connection = null;
    }

    //// Not implemented as not needed for base implementation

    public MessageListener getMessageListener() throws JMSException {
        return null;
    }

    public boolean getTransacted() throws JMSException {
        return false;
    }

    public int getAcknowledgeMode() throws JMSException {
        return ackMode;
    }

    public TopicSubscriber createDurableSubscriber(Topic topic, String name) throws JMSException {
        throw new JMSException("Session.createDurableSubscriber() not implemented.");
    }

    public TopicSubscriber createDurableSubscriber(Topic topic, String name, String messageSelector, boolean noLocal) throws JMSException {
        throw new JMSException("Session.createDurableSubscriber() not implemented.");
    }

    public void commit() throws JMSException {
    }

    public void recover() throws JMSException {
    }

    public void rollback() throws JMSException {
    }

    public void run() {
    }

    public void setMessageListener(MessageListener listener) throws JMSException {
    }

    public void unsubscribe(String name) throws JMSException, InvalidDestinationException {
    }
}
