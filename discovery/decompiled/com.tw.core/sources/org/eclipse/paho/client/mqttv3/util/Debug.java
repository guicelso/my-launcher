package org.eclipse.paho.client.mqttv3.util;

import java.util.Enumeration;
import java.util.Properties;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* JADX INFO: loaded from: classes.dex */
public class Debug {
    private static final String separator = "==============";
    private String clientID;
    private ClientComms comms;
    private static final String CLASS_NAME = ClientComms.class.getName();
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private static final String lineSep = System.getProperty("line.separator", "\n");

    public Debug(String str, ClientComms clientComms) {
        this.clientID = str;
        this.comms = clientComms;
        log.setResourceName(str);
    }

    public static String dumpProperties(Properties properties, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration<?> enumerationPropertyNames = properties.propertyNames();
        stringBuffer.append(String.valueOf(lineSep) + separator + " " + str + " " + separator + lineSep);
        while (enumerationPropertyNames.hasMoreElements()) {
            String str2 = (String) enumerationPropertyNames.nextElement();
            stringBuffer.append(String.valueOf(left(str2, 28, ' ')) + ":  " + properties.get(str2) + lineSep);
        }
        stringBuffer.append("==========================================" + lineSep);
        return stringBuffer.toString();
    }

    public static String left(String str, int i, char c) {
        if (str.length() >= i) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(i);
        stringBuffer.append(str);
        int length = i - str.length();
        while (true) {
            length--;
            if (length < 0) {
                return stringBuffer.toString();
            }
            stringBuffer.append(c);
        }
    }

    public void dumpBaseDebug() {
        dumpVersion();
        dumpSystemProperties();
        dumpMemoryTrace();
    }

    public void dumpClientComms() {
        if (this.comms == null) {
            return;
        }
        log.fine(CLASS_NAME, "dumpClientComms", dumpProperties(this.comms.getDebug(), String.valueOf(this.clientID) + " : ClientComms").toString());
    }

    public void dumpClientDebug() {
        dumpClientComms();
        dumpConOptions();
        dumpClientState();
        dumpBaseDebug();
    }

    public void dumpClientState() {
        if (this.comms == null || this.comms.getClientState() == null) {
            return;
        }
        log.fine(CLASS_NAME, "dumpClientState", dumpProperties(this.comms.getClientState().getDebug(), String.valueOf(this.clientID) + " : ClientState").toString());
    }

    public void dumpConOptions() {
        if (this.comms == null) {
            return;
        }
        log.fine(CLASS_NAME, "dumpConOptions", dumpProperties(this.comms.getConOptions().getDebug(), String.valueOf(this.clientID) + " : Connect Options").toString());
    }

    protected void dumpMemoryTrace() {
        log.dumpTrace();
    }

    public void dumpSystemProperties() {
        log.fine(CLASS_NAME, "dumpSystemProperties", dumpProperties(System.getProperties(), "SystemProperties").toString());
    }

    protected void dumpVersion() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf(lineSep) + separator + " Version Info " + separator + lineSep);
        stringBuffer.append(String.valueOf(left("Version", 20, ' ')) + ":  " + ClientComms.VERSION + lineSep);
        stringBuffer.append(String.valueOf(left("Build Level", 20, ' ')) + ":  " + ClientComms.BUILD_LEVEL + lineSep);
        stringBuffer.append("==========================================" + lineSep);
        log.fine(CLASS_NAME, "dumpVersion", stringBuffer.toString());
    }
}
