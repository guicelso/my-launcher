package android.support.v4.view;

import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
final class q implements View.OnApplyWindowInsetsListener {
    final /* synthetic */ j Ye;

    q(j jVar) {
        this.Ye = jVar;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        return (WindowInsets) D.a(this.Ye.a(view, D.wrap(windowInsets)));
    }
}
