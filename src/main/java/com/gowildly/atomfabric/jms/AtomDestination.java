package com.gowildly.atomfabric.jms;

import org.apache.log4j.Logger;

import javax.jms.*;
import java.util.Vector;

public class AtomDestination implements Destination {

    protected static Logger logger = Logger.getLogger(AtomDestination.class);

    protected Vector producers = new Vector();
    protected Vector consumers = new Vector();
    protected boolean deleted = false;
    protected String name = "";

    class ProducerNode {
        public MessageProducer _obj;
        public Connection _conn;

        public ProducerNode(MessageProducer sender, Connection conn) {
            _obj = sender;
            _conn = conn;
        }
    }

    class ConsumerNode {
        public MessageConsumer _obj;
        public Connection _conn;

        public ConsumerNode(MessageConsumer consumer, Connection conn) {
            _obj = consumer;
            _conn = conn;
        }
    }

    public void addProducer(MessageProducer messageProducer, Connection connection) throws JMSException {
        logger.info("AtomDestination.addProducer(" + messageProducer + ", " + connection + ")");
        if (deleted) {
            throw new JMSException("Cannot use deleted topic");
        }

        ProducerNode prodNode = new ProducerNode(messageProducer, connection);
        producers.add(prodNode);
    }

    public void addConsumer(MessageConsumer messageConsumer, Connection connection) throws JMSException {
        logger.info("AtomDestination.addConsumer(" + messageConsumer + ", " + connection + ")");
        if (deleted) {
            throw new JMSException("Cannot use deleted topic");
        }

        logger.info("AtomDestination.addConsumer()");
        ConsumerNode consNode = new ConsumerNode(messageConsumer, connection);
        consumers.add(consNode);
    }

    public void removeProducer(MessageProducer messageProducer) throws JMSException {
        logger.info("AtomDestination.removeProducer(" + messageProducer + ")");
        int count = producers.size();

        for (int i = 0; i < count; i++) {
            ProducerNode producerNode = (ProducerNode) producers.elementAt(i);

            if (producerNode._obj == messageProducer) {
                try {
                    producers.removeElementAt(i);
                    return;
                } catch (Exception e) {
                }
            }
        }
    }

    public void removeConsumer(MessageConsumer messageConsumer) throws JMSException {
        logger.info("AtomDestination.removeConsumer(" + messageConsumer + ")");
        int count = consumers.size();

        for (int i = 0; i < count; i++) {
            ConsumerNode consumerNode = (ConsumerNode) consumers.elementAt(i);

            if (consumerNode._obj == messageConsumer) {
                try {
                    consumers.removeElementAt(i);
                    return;
                } catch (Exception e) {
                }
            }
        }
    }
}
