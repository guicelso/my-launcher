package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/* JADX INFO: renamed from: android.support.v4.app.j, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0009j extends AbstractC0017s {
    final /* synthetic */ ComponentCallbacksC0013n n;

    C0009j(ComponentCallbacksC0013n componentCallbacksC0013n) {
        this.n = componentCallbacksC0013n;
    }

    @Override // android.support.v4.app.AbstractC0017s
    public ComponentCallbacksC0013n instantiate(Context context, String str, Bundle bundle) {
        return this.n.mHost.instantiate(context, str, bundle);
    }

    @Override // android.support.v4.app.AbstractC0017s
    public View onFindViewById(int i) {
        View view = this.n.mView;
        if (view != null) {
            return view.findViewById(i);
        }
        throw new IllegalStateException("Fragment does not have a view");
    }

    @Override // android.support.v4.app.AbstractC0017s
    public boolean onHasView() {
        return this.n.mView != null;
    }
}
