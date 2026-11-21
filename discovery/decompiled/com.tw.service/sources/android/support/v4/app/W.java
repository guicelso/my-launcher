package android.support.v4.app;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
final class W implements Runnable {
    final /* synthetic */ ArrayList Bd;

    W(ArrayList arrayList) {
        this.Bd = arrayList;
    }

    @Override // java.lang.Runnable
    public void run() {
        ba.b(this.Bd, 4);
    }
}
