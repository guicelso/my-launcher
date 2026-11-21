package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* JADX INFO: loaded from: classes.dex */
public class CommsTokenStore {
    private static final String CLASS_NAME = CommsTokenStore.class.getName();
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private MqttException closedResponse = null;
    private String logContext;
    private Hashtable tokens;

    public CommsTokenStore(String str) {
        log.setResourceName(str);
        this.tokens = new Hashtable();
        this.logContext = str;
        log.fine(CLASS_NAME, "<Init>", "308");
    }

    public void clear() {
        log.fine(CLASS_NAME, "clear", "305", new Object[]{new Integer(this.tokens.size())});
        synchronized (this.tokens) {
            this.tokens.clear();
        }
    }

    public int count() {
        int size;
        synchronized (this.tokens) {
            size = this.tokens.size();
        }
        return size;
    }

    public MqttDeliveryToken[] getOutstandingDelTokens() {
        MqttDeliveryToken[] mqttDeliveryTokenArr;
        synchronized (this.tokens) {
            log.fine(CLASS_NAME, "getOutstandingDelTokens", "311");
            Vector vector = new Vector();
            Enumeration enumerationElements = this.tokens.elements();
            while (enumerationElements.hasMoreElements()) {
                MqttToken mqttToken = (MqttToken) enumerationElements.nextElement();
                if (mqttToken != null && (mqttToken instanceof MqttDeliveryToken) && !mqttToken.internalTok.isNotified()) {
                    vector.addElement(mqttToken);
                }
            }
            mqttDeliveryTokenArr = (MqttDeliveryToken[]) vector.toArray(new MqttDeliveryToken[vector.size()]);
        }
        return mqttDeliveryTokenArr;
    }

    public Vector getOutstandingTokens() {
        Vector vector;
        synchronized (this.tokens) {
            log.fine(CLASS_NAME, "getOutstandingTokens", "312");
            vector = new Vector();
            Enumeration enumerationElements = this.tokens.elements();
            while (enumerationElements.hasMoreElements()) {
                MqttToken mqttToken = (MqttToken) enumerationElements.nextElement();
                if (mqttToken != null) {
                    vector.addElement(mqttToken);
                }
            }
        }
        return vector;
    }

    public MqttToken getToken(String str) {
        return (MqttToken) this.tokens.get(str);
    }

    public MqttToken getToken(MqttWireMessage mqttWireMessage) {
        return (MqttToken) this.tokens.get(mqttWireMessage.getKey());
    }

    public void open() {
        synchronized (this.tokens) {
            log.fine(CLASS_NAME, "open", "310");
            this.closedResponse = null;
        }
    }

    protected void quiesce(MqttException mqttException) {
        synchronized (this.tokens) {
            log.fine(CLASS_NAME, "quiesce", "309", new Object[]{mqttException});
            this.closedResponse = mqttException;
        }
    }

    public MqttToken removeToken(String str) {
        log.fine(CLASS_NAME, "removeToken", "306", new Object[]{str});
        if (str == null) {
            return null;
        }
        return (MqttToken) this.tokens.remove(str);
    }

    public MqttToken removeToken(MqttWireMessage mqttWireMessage) {
        if (mqttWireMessage == null) {
            return null;
        }
        return removeToken(mqttWireMessage.getKey());
    }

    protected MqttDeliveryToken restoreToken(MqttPublish mqttPublish) {
        MqttDeliveryToken mqttDeliveryToken;
        synchronized (this.tokens) {
            String string = new Integer(mqttPublish.getMessageId()).toString();
            if (this.tokens.containsKey(string)) {
                mqttDeliveryToken = (MqttDeliveryToken) this.tokens.get(string);
                log.fine(CLASS_NAME, "restoreToken", "302", new Object[]{string, mqttPublish, mqttDeliveryToken});
            } else {
                mqttDeliveryToken = new MqttDeliveryToken(this.logContext);
                mqttDeliveryToken.internalTok.setKey(string);
                this.tokens.put(string, mqttDeliveryToken);
                log.fine(CLASS_NAME, "restoreToken", "303", new Object[]{string, mqttPublish, mqttDeliveryToken});
            }
        }
        return mqttDeliveryToken;
    }

    protected void saveToken(MqttToken mqttToken, String str) {
        synchronized (this.tokens) {
            log.fine(CLASS_NAME, "saveToken", "307", new Object[]{str, mqttToken.toString()});
            mqttToken.internalTok.setKey(str);
            this.tokens.put(str, mqttToken);
        }
    }

    protected void saveToken(MqttToken mqttToken, MqttWireMessage mqttWireMessage) {
        synchronized (this.tokens) {
            if (this.closedResponse != null) {
                throw this.closedResponse;
            }
            String key = mqttWireMessage.getKey();
            log.fine(CLASS_NAME, "saveToken", "300", new Object[]{key, mqttWireMessage});
            saveToken(mqttToken, key);
        }
    }

    public String toString() {
        String string;
        String property = System.getProperty("line.separator", "\n");
        StringBuffer stringBuffer = new StringBuffer();
        synchronized (this.tokens) {
            Enumeration enumerationElements = this.tokens.elements();
            while (enumerationElements.hasMoreElements()) {
                stringBuffer.append("{" + ((MqttToken) enumerationElements.nextElement()).internalTok + "}" + property);
            }
            string = stringBuffer.toString();
        }
        return string;
    }
}
