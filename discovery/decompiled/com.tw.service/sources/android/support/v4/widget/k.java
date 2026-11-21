package android.support.v4.widget;

import android.support.v4.view.C0027c;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class k extends C0027c {
    k() {
    }

    @Override // android.support.v4.view.C0027c
    public void a(View view, android.support.v4.view.a.b bVar) {
        super.a(view, bVar);
        if (DrawerLayout.y(view)) {
            return;
        }
        bVar.setParent(null);
    }
}
