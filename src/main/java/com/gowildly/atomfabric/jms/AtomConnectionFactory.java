package com.gowildly.atomfabric.jms;

import org.apache.log4j.Logger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public class AtomConnectionFactory implements ConnectionFactory {

    protected static Logger logger = Logger.getLogger(AtomConnectionFactory.class);

    private String url;

    public AtomConnectionFactory(String url) {
        logger.info("AtomConnectionFactory(" + url + ")");
        this.url = url;
    }

    public Connection createConnection() throws JMSException {
        logger.info("AtomConnectionFactory.createConnection()");
        return new AtomConnection(this.url);
    }

    public Connection createConnection(String username, String password) throws JMSException {
        logger.info("AtomConnectionFactory.createConnection(" + username + ", password)");
        return new AtomConnection(this.url, username, password);
    }
}
