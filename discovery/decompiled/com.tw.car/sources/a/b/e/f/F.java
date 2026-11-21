package a.b.e.f;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class F implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ H f273a;

    public F(H h) {
        this.f273a = h;
    }

    @Override // java.lang.Runnable
    public void run() {
        View view = this.f273a.v;
        if (view == null || view.getWindowToken() == null) {
            return;
        }
        this.f273a.show();
    }
}
