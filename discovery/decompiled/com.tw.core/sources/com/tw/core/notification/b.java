package com.tw.core.notification;

import com.tw.core.utils.C0065s;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private static b jZ = new b();
    private Map ka;

    private b() {
    }

    public static b get() {
        return jZ;
    }

    public void oF(String str, c cVar) {
        if (this.ka == null) {
            this.ka = new HashMap();
        }
        if (this.ka.containsKey(str)) {
            return;
        }
        this.ka.put(str, cVar);
    }

    protected void oG(String str, String str2) {
        if (this.ka == null || this.ka.isEmpty()) {
            return;
        }
        C0065s.bK("NotificationClickManage", "notifyNotificationClick msgId = " + str);
        if (this.ka.containsKey(str)) {
            c cVar = (c) this.ka.get(str);
            C0065s.bK("NotificationClickManage", "notifyNotificationClick listener = " + cVar);
            if (cVar != null) {
                cVar.ok(str, str2);
            }
        }
    }

    public void oH(String str) {
        if (this.ka == null || !this.ka.containsKey(str)) {
            return;
        }
        this.ka.remove(str);
    }
}
