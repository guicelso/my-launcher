package a.b.e.e.a;

import android.view.View;
import android.view.ViewTreeObserver;

/* JADX INFO: loaded from: classes.dex */
public class x implements ViewTreeObserver.OnGlobalLayoutListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ z f245a;

    public x(z zVar) {
        this.f245a = zVar;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        if (this.f245a.isShowing()) {
            z zVar = this.f245a;
            if (zVar.j.G) {
                return;
            }
            View view = zVar.o;
            if (view == null || !view.isShown()) {
                this.f245a.dismiss();
            } else {
                this.f245a.j.show();
            }
        }
    }
}
