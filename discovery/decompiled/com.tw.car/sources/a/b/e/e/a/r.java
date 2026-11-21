package a.b.e.e.a;

import android.widget.PopupWindow;

/* JADX INFO: loaded from: classes.dex */
public class r implements PopupWindow.OnDismissListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ s f241a;

    public r(s sVar) {
        this.f241a = sVar;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        this.f241a.d();
    }
}
