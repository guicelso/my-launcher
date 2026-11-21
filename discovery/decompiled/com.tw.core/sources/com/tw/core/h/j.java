package com.tw.core.h;

import com.tw.core.utils.B;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/* JADX INFO: loaded from: classes.dex */
class j implements IMqttActionListener {
    final /* synthetic */ b jt;

    j(b bVar) {
        this.jt = bVar;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
    public void onFailure(IMqttToken iMqttToken, Throwable th) {
        B.cj("MqttClientManager", "mqtt connect failure %s", iMqttToken);
        this.jt.ja = false;
        th.printStackTrace();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
    public void onSuccess(IMqttToken iMqttToken) {
        B.ci("MqttClientManager", "mqtt connect success %s", iMqttToken);
        this.jt.ja = false;
        this.jt.iZ.set(1);
    }
}
