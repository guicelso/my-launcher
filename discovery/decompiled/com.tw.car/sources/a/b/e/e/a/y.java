package a.b.e.e.a;

import android.view.View;
import android.view.ViewTreeObserver;

/* JADX INFO: loaded from: classes.dex */
public class y implements View.OnAttachStateChangeListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ z f246a;

    public y(z zVar) {
        this.f246a = zVar;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        ViewTreeObserver viewTreeObserver = this.f246a.q;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.f246a.q = view.getViewTreeObserver();
            }
            z zVar = this.f246a;
            zVar.q.removeGlobalOnLayoutListener(zVar.k);
        }
        view.removeOnAttachStateChangeListener(this);
    }
}
