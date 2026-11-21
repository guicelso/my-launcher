package android.support.v4.app;

import android.view.View;
import android.view.ViewTreeObserver;

/* JADX INFO: loaded from: classes.dex */
class pa implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
    private final Runnable mRunnable;
    private final View mView;
    private ViewTreeObserver mViewTreeObserver;

    private pa(View view, Runnable runnable) {
        this.mView = view;
        this.mViewTreeObserver = view.getViewTreeObserver();
        this.mRunnable = runnable;
    }

    public static pa add(View view, Runnable runnable) {
        pa paVar = new pa(view, runnable);
        view.getViewTreeObserver().addOnPreDrawListener(paVar);
        view.addOnAttachStateChangeListener(paVar);
        return paVar;
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        removeListener();
        this.mRunnable.run();
        return true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
        this.mViewTreeObserver = view.getViewTreeObserver();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        removeListener();
    }

    public void removeListener() {
        (this.mViewTreeObserver.isAlive() ? this.mViewTreeObserver : this.mView.getViewTreeObserver()).removeOnPreDrawListener(this);
        this.mView.removeOnAttachStateChangeListener(this);
    }
}
