package com.tw.core.h;

import com.tw.core.utils.B;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.J;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private static final String iW;
    private static final String iX;
    private static volatile b jc;
    private volatile boolean ja;
    private MqttConnectOptions jb;
    private MqttAsyncClient je;
    private List jf;
    private Thread jj;
    private String[] iY = {com.tw.core.d.a.gR};
    public int jg = 0;
    public int iV = 1;
    public int jh = 2;
    private final AtomicInteger iZ = new AtomicInteger(1);
    private final MqttCallbackExtended jd = new g(this);
    final Map ji = new HashMap();

    static {
        iW = J.aI ? "dofuntest" : "dofun";
        iX = J.aI ? "dofuntest" : "dofun666666";
    }

    private b() {
        nP();
    }

    public static b nN() {
        if (jc == null) {
            synchronized (b.class) {
                if (jc == null) {
                    jc = new b();
                }
            }
        }
        return jc;
    }

    private void nP() {
        this.jj = new Thread(new i(this));
        this.jj.start();
    }

    private MqttConnectOptions nS() {
        if (this.jb == null) {
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            B.ci("MqttClientManager", "Connecting to broker: ", new Object[0]);
            mqttConnectOptions.setServerURIs(this.iY);
            mqttConnectOptions.setUserName(iW);
            mqttConnectOptions.setPassword(iX.toCharArray());
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setKeepAliveInterval(300);
            mqttConnectOptions.setConnectionTimeout(30);
            mqttConnectOptions.setMqttVersion(4);
            this.jb = mqttConnectOptions;
        }
        return this.jb;
    }

    public void disconnect() {
        B.cj("MqttClientManager", "disconnect", new Object[0]);
        if (isConnected()) {
            try {
                this.je.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        if (this.je != null) {
            return this.je.isConnected();
        }
        return false;
    }

    public void nO(String str, String str2, int i, c cVar) {
        B.ci("MqttClientManager", "sendMsg=%s topic=%s", str, str2);
        if (!isConnected()) {
            B.cj("MqttClientManager", "sendMsg no connect", new Object[0]);
            if (cVar != null) {
                cVar.lT(false);
                return;
            }
            return;
        }
        try {
            MqttMessage mqttMessage = new MqttMessage(str.getBytes());
            mqttMessage.setQos(i);
            this.je.publish(str2, mqttMessage, (Object) null, new k(this, cVar));
        } catch (Exception e) {
            C0065s.bK("MqttClientManager", "sendMsg " + e.getMessage());
            e.printStackTrace();
            if (cVar != null) {
                cVar.lT(false);
            }
        }
    }

    public synchronized void nQ() {
        B.ci("MqttClientManager", "connectMqtt", new Object[0]);
        if (this.ja) {
            B.cj("MqttClientManager", "mqtt is connecting", new Object[0]);
            return;
        }
        if (isConnected()) {
            B.cj("MqttClientManager", "mqtt is connected", new Object[0]);
            return;
        }
        this.ja = true;
        try {
            if (this.je == null) {
                this.je = new MqttAsyncClient(this.iY[0], com.tw.core.b.f.gr(), new MemoryPersistence());
                this.je.setCallback(this.jd);
            }
            this.je.connect(nS(), null, new j(this));
        } catch (Throwable th) {
            th.printStackTrace();
            this.ja = false;
        }
    }

    public void nR(String[] strArr, int[] iArr) {
        if (strArr == null || iArr == null) {
            return;
        }
        synchronized (this.ji) {
            if (!this.ji.containsKey(strArr)) {
                this.ji.put(strArr, iArr);
            }
        }
    }

    public void nT(f fVar) {
        if (fVar == null) {
            return;
        }
        if (this.jf == null) {
            this.jf = new ArrayList();
        }
        if (this.jf.contains(fVar)) {
            return;
        }
        this.jf.add(fVar);
    }

    public void nU(String str, c cVar) {
        nO(str, com.tw.core.app.a.iD, this.iV, cVar);
    }

    public void nV(String[] strArr, int[] iArr) {
        if (isConnected()) {
            try {
                this.je.subscribe(strArr, iArr);
                return;
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        nR(strArr, iArr);
    }

    public void nW(String[] strArr) {
        if (isConnected()) {
            try {
                this.je.unsubscribe(strArr);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void nX(f fVar) {
        if (fVar == null || this.jf == null || !this.jf.contains(fVar)) {
            return;
        }
        this.jf.remove(fVar);
    }
}
