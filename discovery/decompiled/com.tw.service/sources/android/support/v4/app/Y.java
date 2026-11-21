package android.support.v4.app;

import android.graphics.Rect;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class Y implements Runnable {
    final /* synthetic */ ka Dd;
    final /* synthetic */ ComponentCallbacksC0013n Fd;
    final /* synthetic */ ComponentCallbacksC0013n Jd;
    final /* synthetic */ boolean Kd;
    final /* synthetic */ a.b.c.b.b Ld;
    final /* synthetic */ View Md;
    final /* synthetic */ Rect Nd;

    Y(ComponentCallbacksC0013n componentCallbacksC0013n, ComponentCallbacksC0013n componentCallbacksC0013n2, boolean z, a.b.c.b.b bVar, View view, ka kaVar, Rect rect) {
        this.Fd = componentCallbacksC0013n;
        this.Jd = componentCallbacksC0013n2;
        this.Kd = z;
        this.Ld = bVar;
        this.Md = view;
        this.Dd = kaVar;
        this.Nd = rect;
    }

    @Override // java.lang.Runnable
    public void run() {
        ba.b(this.Fd, this.Jd, this.Kd, this.Ld, false);
        View view = this.Md;
        if (view != null) {
            this.Dd.a(view, this.Nd);
        }
    }
}
