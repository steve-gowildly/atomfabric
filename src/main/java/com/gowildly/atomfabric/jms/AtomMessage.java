package com.gowildly.atomfabric.jms;

import com.gowildly.atomfabric.utils.CollectionContainer;

import javax.jms.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;

public class AtomMessage implements Message, Serializable {

    public final static int PUB_SUB_MESSAGE = 1;
    public final static int NORMAL_PRIORITY = 4;
    public final static int PENDING_STATE = 1;
    public final static int SENT_STATE = 2;

    protected String destinationName = "";
    protected String correlationID = "";
    protected String id = "";
    protected String type = "";
    protected Destination destination = null;
    protected Destination replyTo = null;
    protected int messageType = PUB_SUB_MESSAGE;
    protected int deliveryMode = DeliveryMode.NON_PERSISTENT;
    protected int priority = NORMAL_PRIORITY;
    protected int state = PENDING_STATE;
    protected long expiration = 0;
    protected long tstamp = 0;
    protected boolean redelivered = false;
    protected CollectionContainer props = new CollectionContainer();

    public AtomMessage() {
    }

    public void setDestinationName(String name) {
        this.destinationName = name;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public void setSent(boolean sent) {
        if (sent)
            state = SENT_STATE;
    }

    public void acknowledge() throws JMSException {
        //Acknowledges all consumed messages of the session of this message. 
    }

    public void clearBody() throws JMSException {
        // Each specific message object should override this
    }

    public void clearProperties() throws JMSException {
        try {
            props.clear();
        } catch (Exception e) {
        }

        props = new CollectionContainer();
    }

    public String getJMSCorrelationID() throws JMSException {
        return correlationID;
    }

    public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
        return correlationID.getBytes();
    }

    public int getJMSDeliveryMode() throws JMSException {
        return deliveryMode;
    }

    public Destination getJMSDestination() throws JMSException {
        return destination;
    }

    public long getJMSExpiration() throws JMSException {
        return expiration;
    }

    public String getJMSMessageID() throws JMSException {
        return id;
    }

    public int getJMSPriority() throws JMSException {
        return priority;
    }

    public boolean getJMSRedelivered() throws JMSException {
        return redelivered;
    }

    public Destination getJMSReplyTo() throws JMSException {
        return replyTo;
    }

    public long getJMSTimestamp() throws JMSException {
        return tstamp;
    }

    public String getJMSType() throws JMSException {
        return type;
    }

    public void setJMSCorrelationID(String correlationID) throws JMSException {
        this.correlationID = new String(correlationID);
    }

    public void setJMSCorrelationIDAsBytes(byte[] correlationID) throws JMSException {
        this.correlationID = new String(correlationID);
    }

    public void setJMSDeliveryMode(int deliveryMode) throws JMSException {
        switch (deliveryMode) {
            case DeliveryMode.NON_PERSISTENT:
                this.deliveryMode = deliveryMode;
                break;
            case DeliveryMode.PERSISTENT:
                throw new JMSException("PERSISTENT messaging not supported");

            default:
                throw new JMSException("Invalid delivery mode");
        }
    }

    public void setJMSDestination(Destination destination) throws JMSException {
        this.destination = destination;
    }

    public void setJMSExpiration(long expiration) throws JMSException {
        if (expiration == 0) {
            this.expiration = 0;
            return;
        }

        GregorianCalendar currentGMT = new GregorianCalendar();
        currentGMT.set(currentGMT.get(Calendar.YEAR) - 1900,
                currentGMT.get(Calendar.MONTH),
                currentGMT.get(Calendar.DAY_OF_MONTH),
                currentGMT.get(Calendar.HOUR_OF_DAY),
                currentGMT.get(Calendar.MINUTE),
                currentGMT.get(Calendar.SECOND));

        long gmt = currentGMT.getTime().getTime();
        this.expiration = expiration + gmt;
    }

    public void setJMSMessageID(String id) throws JMSException {
        this.id = new String(id);
    }

    public void setJMSPriority(int priority) throws JMSException {
        if ((priority > 9) || (priority < 0)) {
            return;
        }

        this.priority = priority;
    }

    public void setJMSRedelivered(boolean redelivered) throws JMSException {
        this.redelivered = redelivered;
    }

    public void setJMSReplyTo(Destination replyTo) throws JMSException {
        this.replyTo = replyTo;
    }

    public void setJMSTimestamp(long tstamp) throws JMSException {
        if (state == SENT_STATE) {
            return;
        }

        this.tstamp = tstamp;
    }

