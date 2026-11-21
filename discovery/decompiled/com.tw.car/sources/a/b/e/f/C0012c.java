package a.b.e.f;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.ActionBarOverlayLayout;

/* JADX INFO: renamed from: a.b.e.f.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0012c extends AnimatorListenerAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ActionBarOverlayLayout f315a;

    public C0012c(ActionBarOverlayLayout actionBarOverlayLayout) {
        this.f315a = actionBarOverlayLayout;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        ActionBarOverlayLayout actionBarOverlayLayout = this.f315a;
        actionBarOverlayLayout.w = null;
        actionBarOverlayLayout.l = false;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        ActionBarOverlayLayout actionBarOverlayLayout = this.f315a;
        actionBarOverlayLayout.w = null;
        actionBarOverlayLayout.l = false;
    }
}
