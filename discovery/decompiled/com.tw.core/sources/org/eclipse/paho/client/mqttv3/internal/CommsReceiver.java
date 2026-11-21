package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRec;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* JADX INFO: loaded from: classes.dex */
public class CommsReceiver implements Runnable {
    private static final String CLASS_NAME = CommsReceiver.class.getName();
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private ClientComms clientComms;
    private ClientState clientState;
    private MqttInputStream in;
    private Future receiverFuture;
    private volatile boolean receiving;
    private String threadName;
    private CommsTokenStore tokenStore;
    private boolean running = false;
    private Object lifecycle = new Object();
    private Thread recThread = null;
    private final Semaphore runningSemaphore = new Semaphore(1);

    public CommsReceiver(ClientComms clientComms, ClientState clientState, CommsTokenStore commsTokenStore, InputStream inputStream) {
        this.clientState = null;
        this.clientComms = null;
        this.tokenStore = null;
        this.in = new MqttInputStream(clientState, inputStream);
        this.clientComms = clientComms;
        this.clientState = clientState;
        this.tokenStore = commsTokenStore;
        log.setResourceName(clientComms.getClient().getClientId());
    }

    public boolean isReceiving() {
        return this.receiving;
    }

    public boolean isRunning() {
        return this.running;
    }

    @Override // java.lang.Runnable
    public void run() {
        MqttToken token = null;
        this.recThread = Thread.currentThread();
        this.recThread.setName(this.threadName);
        try {
            this.runningSemaphore.acquire();
            while (this.running && this.in != null) {
                try {
                    try {
                        log.fine(CLASS_NAME, "run", "852");
                        this.receiving = this.in.available() > 0;
                        MqttWireMessage mqttWireMessage = this.in.readMqttWireMessage();
                        this.receiving = false;
                        if (mqttWireMessage instanceof MqttAck) {
                            token = this.tokenStore.getToken(mqttWireMessage);
                            if (token != null) {
                                synchronized (token) {
                                    this.clientState.notifyReceivedAck((MqttAck) mqttWireMessage);
                                }
                            } else {
                                if (!(mqttWireMessage instanceof MqttPubRec) && !(mqttWireMessage instanceof MqttPubComp) && !(mqttWireMessage instanceof MqttPubAck)) {
                                    throw new MqttException(6);
                                }
                                log.fine(CLASS_NAME, "run", "857");
                            }
                        } else if (mqttWireMessage != null) {
                            this.clientState.notifyReceivedMsg(mqttWireMessage);
                        }
                        this.receiving = false;
                        this.runningSemaphore.release();
                    } catch (IOException e) {
                        log.fine(CLASS_NAME, "run", "853");
                        this.running = false;
                        if (!this.clientComms.isDisconnecting()) {
                            this.clientComms.shutdownConnection(token, new MqttException(32109, e));
                        }
                        this.receiving = false;
                        this.runningSemaphore.release();
                    } catch (MqttException e2) {
                        MqttToken mqttToken = token;
                        log.fine(CLASS_NAME, "run", "856", null, e2);
                        this.running = false;
                        this.clientComms.shutdownConnection(mqttToken, e2);
                        this.receiving = false;
                        this.runningSemaphore.release();
                        token = mqttToken;
                    }
                } catch (Throwable th) {
                    this.receiving = false;
                    this.runningSemaphore.release();
                    throw th;
                }
            }
            log.fine(CLASS_NAME, "run", "854");
        } catch (InterruptedException e3) {
            this.running = false;
        }
    }

    public void start(String str, ExecutorService executorService) {
        this.threadName = str;
        log.fine(CLASS_NAME, "start", "855");
        synchronized (this.lifecycle) {
            if (!this.running) {
                this.running = true;
                this.receiverFuture = executorService.submit(this);
            }
        }
    }

    public void stop() {
        synchronized (this.lifecycle) {
            if (this.receiverFuture != null) {
                this.receiverFuture.cancel(true);
            }
            log.fine(CLASS_NAME, "stop", "850");
            if (this.running) {
                this.running = false;
                this.receiving = false;
                if (!Thread.currentThread().equals(this.recThread)) {
                    try {
                        try {
                            this.runningSemaphore.acquire();
                            this.runningSemaphore.release();
                        } catch (InterruptedException e) {
                            this.runningSemaphore.release();
                        }
                    } catch (Throwable th) {
                        this.runningSemaphore.release();
                        throw th;
                    }
                }
            }
        }
        this.recThread = null;
        log.fine(CLASS_NAME, "stop", "851");
    }
}
