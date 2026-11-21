package android.support.v4.app;

import android.app.Notification;

/* JADX INFO: renamed from: android.support.v4.app.f, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0005f extends C0004e {
    C0005f() {
    }

    @Override // android.support.v4.app.C0004e, android.support.v4.app.InterfaceC0002c
    public Notification qy(C0012m c0012m, C0003d c0003d) {
        Notification notification = c0012m.lA;
        notification.setLatestEventInfo(c0012m.lt, c0012m.ls, c0012m.lr, c0012m.lq);
        Notification notificationRd = D.rd(notification, c0012m.lt, c0012m.ls, c0012m.lr, c0012m.lq, c0012m.lv);
        if (c0012m.lD > 0) {
            notificationRd.flags |= 128;
        }
        return notificationRd;
    }
}
