package com.tw.core.h;

import com.tw.core.utils.B;
import com.tw.core.utils.C0065s;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/* JADX INFO: loaded from: classes.dex */
class k implements IMqttActionListener {
    final /* synthetic */ b ju;
    final /* synthetic */ c jv;

    k(b bVar, c cVar) {
        this.ju = bVar;
        this.jv = cVar;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
    public void onFailure(IMqttToken iMqttToken, Throwable th) {
        th.printStackTrace();
        C0065s.bK("MqttClientManager", "onFailure:");
        if (this.jv != null) {
            this.jv.lT(false);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
    public void onSuccess(IMqttToken iMqttToken) {
        B.ci("MqttClientManager", "onSuccess:%s", iMqttToken);
        if (this.jv != null) {
            this.jv.lT(true);
        }
    }
}
