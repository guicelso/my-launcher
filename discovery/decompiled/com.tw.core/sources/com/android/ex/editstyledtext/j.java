package com.android.ex.editstyledtext;

import android.util.Log;
import java.util.HashMap;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.logging.Logger;

/* JADX INFO: loaded from: classes.dex */
public class j {
    private HashMap lg;
    private a lh;
    private int li;

    private e ql(int i) {
        if (this.lg.containsKey(Integer.valueOf(i))) {
            return (e) this.lg.get(Integer.valueOf(i));
        }
        return null;
    }

    public boolean qk(int i) {
        Log.d("EditModeActions", "--- do the next action: " + i + "," + this.lh.oZ());
        e eVarQl = ql(i);
        if (eVarQl == null) {
            Log.e("EditModeActions", "--- invalid action error.");
            return false;
        }
        switch (this.lh.oZ()) {
            case MqttConnectOptions.MQTT_VERSION_DEFAULT /* 0 */:
                return eVarQl.qb();
            case Logger.SEVERE /* 1 */:
                return eVarQl.qe();
            case Logger.WARNING /* 2 */:
                return eVarQl.qa();
            case 3:
                return this.lh.pf() ? eVarQl.qd() : eVarQl.qc();
            default:
                return false;
        }
    }

    public void qm(int i) {
        qn(i, null);
    }

    public void qn(int i, Object[] objArr) {
        ql(i).pZ(objArr);
        this.li = i;
        qk(i);
    }

    public void qo() {
        qk(5);
    }
}
