package a.b.e.e.a;

import android.view.View;
import android.view.ViewTreeObserver;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class e implements ViewTreeObserver.OnGlobalLayoutListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ i f214a;

    public e(i iVar) {
        this.f214a = iVar;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        if (!this.f214a.isShowing() || this.f214a.j.size() <= 0 || this.f214a.j.get(0).f222a.G) {
            return;
        }
        View view = this.f214a.q;
        if (view == null || !view.isShown()) {
            this.f214a.dismiss();
            return;
        }
        Iterator<i.a> it = this.f214a.j.iterator();
        while (it.hasNext()) {
            it.next().f222a.show();
        }
    }
}
