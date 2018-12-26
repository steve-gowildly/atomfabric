package com.gowildly.atomfabric.jms;

import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.jms.*;
import java.util.Vector;

public class AtomSession implements Session {

    protected static Logger logger = Logger.getLogger(AtomSession.class);

    protected int ackMode = 0;
    protected Vector producers = new Vector();
    protected Vector consumers = new Vector();
    protected Connection connection = null;
    protected String url;

    public AtomSession() {
        logger.info("AtomSession()");
    }

    public AtomSession(Connection connection, String url, boolean transacted, int ackMode) {
        logger.info("AtomSession(" + connection + ", " + url + ", " + transacted + ", " + ackMode + ")");
        this.connection = connection;
        this.ackMode = ackMode;
        this.url = url;
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
        MessageConsumer consumer = AtomTopicSession._createSubscriber((Topic) destination, messageSelector, noLocal, this.connection, this.url);

        consumers.addElement(consumer);

        return consumer;
    }

    public MessageProducer createProducer(Destination destination) throws JMSException {
        logger.info("AtomSession.createProducer(" + destination + ")");
        MessageProducer producer = AtomTopicSession._createPublisher((Topic) destination, this.connection);

        producers.addElement(producer);

        return producer;
    }

    public Queue createQueue(String queueName) throws JMSException {
        logger.info("AtomSession.createQueue(" + queueName + ")");
        throw new NotImplementedException();
    }

    public Topic createTopic(String topicName) throws JMSException {
        logger.info("AtomSession.createTopic(" + topicName + ")");
        return AtomTopicSession._createTopic(topicName);
    }

    public QueueBrowser createBrowser(Queue queue) throws JMSException {
        logger.info("AtomSession.createBrowser(" + queue + ")");
        throw new NotImplementedException();
    }

    public QueueBrowser createBrowser(Queue queue, String messageSelector) throws JMSException {
        logger.info("AtomSession.createBrowser(" + queue + ", " + messageSelector + ")");
        throw new NotImplementedException();
    }

    public TemporaryQueue createTemporaryQueue() throws JMSException {
        logger.info("AtomSession.createTemporaryQueue()");
        throw new NotImplementedException();
    }

    public TemporaryTopic createTemporaryTopic() throws JMSException {
        logger.info("AtomSession.createTemporaryTopic()");
        throw new NotImplementedException();
    }

    public BytesMessage createBytesMessage() throws JMSException {
        logger.info("AtomSession.createBytesMessage()");
        throw new NotImplementedException();
    }

    public MapMessage createMapMessage() throws JMSException {
        logger.info("AtomSession.createMapMessage()");
        throw new NotImplementedException();
    }

    public Message createMessage() throws JMSException {
        logger.info("AtomSession.createMessage()");
        throw new NotImplementedException();
    }

    public ObjectMessage createObjectMessage() throws JMSException {
        logger.info("AtomSession.createObjectMessage()");
        throw new NotImplementedException();
    }

    public ObjectMessage createObjectMessage(java.io.Serializable obj) throws JMSException {
        logger.info("AtomSession.createObjectMessage(" + obj + ")");
        throw new NotImplementedException();
    }

    public StreamMessage createStreamMessage() throws JMSException {
        logger.info("AtomSession.createStreamMessage()");
        throw new NotImplementedException();
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
        int producerCount = producers.size();

        for (int i = 0; i < producerCount; i++) {
            MessageProducer messageProducer = (MessageProducer)producers.elementAt(i);

            try {
                messageProducer.close();
            } catch (Exception e) {
            }
        }

        int consumerCount = consumers.size();

        for (int i = 0; i < consumerCount; i++) {
            MessageConsumer messageConsumer = (MessageConsumer) consumers.elementAt(i);

            try {
                messageConsumer.close();
            } catch (Exception e) {
            }
        }

        producers = new Vector();
        consumers = new Vector();
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
        throw new NotImplementedException();
    }

    public TopicSubscriber createDurableSubscriber(Topic topic, String name, String messageSelector, boolean noLocal) throws JMSException {
        throw new NotImplementedException();
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
