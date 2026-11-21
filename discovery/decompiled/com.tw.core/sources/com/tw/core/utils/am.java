package com.tw.core.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/* JADX INFO: loaded from: classes.dex */
class am extends Handler {
    final /* synthetic */ I bz;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    am(I i, Looper looper) {
        super(looper);
        this.bz = i;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        try {
            switch (message.what) {
                case MqttConnectOptions.MQTT_VERSION_DEFAULT /* 0 */:
                default:
                    return;
                case 266:
                    if (message.arg1 == 0) {
                        this.bz.aG = (String) message.obj;
                        B.ci("TwSystemUtils", "mcu %s", this.bz.aG);
                        this.bz.db(this.bz.aG);
                        return;
                    }
                    return;
            }
        } catch (Exception e) {
            this.bz.db("unknow");
        }
        this.bz.db("unknow");
    }
}
