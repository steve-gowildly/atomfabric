package com.gowildly.atomfabric.sockets;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(
        value = "/atom/{atom}"
)

public class AtomServerEndpoint {

    protected static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AtomServerEndpoint.class);

    private static final Set<String> atoms = new ConcurrentSkipListSet<>();
    private String atom;
    private Session s;

    @OnOpen
    public void userConnectedCallback(@PathParam("atom") String atom, Session s) {
        logger.info("AtomServerEndpoint.userConnectedCallback(" + atom + ", " + s + ")");
        this.s = s;
        s.getUserProperties().put("atom", atom);
        this.atom = atom;
        atoms.add(atom);
    }

    @OnMessage
    public void msgReceived(String document, Session s) {
        logger.info("AtomServerEndpoint.msgReceived(" + document + ", " + s + ")");
        Predicate<Session> filterCriteria = (session) -> !session.getUserProperties().get("atom").equals(atom);

        s.getOpenSessions().stream()
                .filter(filterCriteria)
                .forEach((session) -> session.getAsyncRemote().sendText(document));
    }

    @OnClose
    public void onCloseCallback() {
        logger.info("AtomServerEndpoint.onCloseCallback()");
        try {
            atoms.remove(this.atom);
            s.getOpenSessions().stream()
                    .filter((sn) -> sn.isOpen())
                    .forEach((session) -> session.getAsyncRemote().sendText("bye"));

        } catch (Exception ex) {
            Logger.getLogger(AtomServerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
