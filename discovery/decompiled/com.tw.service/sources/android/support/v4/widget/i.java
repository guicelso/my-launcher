package android.support.v4.widget;

import android.annotation.TargetApi;
import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
class i implements View.OnApplyWindowInsetsListener {
    i(DrawerLayout drawerLayout) {
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    @TargetApi(21)
    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        ((DrawerLayout) view).a(windowInsets, windowInsets.getSystemWindowInsetTop() > 0);
        return windowInsets.consumeSystemWindowInsets();
    }
}
