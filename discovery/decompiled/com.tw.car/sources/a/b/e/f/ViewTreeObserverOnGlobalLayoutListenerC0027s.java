package a.b.e.f;

import android.view.ViewTreeObserver;

/* JADX INFO: renamed from: a.b.e.f.s, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class ViewTreeObserverOnGlobalLayoutListenerC0027s implements ViewTreeObserver.OnGlobalLayoutListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0026q.b f377a;

    public ViewTreeObserverOnGlobalLayoutListenerC0027s(C0026q.b bVar) {
        this.f377a = bVar;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        C0026q.b bVar = this.f377a;
        if (!bVar.a(C0026q.this)) {
            this.f377a.dismiss();
        } else {
            this.f377a.c();
            super/*a.b.e.f.H*/.show();
        }
    }
}
