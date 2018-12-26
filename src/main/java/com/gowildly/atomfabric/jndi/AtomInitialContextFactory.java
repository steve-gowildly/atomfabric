package com.gowildly.atomfabric.jndi;

import com.gowildly.atomfabric.jms.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

import org.apache.log4j.Logger;

public class AtomInitialContextFactory implements InitialContextFactory {

    static Logger logger = Logger.getLogger(AtomInitialContextFactory.class);

    private String CONNECTION_FACTORY_PREFIX = "connectionfactory.";
    private String DESTINATION_PREFIX = "destination.";
    private String TOPIC_PREFIX = "topic.";

    public Context getInitialContext(Hashtable environment) throws NamingException {
        Map data = new ConcurrentHashMap();
        String file = null;
        logger.info("Getting properties file information");
        String fileName = (environment.containsKey(Context.PROVIDER_URL))
                ? (String) environment.get(Context.PROVIDER_URL) : System.getProperty(Context.PROVIDER_URL);
        try {
            if (environment.containsKey(Context.PROVIDER_URL)) {
                file = (String) environment.get(Context.PROVIDER_URL);
            } else {
                file = System.getProperty(Context.PROVIDER_URL);
            }
            if (file != null) {
                // Load the properties specified
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                Properties p = new Properties();
                try {
                    p.load(inputStream);
                } finally {
                    inputStream.close();
                }
                for (Map.Entry me : p.entrySet()) {
                    String key = (String) me.getKey();
                    String value = (String) me.getValue();
                    environment.put(key, value);
                    if (System.getProperty(key) == null) {
                        System.setProperty(key, value);
                    }
                }
            } else {
                throw new NamingException("No Provider URL specified.");
            }
        } catch (IOException ioe) {
            NamingException ne = new NamingException("Unable to load property file:" + file + ".");
            ne.setRootCause(ioe);
            throw ne;
        }
        try {
            createConnectionFactories(data, environment);
        } catch (MalformedURLException e) {
            NamingException ne = new NamingException();
            ne.setRootCause(e);
            throw ne;
        }

        logger.info("Creating destinations");
        createDestinations(data, environment);
        logger.info("Creating topics");
        createTopics(data, environment);
        return createContext(data, environment);
    }

    protected ReadOnlyContext createContext(Map data, Hashtable environment) {
        return new ReadOnlyContext(environment, data);
    }

    protected void createConnectionFactories(Map data, Hashtable environment) throws MalformedURLException {
        for (Iterator iter = environment.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            if (key.startsWith(CONNECTION_FACTORY_PREFIX)) {
                String jndiName = key.substring(CONNECTION_FACTORY_PREFIX.length());
                ConnectionFactory cf = createFactory(entry.getValue().toString().trim());
                if (cf != null) {
                    data.put(jndiName, cf);
                }
            }
        }
    }

    protected void createDestinations(Map data, Hashtable environment) {
        for (Iterator iter = environment.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            if (key.startsWith(DESTINATION_PREFIX)) {
                String jndiName = key.substring(DESTINATION_PREFIX.length());
                Destination dest = createDestination(entry.getValue().toString().trim());
                if (dest != null) {
                    data.put(jndiName, dest);
                }
            }
        }
    }

    protected void createTopics(Map data, Hashtable environment) {
        for (Iterator iter = environment.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            if (key.startsWith(TOPIC_PREFIX)) {
                String jndiName = key.substring(TOPIC_PREFIX.length());
                Topic t = createTopic(entry.getValue().toString().trim());
                if (t != null) {
                    data.put(jndiName, t);
                }
            }
        }
    }

    private ConnectionFactory createFactory(String url) throws MalformedURLException {
        logger.info("Instance of factory for: " + url);
        return new AtomConnectionFactory(url);
    }

    private AtomDestination createDestination(String str) {
        logger.info("Instance of destination for: " + str);
        return new AtomDestination();
    }

    private AtomTopic createTopic(String address) {
        logger.info("Instance of topic for: " + address);
        return new AtomTopic(address);
    }
}