    public void setJMSType(String type) throws JMSException {
        this.type = type;
    }

    public void setLongProperty(String name, long value) throws JMSException, MessageNotWriteableException {
        if (state == SENT_STATE) {
            throw new MessageNotWriteableException("Cannot set properties after message is sent");
        }

        props.setLong(name, value);
    }

    public void setObjectProperty(String name, Object value) throws JMSException, MessageNotWriteableException {
        if (state == SENT_STATE) {
            throw new MessageNotWriteableException("Cannot set properties after message is sent");
        }

        props.setObject(name, value);
    }

    public void setShortProperty(String name, short value) throws JMSException, MessageNotWriteableException {
        if (state == SENT_STATE) {
            throw new MessageNotWriteableException("Cannot set properties after message is sent");
        }

        props.setShort(name, value);
    }

    public void setStringProperty(String name, String value) throws JMSException, MessageNotWriteableException {
        if (state == SENT_STATE) {
            throw new MessageNotWriteableException("Cannot set properties after message is sent");
        }

        props.setString(name, value);
    }

    public void setBooleanProperty(String name, boolean value) throws JMSException, MessageNotWriteableException {
        if (state == SENT_STATE) {
            throw new MessageNotWriteableException("Cannot set properties after message is sent");
        }

        props.setBoolean(name, value);
    }

    public void setByteProperty(String name, byte value) throws JMSException, MessageNotWriteableException {
        if (state == SENT_STATE) {
            throw new MessageNotWriteableException("Cannot set properties after message is sent");
        }

        props.setByte(name, value);
    }

    public void setDoubleProperty(String name, double value) throws JMSException, MessageNotWriteableException {
        if (state == SENT_STATE) {
            throw new MessageNotWriteableException("Cannot set properties after message is sent");
        }

        props.setDouble(name, value);
    }

    public void setFloatProperty(String name, float value) throws JMSException, MessageNotWriteableException {
        if (state == SENT_STATE) {
            throw new MessageNotWriteableException("Cannot set properties after message is sent");
        }

        props.setFloat(name, value);
    }

    public void setIntProperty(String name, int value) throws JMSException, MessageNotWriteableException {
        if (state == SENT_STATE) {
            throw new MessageNotWriteableException("Cannot set properties after message is sent");
        }

        props.setInt(name, value);
    }

    public boolean getBooleanProperty(String name) throws JMSException, MessageFormatException {
        try {
            return props.getBoolean(name);
        } catch (Exception e) {
            throw new MessageFormatException(e.getMessage());
        }
    }

    public byte getByteProperty(String name) throws JMSException, MessageFormatException {
        try {
            return props.getByte(name);
        } catch (Exception e) {
            throw new MessageFormatException(e.getMessage());
        }
    }

    public double getDoubleProperty(String name) throws JMSException, MessageFormatException {
        try {
            return props.getDouble(name);
        } catch (Exception e) {
            throw new MessageFormatException(e.getMessage());
        }
    }

    public float getFloatProperty(String name) throws JMSException, MessageFormatException {
        try {
            return props.getFloat(name);
        } catch (Exception e) {
            throw new MessageFormatException(e.getMessage());
        }
    }

    public int getIntProperty(String name) throws JMSException, MessageFormatException {
        try {
            return props.getInt(name);
        } catch (Exception e) {
            throw new MessageFormatException(e.getMessage());
        }
    }

    public long getLongProperty(String name) throws JMSException, MessageFormatException {
        try {
            return props.getLong(name);
        } catch (Exception e) {
            throw new MessageFormatException(e.getMessage());
        }
    }

    public Object getObjectProperty(String name) throws JMSException, MessageFormatException {
        try {
            return props.getObject(name);
        } catch (Exception e) {
            throw new MessageFormatException(e.getMessage());
        }
    }

    public short getShortProperty(String name) throws JMSException, MessageFormatException {
        try {
            return props.getShort(name);
        } catch (Exception e) {
            throw new MessageFormatException(e.getMessage());
        }
    }

    public String getStringProperty(String name) throws JMSException, MessageFormatException {
        try {
            return props.getString(name);
        } catch (Exception e) {
            throw new MessageFormatException(e.getMessage());
        }
    }

    public boolean propertyExists(String name) throws JMSException, MessageFormatException {
        return props.itemExists(name);
    }

    public Enumeration getPropertyNames() throws JMSException, MessageFormatException {
        try {
            return props.getMapNames();
        } catch (Exception e) {
            throw new MessageFormatException(e.getMessage());
        }
    }
}
