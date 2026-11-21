package android.support.v4.app;

import android.os.Build;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: renamed from: android.support.v4.app.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0001b {
    private static final InterfaceC0002c ll;

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            ll = new C0011l();
            return;
        }
        if (Build.VERSION.SDK_INT >= 20) {
            ll = new C0010k();
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            ll = new C0009j();
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            ll = new C0008i();
            return;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            ll = new C0007h();
            return;
        }
        if (Build.VERSION.SDK_INT >= 11) {
            ll = new C0006g();
        } else if (Build.VERSION.SDK_INT >= 9) {
            ll = new C0005f();
        } else {
            ll = new C0004e();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void qt(InterfaceC0018s interfaceC0018s, ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            interfaceC0018s.qP((C0017r) it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void qu(InterfaceC0019t interfaceC0019t, AbstractC0013n abstractC0013n) {
        if (abstractC0013n != null) {
            if (abstractC0013n instanceof C0015p) {
                C0015p c0015p = (C0015p) abstractC0013n;
                B.qY(interfaceC0019t, c0015p.lP, c0015p.lR, c0015p.lQ, c0015p.lV);
            } else if (abstractC0013n instanceof C0016q) {
                C0016q c0016q = (C0016q) abstractC0013n;
                B.qZ(interfaceC0019t, c0016q.lP, c0016q.lR, c0016q.lQ, c0016q.lW);
            } else if (abstractC0013n instanceof C0014o) {
                C0014o c0014o = (C0014o) abstractC0013n;
                B.qX(interfaceC0019t, c0014o.lP, c0014o.lR, c0014o.lQ, c0014o.lU, c0014o.lS, c0014o.lT);
            }
        }
    }
}
