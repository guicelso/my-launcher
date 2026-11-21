package android.support.design.widget;

import android.support.v4.view.D;
import android.support.v4.view.j;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class a implements j {
    final /* synthetic */ CoordinatorLayout n;

    a(CoordinatorLayout coordinatorLayout) {
        this.n = coordinatorLayout;
    }

    @Override // android.support.v4.view.j
    public D a(View view, D d) {
        return this.n.b(d);
    }
}
