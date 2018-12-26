package com.gowildly.atomfabric.utils;

import java.util.Enumeration;
import java.util.Hashtable;

public class CollectionContainer {

    Hashtable objectValues = new Hashtable();

    public CollectionContainer() {
    }

    protected long getNumeric(String name) throws Exception {
        if (objectValues != null) {
            Object val = objectValues.get(name);

            if (val != null) {
                if (val instanceof Long) {
                    return ((Long) val).longValue();
                }

                if (val instanceof Integer) {
                    return ((Integer) val).intValue();
                }

                if (val instanceof Short) {
                    return ((Short) val).shortValue();
                }

                if (val instanceof Byte) {
                    return ((Byte) val).byteValue();
                }
            }
        }

        return 0;
    }

    public boolean getBoolean(String name) {
        if (objectValues != null) {
            Boolean val = (Boolean) objectValues.get(name);

            if (val != null) {
                return val.booleanValue();
            }
        }

        return false;
    }

    public byte getByte(String name) throws Exception {
        return (byte) getNumeric(name);
    }


    public short getShort(String name) throws Exception {
        return (short) getNumeric(name);
    }

    public int getInt(String name) throws Exception {
        return (int) getNumeric(name);
    }

    public long getLong(String name) throws Exception {
        return getNumeric(name);
    }

    public float getFloat(String name) throws Exception {
        if (objectValues != null) {
            Float val = (Float) objectValues.get(name);

            if (val != null) {
                return val.floatValue();
            }
        }

        return 0;
    }

    public double getDouble(String name) throws Exception {
        if (objectValues != null) {
            Object val = objectValues.get(name);

            if (val != null) {
                if (val instanceof Double) {
                    return ((Double) val).doubleValue();
                }

                if (val instanceof Float) {
                    return ((Float) val).floatValue();
                }
            }
        }

        return 0;
    }

    public String getString(String name) throws Exception {
        if (objectValues != null) {
            Object val = objectValues.get(name);

            if (val != null) {
                return val.toString();
            }
        }

        return null;
    }

    public Object getObject(String name) throws Exception {
        if (objectValues != null) {
            return objectValues.get(name);
        }

        return null;
    }

    public Enumeration getMapNames() throws Exception {
        if (objectValues != null) {
            return objectValues.keys();
        }

        return null;
    }

    public void setBoolean(String name, boolean value) {
        objectValues.put(name, new Boolean(value));
    }

    public void setByte(String name, byte value) {
        objectValues.put(name, new Byte(value));
    }

    public void setShort(String name, short value) {
        objectValues.put(name, new Short(value));
    }

    public void setInt(String name, int value) {
        objectValues.put(name, new Integer(value));
    }

    public void setLong(String name, long value) {
        objectValues.put(name, new Long(value));
    }

    public void setFloat(String name, float value) {
        objectValues.put(name, new Float(value));
    }

    public void setDouble(String name, double value) {
        objectValues.put(name, new Double(value));
    }

    public void setString(String name, String value) {
        objectValues.put(name, new String(value));
    }

    public void setObject(String name, Object value) {
        objectValues.put(name, value);
    }

    public boolean itemExists(String name) {
        return objectValues.contains(name);
    }

    public void clear() {
        objectValues.clear();
    }
}