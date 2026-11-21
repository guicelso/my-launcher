package com.tw.core.h;

import com.tw.core.utils.B;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* JADX INFO: loaded from: classes.dex */
class h implements Runnable {
    final /* synthetic */ g jp;
    final /* synthetic */ MqttMessage jq;
    final /* synthetic */ String jr;

    h(g gVar, MqttMessage mqttMessage, String str) {
        this.jp = gVar;
        this.jq = mqttMessage;
        this.jr = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.jq != null) {
                String str = new String(this.jq.getPayload());
                B.ci("MqttClientManager", "messageArrived %s msg %s", this.jr, str);
                a.get().nM(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
