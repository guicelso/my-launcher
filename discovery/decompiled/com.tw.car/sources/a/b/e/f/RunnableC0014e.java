package a.b.e.f;

import android.support.v7.widget.ActionBarOverlayLayout;

/* JADX INFO: renamed from: a.b.e.f.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class RunnableC0014e implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ActionBarOverlayLayout f320a;

    public RunnableC0014e(ActionBarOverlayLayout actionBarOverlayLayout) {
        this.f320a = actionBarOverlayLayout;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f320a.a();
        ActionBarOverlayLayout actionBarOverlayLayout = this.f320a;
        actionBarOverlayLayout.w = actionBarOverlayLayout.e.animate().translationY(-this.f320a.e.getHeight()).setListener(this.f320a.x);
    }
}
