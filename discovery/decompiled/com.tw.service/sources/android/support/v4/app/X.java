package android.support.v4.app;

import android.view.View;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
final class X implements Runnable {
    final /* synthetic */ ArrayList Bd;
    final /* synthetic */ Object Cd;
    final /* synthetic */ ka Dd;
    final /* synthetic */ View Ed;
    final /* synthetic */ ComponentCallbacksC0013n Fd;
    final /* synthetic */ ArrayList Gd;
    final /* synthetic */ ArrayList Hd;
    final /* synthetic */ Object Id;

    X(Object obj, ka kaVar, View view, ComponentCallbacksC0013n componentCallbacksC0013n, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, Object obj2) {
        this.Cd = obj;
        this.Dd = kaVar;
        this.Ed = view;
        this.Fd = componentCallbacksC0013n;
        this.Gd = arrayList;
        this.Hd = arrayList2;
        this.Bd = arrayList3;
        this.Id = obj2;
    }

    @Override // java.lang.Runnable
    public void run() {
        Object obj = this.Cd;
        if (obj != null) {
            this.Dd.a(obj, this.Ed);
            this.Hd.addAll(ba.b(this.Dd, this.Cd, this.Fd, this.Gd, this.Ed));
        }
        if (this.Bd != null) {
            if (this.Id != null) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(this.Ed);
                this.Dd.a(this.Id, this.Bd, arrayList);
            }
            this.Bd.clear();
            this.Bd.add(this.Ed);
        }
    }
}
