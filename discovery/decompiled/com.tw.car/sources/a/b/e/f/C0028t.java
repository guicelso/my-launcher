package a.b.e.f;

import android.view.ViewTreeObserver;
import android.widget.PopupWindow;

/* JADX INFO: renamed from: a.b.e.f.t, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0028t implements PopupWindow.OnDismissListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ViewTreeObserver.OnGlobalLayoutListener f380a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0026q.b f381b;

    public C0028t(C0026q.b bVar, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        this.f381b = bVar;
        this.f380a = onGlobalLayoutListener;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        ViewTreeObserver viewTreeObserver = C0026q.this.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.f380a);
        }
    }
}
