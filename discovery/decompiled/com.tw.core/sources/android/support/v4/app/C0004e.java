package android.support.v4.app;

import android.app.Notification;

/* JADX INFO: renamed from: android.support.v4.app.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0004e implements InterfaceC0002c {
    C0004e() {
    }

    @Override // android.support.v4.app.InterfaceC0002c
    public Notification qy(C0012m c0012m, C0003d c0003d) {
        Notification notification = c0012m.lA;
        notification.setLatestEventInfo(c0012m.lt, c0012m.ls, c0012m.lr, c0012m.lq);
        if (c0012m.lD > 0) {
            notification.flags |= 128;
        }
        return notification;
    }
}
