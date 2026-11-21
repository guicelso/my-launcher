package com.tw.core.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.C0012m;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0065s;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static a jX;
    private NotificationManager jY;

    private a() {
    }

    public static a get() {
        if (jX == null) {
            synchronized (a.class) {
                if (jX == null) {
                    jX = new a();
                }
            }
        }
        return jX;
    }

    public void oD(String str) {
        if (this.jY != null) {
            this.jY.cancel(C0062p.br(str));
        }
    }

    public void oE(int i, String str, String str2, String str3, String str4) {
        C0012m c0012mQG = new C0012m(DoFunApplication.nu()).qJ(i).qH(str2).qG(str3);
        c0012mQG.qD(true);
        NotificationView notificationView = new NotificationView(DoFunApplication.nu());
        notificationView.oC(str2, str3, i);
        c0012mQG.qE(notificationView);
        Intent intent = new Intent(DoFunApplication.nu(), (Class<?>) NotificationReceiver.class);
        intent.putExtra("msgId", str);
        intent.putExtra("body", str4);
        int iBr = C0062p.br(str);
        C0065s.bK("NotificationHelper", "sendNotification msgId = " + str + ",id = " + iBr);
        c0012mQG.qF(PendingIntent.getBroadcast(DoFunApplication.nu(), iBr, intent, 134217728));
        if (this.jY == null) {
            this.jY = (NotificationManager) DoFunApplication.nu().getSystemService("notification");
        }
        this.jY.notify(iBr, c0012mQG.qA());
    }
}
