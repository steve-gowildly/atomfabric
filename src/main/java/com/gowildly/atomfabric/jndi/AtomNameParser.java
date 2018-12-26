package com.gowildly.atomfabric.jndi;

import javax.naming.CompositeName;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;

public class AtomNameParser implements NameParser {
    public Name parse(String name) throws NamingException {
        return new CompositeName(name);
    }
}