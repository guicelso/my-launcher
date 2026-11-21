package android.support.v4.view;

import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public class i {
    private int We;

    public i(ViewGroup viewGroup) {
    }

    public void a(View view, int i) {
        this.We = 0;
    }

    public void a(View view, View view2, int i, int i2) {
        this.We = i;
    }

    public int getNestedScrollAxes() {
        return this.We;
    }

    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.We = i;
    }

    public void onStopNestedScroll(View view) {
        this.We = 0;
    }
}
