package a.b.e.f;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class M implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ View f287a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ N f288b;

    public M(N n, View view) {
        this.f288b = n;
        this.f287a = view;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f288b.smoothScrollTo(this.f287a.getLeft() - ((this.f288b.getWidth() - this.f287a.getWidth()) / 2), 0);
        this.f288b.f289a = null;
    }
}
