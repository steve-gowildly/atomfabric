package com.gowildly.atomfabric.jms;

import org.apache.log4j.Logger;

import javax.jms.Destination;

public class AtomDestination implements Destination {

    protected static Logger logger = Logger.getLogger(AtomDestination.class);

    protected String name = "";
}
