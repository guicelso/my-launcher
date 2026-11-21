package a.b.e.f;

import android.support.v7.widget.ActionBarOverlayLayout;

/* JADX INFO: renamed from: a.b.e.f.d, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class RunnableC0013d implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ActionBarOverlayLayout f319a;

    public RunnableC0013d(ActionBarOverlayLayout actionBarOverlayLayout) {
        this.f319a = actionBarOverlayLayout;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f319a.a();
        ActionBarOverlayLayout actionBarOverlayLayout = this.f319a;
        actionBarOverlayLayout.w = actionBarOverlayLayout.e.animate().translationY(0.0f).setListener(this.f319a.x);
    }
}
