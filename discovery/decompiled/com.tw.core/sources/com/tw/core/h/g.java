package com.tw.core.h;

import android.os.Handler;
import android.os.Looper;
import com.tw.core.utils.B;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* JADX INFO: loaded from: classes.dex */
class g implements MqttCallbackExtended {
    final /* synthetic */ b jo;

    g(b bVar) {
        this.jo = bVar;
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallbackExtended
    public void connectComplete(boolean z, String str) {
        B.ci("MqttClientManager", "connect success reconnect %s serverURI %s", Boolean.valueOf(z), str);
        try {
            if (this.jo.jf != null && this.jo.jf.size() > 0) {
                Iterator it = this.jo.jf.iterator();
                while (it.hasNext()) {
                    ((f) it.next()).jc(z, str);
                }
            }
            synchronized (this.jo.ji) {
                if (!this.jo.ji.isEmpty()) {
                    for (Map.Entry entry : this.jo.ji.entrySet()) {
                        this.jo.nV((String[]) entry.getKey(), (int[]) entry.getValue());
                    }
                    this.jo.ji.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void connectionLost(Throwable th) {
        th.printStackTrace();
        B.cj("MqttClientManager", "mqtt connection lost", new Object[0]);
        if (this.jo.jf == null || this.jo.jf.size() <= 0) {
            return;
        }
        Iterator it = this.jo.jf.iterator();
        while (it.hasNext()) {
            ((f) it.next()).jy(th);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        try {
            B.ci("MqttClientManager", "deliveryComplete: %s", iMqttDeliveryToken.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void messageArrived(String str, MqttMessage mqttMessage) {
        new Handler(Looper.getMainLooper()).post(new h(this, mqttMessage, str));
    }
}
