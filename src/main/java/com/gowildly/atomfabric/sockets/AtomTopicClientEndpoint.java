package com.gowildly.atomfabric.sockets;

import com.gowildly.atomfabric.jms.AtomTextMessage;
import com.gowildly.atomfabric.jms.AtomTopicSubscriber;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.websocket.*;

@ClientEndpoint
public class AtomTopicClientEndpoint extends AtomTopicSubscriber {

    public AtomTopicClientEndpoint(Topic topic, String messageSelector, boolean noLocal) {
        super(topic, messageSelector, noLocal);
    }

    @OnOpen
    public void onOpen(Session userSession) {
        logger.info("AtomClientEndpoint.onOpen(" + userSession + ")");
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        logger.info("AtomClientEndpoint.onClose(" + userSession + ", " + reason + ")");

        try {
            this.close();
        } catch (JMSException e) {
            logger.error(e.getMessage());
        }
    }

    @OnMessage
    public void onMessage(String document) {
        logger.info("AtomClientEndpoint.onMessage(" + document + ")");
        try {
            this.onMessage(new AtomTextMessage(document));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
