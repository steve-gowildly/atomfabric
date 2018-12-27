package com.gowildly.atomfabric.jms;

import com.gowildly.atomfabric.sockets.AtomServerEndpoint;
import org.apache.log4j.Logger;
import org.glassfish.tyrus.server.Server;

import javax.jms.*;
import javax.websocket.DeploymentException;
import java.util.Optional;
import java.util.Vector;

public class AtomConnection implements Connection {

    protected static Logger logger = Logger.getLogger(AtomConnection.class);

    protected Vector sessions = new Vector();
    protected String clientId = "ATOMFABRIC_CLIENTID";
    protected String url;
    protected String username;
    protected String password;
    protected ExceptionListener exceptListener = null;
    protected ConnectionMetaData metaData = null;
    protected Server server;

    public AtomConnection(String url) {
        logger.info("AtomConnection(" + url + ")");
        this.url = url;
    }

    public AtomConnection(String url, String username, String password) {
        logger.info("AtomConnection(" + url + ", " + username + ", " + password + ")");
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Session createSession(boolean trans, int ackMode) throws JMSException {
        logger.info("AtomConnection.createSession(" + trans + ", " + ackMode + ")");
        AtomSession session = new AtomSession(this, url, trans, ackMode);
        sessions.addElement(session);

        return session;
    }

    public ConnectionConsumer createConnectionConsumer(Destination destination, String messageSelector, ServerSessionPool sessionPool, int maxMessages) throws JMSException {
        logger.info("AtomConnection.createConnectionConsumer(" + destination + ", " + messageSelector + ", " + sessionPool + ", " + maxMessages + ")");
        throw new JMSException("Connection.createConnectionConsumer() not implemented.");
    }

    public ConnectionConsumer createDurableConnectionConsumer(Topic topic, String subscriptionName, String messageSelector, ServerSessionPool sessionPool, int maxMessages) throws JMSException {
        logger.info("AtomConnection.createDurableConnectionConsumer(" + topic + ", " + subscriptionName + ", " + messageSelector + ", " + sessionPool + ", " + maxMessages + ")");
        throw new JMSException("Connection.createDurableConnectionConsumer() not implemented.");
    }

    public String getClientID() throws JMSException {
        logger.info("AtomConnection.getClientID()");
        return clientId;
    }

    public ExceptionListener getExceptionListener() throws JMSException {
        logger.info("AtomConnection.getExceptionListener()");
        return exceptListener;
    }

    public ConnectionMetaData getMetaData() throws JMSException {
        logger.info("AtomConnection.getMetaData()");
        return metaData;
    }

    public void setClientID(String clientId) throws JMSException {
        logger.info("AtomConnection.setClientID(" + clientId + ")");
        this.clientId = clientId;
    }

    public void setExceptionListener(ExceptionListener exceptionListener) throws JMSException {
        logger.info("AtomConnection.setExceptionListener(" + exceptionListener + ")");
        this.exceptListener = exceptionListener;
    }

    public void start() throws JMSException {
        logger.info("AtomConnection.start()");
        server = new Server(
                Optional.ofNullable(System.getenv("HOSTNAME")).orElse("localhost"),
                Integer.parseInt(Optional.ofNullable(System.getenv("PORT")).orElse("8080")),
                "",
                null,
                AtomServerEndpoint.class);

        try {
            server.start();
        } catch (DeploymentException e) {
            logger.error(e.getMessage());
        }
    }

    public void stop() throws JMSException {
        logger.info("AtomConnection.stop()");

        if (server != null) {
            server.stop();
        }
    }

    public void close() throws JMSException {
        logger.info("AtomConnection.close()");
        int count = sessions.size();

        for (int i = 0; i < count; i++) {
            AtomSession session = (AtomSession) sessions.elementAt(i);

            try {
                session.close();
            } catch (Exception e) {
            }
        }

        sessions = new Vector();
        clientId = "ATOMFABRIC_CLIENTID";
        exceptListener = null;
        metaData = null;
    }
}
